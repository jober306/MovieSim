package data.tmdb;

import static java.util.stream.Collectors.*;
import java.util.List;
import java.util.stream.Collectors;

import model.MovieDocument;
import nlp.Stemmer;
import nlp.Tokenizer;

public class TMDbMovieDataProcessor {
	
	final static Stemmer STEMMER = new Stemmer(); 
	
	public static List<ProcessedTMDbMovieData> processToRawData(List<TMDbMovieData> moviesData){
		return moviesData.stream().map(TMDbMovieDataProcessor::processMovieData).collect(toList());
	}
	
	public static List<MovieDocument> processToMovieDocument(List<TMDbMovieData> moviesData){
		return processToRawData(moviesData).stream().map(MovieDocument::new).collect(toList());
	}
	
	private static ProcessedTMDbMovieData processMovieData(TMDbMovieData movieData){
		List<List<String>> tokenizedTags = movieData.tags().stream().map(Tokenizer::tokenize).collect(toList());
		List<List<String>> stemmedTags = tokenizedTags.stream().map(TMDbMovieDataProcessor::stemTokens).collect(toList());
		return new ProcessedTMDbMovieData(movieData.name(), stemmedTags);
	}
	
	private static List<String> stemTokens(List<String> tokens){
		return tokens.stream().map(String::toLowerCase).map(STEMMER::stem).collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		System.out.println(TMDbMovieDataProcessor.processToRawData(TMDbDataReader.read()));
	}
}
