package data.tmdb;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import data.MovieData;

/**
 * Class representing a movie entry. It is essentially a container for the movie name and its tags.
 * @author jbergeron
 *
 */
public class TMDbMovieData extends MovieData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the movie entry class.
	 * @param name The name of the movie.
	 * @param tags The associated with the movie.
	 */
	public TMDbMovieData(String name, List<String> tags) {
		super(name, tags, null, null);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Movie Name: ");
		sb.append(name());
		sb.append("\t Movie Tags: [");
		sb.append(tags().stream().collect(Collectors.joining(", ")));
		sb.append("]\n");
		return sb.toString();
	}
}
