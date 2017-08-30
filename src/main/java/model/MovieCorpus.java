package model;

import java.util.List;
import java.util.stream.Collectors;

import util.JacksonWrapper;

public class MovieCorpus {
	
	final List<MovieDocument> movies;
	
	public MovieCorpus(List<MovieDocument> movies) {
		this.movies = movies;
	}
	
	public List<MovieDocument> movies(){
		return this.movies;
	}
	
	public MovieDocument getMovie(String name) {
		return movies.stream().filter(movie -> movie.name().equals(name)).collect(Collectors.toList()).get(0);
	}
}
