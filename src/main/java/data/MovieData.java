package data;

import java.io.Serializable;
import java.util.List;

public class MovieData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	final String name;
	final List<String> tags;
	final List<List<String>> processedTags;
	final List<Double> categoriesScore;
	
	public MovieData(String name, List<String> tags, List<Double> categoriesScore, List<List<String>> processedTags) {
		this.name = name;
		this.tags = tags;
		this.categoriesScore = categoriesScore;
		this.processedTags = processedTags;
	}
	
	public String name() {
		return this.name;
	}
	
	public List<String> tags(){
		return this.tags;
	}
	
	public List<Double> categoriesScore(){
		return this.categoriesScore;
	}
	
	public List<List<String>> processedTags(){
		return this.processedTags;
	}
}
