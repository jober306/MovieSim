package core;

import static util.JacksonWrapper.getObjAsString;
import static util.JacksonWrapper.writeObject;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import comparator.ScoredMovieDocumentComparator;
import data.ScoredMovieDocument;
import data.ScoredMovieDocument.ScoredMovieDocumentDAO;
import feature.FeatureExtractor;
import model.MovieCorpus;
import model.MovieDictionnary;
import model.MovieDocument;
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
		return corpus.getMovies().stream().collect(Collectors.toMap(movie -> movie.getName(), movie -> findMovieTopN(movie, n)));
	}
	
	public String findAllMoviesTopNAsJson(int n) {
		return getObjAsString(mapMoviesTopNToDAO(findAllMoviesTopN(n)));
	}
	
	public void writeAllMoviesTopNAsJson(int n, File f) {
		writeObject(f, mapMoviesTopNToDAO(findAllMoviesTopN(n)));
	}
	
	public List<ScoredMovieDocument> findMovieTopN(MovieDocument doc, int n) {
		double[] currentDocFeatures = extractor.extract(doc, dict);
		List<ScoredMovieDocument> unsortedScoredMovies = corpus.getMovies().stream().map(movie -> new ScoredMovieDocument(movie, getScoreFor(currentDocFeatures, movie))).collect(Collectors.toList());
		unsortedScoredMovies.sort(new ScoredMovieDocumentComparator());
		//The first result is skip because the most similar movie is itself.
		return unsortedScoredMovies.subList(1, Math.min(unsortedScoredMovies.size(), n+1));
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
		return movies.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> mapMovieTopNToDAO(entry.getValue())));
	}
	
	private List<ScoredMovieDocumentDAO> mapMovieTopNToDAO(List<ScoredMovieDocument> movies){
		return movies.stream().map(otherMovie -> otherMovie.toDAO()).collect(Collectors.toList());
	}
	
	private double getScoreFor(double[] currentDocFeatures, MovieDocument movie) {
		return sim.between(currentDocFeatures, extractor.extract(movie, dict));
	}
}
