package data;

import java.util.List;
import java.util.stream.Collectors;

import model.MovieDocument;
import nlp.Stemmer;
import nlp.Tokenizer;

public class MovieDataProcessor {
	
	final static Stemmer STEMMER = new Stemmer(); 
	
	public static List<ProcessedMovieData> processToRawData(List<MovieData> moviesData){
		return moviesData.stream().map(MovieDataProcessor::processMovieData).collect(Collectors.toList());
	}
	
	public static List<MovieDocument> processToMovieDocument(List<MovieData> moviesData){
		return processToRawData(moviesData).stream().map(MovieDocument::new).collect(Collectors.toList());
	}
	
	private static ProcessedMovieData processMovieData(MovieData movieData){
		List<List<String>> tokenizedTags = movieData.getTags().stream().map(Tokenizer::tokenize).collect(Collectors.toList());
		List<List<String>> stemmedTags = tokenizedTags.stream().map(MovieDataProcessor::stemTokens).collect(Collectors.toList());
		return new ProcessedMovieData(movieData.getName(), stemmedTags);
	}
	
	private static List<String> stemTokens(List<String> tokens){
		return tokens.stream().map(String::toLowerCase).map(STEMMER::stem).collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		System.out.println(MovieDataProcessor.processToRawData(MovieDataReader.read()));
	}
}
