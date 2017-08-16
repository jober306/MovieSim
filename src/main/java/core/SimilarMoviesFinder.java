package core;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import comparator.ScoredMovieDocumentComparator;
import data.ScoredMovieDocument;
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
	
	public List<ScoredMovieDocument> findTopN(MovieDocument doc, int n) {
		double[] currentDocFeatures = extractor.extract(doc, dict);
		List<ScoredMovieDocument> unsortedScoredMovies = corpus.getMovies().stream().map(movie -> new ScoredMovieDocument(movie, getScoreFor(movie, currentDocFeatures))).collect(Collectors.toList());
		unsortedScoredMovies.sort(new ScoredMovieDocumentComparator());
		return unsortedScoredMovies.subList(0, Math.min(unsortedScoredMovies.size(), n));
	}
	
	private double getScoreFor(MovieDocument otherDoc, double[] currentDocFeatures) {
		return sim.between(currentDocFeatures, extractor.extract(otherDoc, dict));
	}
}
