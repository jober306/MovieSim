package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import data.MovieDataProcessor;
import data.MovieDataReader;

public class MovieDictionnary {
	
	final MovieCorpus corpus;
	final Map<String, Integer> dict;
	final Set<String> vocabulary;
	final Map<String, Double> idf;
	final double defaultIdf;
	
	
	public MovieDictionnary(MovieCorpus corpus) {
		this.corpus = corpus;
		this.dict = createDict();
		this.vocabulary = createVocabulary();
		this.idf = createIdf();
		this.defaultIdf = calculateDefaultIdf();
	}
	
	public Set<String> getVocabulary(){
		return vocabulary;
	}
	
	public int getIndex(String word) {
		return dict.getOrDefault(word, -1);
	}
	
	public double getIdf(String word) {
		return idf.getOrDefault(word, defaultIdf);
	}
	
	private Map<String, Integer> createDict(){
		Map<String, Integer> dict = new HashMap<String, Integer>();
		List<String> allWords = corpus.getMovies().stream().map(entry -> entry.getBOW()).flatMap(List::stream).distinct().collect(Collectors.toList());
		IntStream.range(0, allWords.size()).forEach(index -> dict.put(allWords.get(index), index));
		return dict;
	}
	
	private Set<String> createVocabulary(){
		return dict.entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.toSet());
	}
	
	private Map<String, Double> createIdf(){
		Map<String, Double> idf = new HashMap<String, Double>();
		Set<String> vocabulary = getVocabulary();
		int vocabularySize = vocabulary.size();
		for(String word : vocabulary) {
			int d = corpus.getMovies().stream().map(entry -> entry.getBOW()).filter(bow -> bow.contains(word)).collect(Collectors.toSet()).size();
			double idfScore = Math.log((double)vocabularySize / (double)d); 
			idf.put(word, idfScore);
		}
		return idf;
	}
	
	private double calculateDefaultIdf() {
		return Math.log((double) getVocabulary().size() / 1.0d);
	}
	
	public static void main(String[] args) {
		MovieCorpus corpus = new MovieCorpus(MovieDataProcessor.processToMovieDocument(MovieDataReader.read()));
		MovieDictionnary dict = new MovieDictionnary(corpus);
	}
}
