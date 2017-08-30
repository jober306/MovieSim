package core;

import static util.JacksonWrapper.getObjAsString;
import static util.JacksonWrapper.writeObject;
import static util.VectorUtil.findNearestVector;
import static java.util.stream.Collectors.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import comparator.ScoredMovieDocumentComparator;
import feature.FeatureExtractor;
import model.MovieCorpus;
import model.MovieDictionnary;
import model.MovieDocument;
import model.ScoredMovieDocument;
import model.ScoredMovieDocument.ScoredMovieDocumentDAO;
import nlp.KMeans;
import similarity.Similarity;

public class SimilarMoviesFinder {
	
	MovieCorpus corpus;
	MovieDictionnary dict;
	Similarity sim;
	FeatureExtractor extractor;
	
	public SimilarMoviesFinder(MovieCorpus corpus, MovieDictionnary dict, Similarity sim, FeatureExtractor extractor) {
		this.corpus = corpus;
		this.dict = dict;
		this.sim = sim;
		this.extractor = extractor;
	}
	
	public Map<String, List<ScoredMovieDocument>> findAllMoviesTopN(int n){
		return corpus.movies().stream().collect(toMap(movie -> movie.name(), movie -> findMovieTopN(movie, n)));
	}
	
	public List<ScoredMovieDocument> findMovieTopN(MovieDocument doc, int n) {
		double[] currentDocFeatures = extractor.extract(doc, dict);
		List<ScoredMovieDocument> scoredMovies = corpus.movies().stream().map(movie -> new ScoredMovieDocument(movie, getScoreFor(currentDocFeatures, movie))).collect(toList());
		scoredMovies.sort(new ScoredMovieDocumentComparator());
		//The first result is skip because the most similar movie is itself.
		return scoredMovies.subList(1, Math.min(scoredMovies.size(), n+1));
	}
	
	public List<MovieDocument> findNDelegate(int n){
		List<MovieDocument> delegates = new ArrayList<MovieDocument>();
		List<MovieDocument> movies = corpus.movies();
		List<double[]> vectors = corpus.movies().stream().map(movie -> extractor.extract(movie, dict)).collect(toList());
		List<double[]> centroids = KMeans.apply(vectors, n);
		for(double[] centroid : centroids) {
			int index = findNearestVector(centroid, vectors);
			delegates.add(movies.get(index));
			System.out.println(movies.get(index));
			vectors.remove(index);
			movies.remove(index);
		}
		return delegates;
	}
	
	public String findAllMoviesTopNAsJson(int n) {
		return getObjAsString(mapMoviesTopNToDAO(findAllMoviesTopN(n)));
	}
	
	public void writeAllMoviesTopNAsJson(int n, File f) {
		writeObject(f, mapMoviesTopNToDAO(findAllMoviesTopN(n)));
	}
	
	public String findTopNAsJson(MovieDocument movie, int n) {
		return getObjAsString(findMovieTopNAsDAO(movie, n));
	}
	
	public void writeMovieTopNAsJson(MovieDocument movie, int n, File f) {
		writeObject(f, findMovieTopNAsDAO(movie, n));
	}
	
	private List<ScoredMovieDocumentDAO> findMovieTopNAsDAO(MovieDocument movie, int n){
		return mapMovieTopNToDAO(findMovieTopN(movie, n));
	}
	
	private Map<String, List<ScoredMovieDocumentDAO>> mapMoviesTopNToDAO(Map<String, List<ScoredMovieDocument>> movies){
		return movies.entrySet().stream().collect(toMap(Entry::getKey, entry -> mapMovieTopNToDAO(entry.getValue())));
	}
	
	private List<ScoredMovieDocumentDAO> mapMovieTopNToDAO(List<ScoredMovieDocument> movies){
		return movies.stream().map(otherMovie -> otherMovie.toDAO()).collect(toList());
	}
	
	private double getScoreFor(double[] currentDocFeatures, MovieDocument movie) {
		return sim.between(currentDocFeatures, extractor.extract(movie, dict));
	}
}
