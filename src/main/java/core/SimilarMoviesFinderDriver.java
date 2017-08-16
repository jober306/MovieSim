package core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import data.MovieData;
import data.MovieDataProcessor;
import data.MovieDataReader;
import feature.BinaryFeatureExtractor;
import feature.FeatureExtractor;
import feature.TFFeatureExtractor;
import feature.TFIDFFeatureExtractor;
import model.MovieCorpus;
import model.MovieDictionnary;
import model.MovieDocument;
import similarity.CosineSimilarity;
import similarity.Similarity;

public class SimilarMoviesFinderDriver {
	
	
	public static void main(String[] args) {
		List<MovieData> rawMovieData = MovieDataReader.read();
		List<MovieDocument> movies = MovieDataProcessor.processToMovieDocument(rawMovieData);
		MovieCorpus corpus = new MovieCorpus(movies);
		MovieDictionnary dict = new MovieDictionnary(corpus);
		Similarity sim = new CosineSimilarity();
		FeatureExtractor extractor = new BinaryFeatureExtractor();
		SimilarMoviesFinder finder = new SimilarMoviesFinder(corpus, dict, sim, extractor);
		MovieDocument maxSteel = corpus.getMovie("Max Steel");
		MovieDocument annabelle = corpus.getMovie("Annabelle");
		MovieDocument toyStory3 = corpus.getMovie("Toy Story 3");
		System.out.println(finder.findTopN(toyStory3, 10).stream().map(scoredDoc -> scoredDoc.toString()).collect(Collectors.joining("\n")));
	}
}
