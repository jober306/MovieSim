package data.tmdb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.MovieData;

public class ProcessedTMDbMovieData  extends MovieData implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	public ProcessedTMDbMovieData(String name, List<List<String>> processedTags) {
		super(name, null, null, processedTags);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Movie Name: ");
		sb.append(name());
		sb.append("\t Movie Tags: [");
		sb.append(processedTags().stream().map(this::formatProcessedTags).collect(Collectors.joining(", ")));
		sb.append("]\n");
		return sb.toString();
	}
	
	private String formatProcessedTags(List<String> processedTag) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(processedTag.stream().collect(Collectors.joining(", ")));
		sb.append("]");
		return sb.toString();
	}
}
