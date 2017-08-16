package model;

import java.util.List;
import java.util.stream.Collectors;

public class MovieCorpus {
	
	final List<MovieDocument> movies;
	
	public MovieCorpus(List<MovieDocument> movies) {
		this.movies = movies;
	}
	
	public List<MovieDocument> getMovies(){
		return this.movies;
	}
	
	public MovieDocument getMovie(String name) {
		return movies.stream().filter(movie -> movie.getName().equals(name)).collect(Collectors.toList()).get(0);
	}
}
