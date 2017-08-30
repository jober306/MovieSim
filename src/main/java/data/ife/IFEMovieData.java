package data.ife;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import data.MovieData;

public class IFEMovieData extends MovieData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public IFEMovieData(String name, List<Double> categoriesScore) {
		super(name, null, categoriesScore, null);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(name());
		sb.append("\"");
		sb.append(" [");
		sb.append(categoriesScore().stream().map(e -> e.toString()).collect(Collectors.joining(",")));
		sb.append("]");
		return sb.toString();
	}
}
