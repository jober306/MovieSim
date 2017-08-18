package data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class representing a movie entry. It is essentially a container for the movie name and its tags.
 * @author jbergeron
 *
 */
public class MovieData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	final String name;
	final List<String> tags;
	
	/**
	 * Constructor of the movie entry class.
	 * @param name The name of the movie.
	 * @param tags The associated with the movie.
	 */
	public MovieData(String name, List<String> tags) {
		this.name = name;
		this.tags = tags;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<String> getTags(){
		return this.tags;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Movie Name: ");
		sb.append(name);
		sb.append("\t Movie Tags: [");
		sb.append(tags.stream().collect(Collectors.joining(", ")));
		sb.append("]\n");
		return sb.toString();
	}
}
