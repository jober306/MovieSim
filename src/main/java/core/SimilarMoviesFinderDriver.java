package core;

import java.util.List;
import java.util.stream.Collectors;

import data.ife.IFEDataReader;
import data.ife.IFEMovieData;
import data.tmdb.TMDbDataReader;
import data.tmdb.TMDbMovieData;
import data.tmdb.TMDbMovieDataProcessor;
import edu.stanford.nlp.coref.neural.CategoricalFeatureExtractor;
import feature.*;
import model.MovieCorpus;
import model.MovieDictionnary;
import model.MovieDocument;
import similarity.CosineSimilarity;
import similarity.Similarity;

public class SimilarMoviesFinderDriver {
	
	
	public static void main(String[] args)  {
		testIFE();
	}
	
	public static void testTMDb() {
		List<TMDbMovieData> rawMovieData = TMDbDataReader.read();
		List<MovieDocument> movies = TMDbMovieDataProcessor.processToMovieDocument(rawMovieData);
		List<MovieDocument> moviesAtLeastOne = movies.stream().filter(movie -> movie.getBOW().size() >= 1).collect(Collectors.toList());
		MovieCorpus corpus = new MovieCorpus(moviesAtLeastOne);
		MovieDictionnary dict = new MovieDictionnary(corpus);
		Similarity sim = new CosineSimilarity();
		FeatureExtractor extractor = new TFIDFFeatureExtractor();
		SimilarMoviesFinder finder = new SimilarMoviesFinder(corpus, dict, sim, extractor);
		finder.findNDelegate(10).stream().map(MovieDocument::name).forEach(System.out::println);
	}
	
	public static void testIFE() {
		List<IFEMovieData> rawMovieData = IFEDataReader.read();
		List<MovieDocument> movies = rawMovieData.stream().map(MovieDocument::new).collect(Collectors.toList());
		MovieCorpus corpus = new MovieCorpus(movies);
		MovieDictionnary dict = new MovieDictionnary(corpus);
		Similarity sim = new CosineSimilarity();
		FeatureExtractor extractor = new CategoryFeatureExtractor();
		SimilarMoviesFinder finder = new SimilarMoviesFinder(corpus, dict, sim, extractor);
		finder.findNDelegate(10).stream().map(MovieDocument::name).forEach(System.out::println);
	}
}
