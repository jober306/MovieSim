package core;

import java.util.List;

import data.MovieData;
import data.MovieDataProcessor;
import data.MovieDataReader;
import feature.*;
import model.MovieCorpus;
import model.MovieDictionnary;
import model.MovieDocument;
import similarity.CosineSimilarity;
import similarity.Similarity;

public class SimilarMoviesFinderDriver {
	
	
	public static void main(String[] args)  {
		List<MovieData> rawMovieData = MovieDataReader.read();
		List<MovieDocument> movies = MovieDataProcessor.processToMovieDocument(rawMovieData);
		MovieCorpus corpus = new MovieCorpus(movies);
		MovieDictionnary dict = new MovieDictionnary(corpus);
		Similarity sim = new CosineSimilarity();
		FeatureExtractor extractor = new BinaryFeatureExtractor();
		SimilarMoviesFinder finder = new SimilarMoviesFinder(corpus, dict, sim, extractor);
		System.out.println(finder.findAllMoviesTopNAsJson(10));
	}
}
