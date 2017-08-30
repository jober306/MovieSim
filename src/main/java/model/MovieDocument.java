package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.MovieData;
import data.tmdb.ProcessedTMDbMovieData;

public class MovieDocument implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	final MovieData movieData;
	final Map<String, Integer> tf;
	
	public MovieDocument(MovieData movieData) {
		this.movieData = movieData;
		if(movieData.processedTags() != null)
			this.tf = createTf();
		else
			this.tf = null;
	}
	
	public List<Double> categoriesScore() {
		return this.movieData.categoriesScore();
	}
	
	public Map<String, Integer> tf(){
		return this.tf;
	}
	
	public int tfOf(String word) {
		return tf.getOrDefault(word, 0);
	}
	
	public String name() {
		return movieData.name();
	}
	
	private Map<String, Integer> createTf(){
		Map<String, Integer> tf = new HashMap<String, Integer>();
		movieData.processedTags().stream().flatMap(List::stream).forEach(word -> addWord(word, tf));
		return tf;
	}
	
	private void addWord(String word, Map<String, Integer> tf) {
		if(!tf.containsKey(word)) {
			tf.put(word, 1);
		}else {
			int count = tf.get(word);
			tf.put(word, count + 1);
		}
	}
	
	public List<String> getBOW(){
		return movieData.processedTags().stream().flatMap(List::stream).distinct().collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return movieData.toString();
	}
}
