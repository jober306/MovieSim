package core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import data.ife.IFEDataReader;
import data.ife.IFEMovieData;
import data.tmdb.TMDbDataReader;
import data.tmdb.TMDbMovieData;
import data.tmdb.TMDbMovieDataProcessor;
import feature.*;
import model.MovieCorpus;
import model.MovieDictionnary;
import model.MovieDocument;
import similarity.CosineSimilarity;
import similarity.Similarity;

public class SimilarMoviesFinderDriver {
	
	private static final List<String> INITIAL_SEEDS_NAME = Stream.of("Moana", "2 Guns", 
			"Harry Potter and the Deathly Hallows Part 1", "The Avengers",
			"Trainwreck", "Keeping Up with the Joneses", "Passengers", "Allied",
			"The Revenant", "The Matrix", "The Talented Mr. Ripley",
			"The Hobbit: An Unexpected Journey", "The Space Between Us",
			"Skyfall", "Split", "Pitch Perfect 2", "Life of PI", "Moonlight",
			"Miss You Already", "Snowden").collect(Collectors.toList());
	
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
		List<double[]> seeds = INITIAL_SEEDS_NAME.stream().map(name -> corpus.getMovie(name)).map(movie -> extractor.extract(movie, dict)).collect(Collectors.toList());
		finder.findNDelegate(20, seeds).stream().map(MovieDocument::name).forEach(System.out::println);
	}
}
