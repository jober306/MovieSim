package data.tmdb;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class responsible to read the movie entries contained in the resource file.
 * @author jbergeron
 *
 */
public class TMDbDataReader {
	
	final public static String MOVIE_ENTRIES_FILE_NAME = "/data/keywords_301.txt"; 
	
	/**
	 * Method that reads the resource file and returns it as a list of MovieEntry.
	 * @return The list of movie entries in the resource file. If the file can't be found or the Json
	 * is malformed, an empty list is returned.
	 */
	public static List<TMDbMovieData> read(){
		List<TMDbMovieData> movieEntries = new ArrayList<TMDbMovieData>();
		JsonFactory factory = new JsonFactory();
	    ObjectMapper mapper = new ObjectMapper(factory);
	    JsonNode rootNode;
		try {
			rootNode = mapper.readTree(getMovieEntriesFile());
		    Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
		    while (fieldsIterator.hasNext()) {
	           TMDbMovieData entry = extractMovieEntry(fieldsIterator.next());
	           movieEntries.add(entry);
	       }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return movieEntries;
	}
	
	/**
	 * Method that return the movie entries file.
	 * @return The movie entries file containing the json representing the movie name and its tags.
	 * @throws URISyntaxException If the uri representing the movie entries is malformed.
	 */
	public static File getMovieEntriesFile() throws URISyntaxException {
		return new File(TMDbDataReader.class.getResource(MOVIE_ENTRIES_FILE_NAME).toURI());
	}
	
	private static List<String> extractMovieTags(JsonNode movieTagsNode){
		List<String> movieTags = new ArrayList<String>();
        Iterator<JsonNode> tagsIterator = movieTagsNode.elements();
        while(tagsIterator.hasNext()) {
     	   movieTags.add(tagsIterator.next().asText());
        }
        return movieTags;
	}
	
	private static TMDbMovieData extractMovieEntry(Map.Entry<String, JsonNode> field) {
        String movieName = field.getKey();
        List<String> movieTags = extractMovieTags(field.getValue());
        return new TMDbMovieData(movieName, movieTags);
	}
	
	public static void main(String[] args){
		List<TMDbMovieData> entries = TMDbDataReader.read();
		entries.forEach(System.out::println);
	}
}
