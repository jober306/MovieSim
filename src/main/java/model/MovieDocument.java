package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.ProcessedMovieData;

public class MovieDocument {
	
	final ProcessedMovieData entry;
	final Map<String, Integer> tf;
	
	public MovieDocument(ProcessedMovieData entry) {
		this.entry = entry;
		this.tf = createTf();
	}
	
	public String getName() {
		return entry.getName();
	}
	
	public int getTf(String word) {
		return tf.getOrDefault(word, 0);
	}
	
	private Map<String, Integer> createTf(){
		Map<String, Integer> tf = new HashMap<String, Integer>();
		entry.getProcessedTags().stream().flatMap(List::stream).forEach(word -> addWord(word, tf));
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
		return entry.getProcessedTags().stream().flatMap(List::stream).distinct().collect(Collectors.toList());
	}
}
