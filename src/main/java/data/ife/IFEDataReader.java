package data.ife;

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


public class IFEDataReader {
	
	final public static String MOVIE_ENTRIES_FILE_NAME = "/data/categories.json"; 

	
	public static List<IFEMovieData> read(){
		List<IFEMovieData> movieEntries = new ArrayList<IFEMovieData>();
		JsonFactory factory = new JsonFactory();
	    ObjectMapper mapper = new ObjectMapper(factory);
	    JsonNode rootNode;
		try {
			rootNode = mapper.readTree(getMovieEntriesFile());
		    for (JsonNode node : rootNode) {
		    	Iterator<Map.Entry<String,JsonNode>> fieldsIterator = node.fields();
		    	String name = fieldsIterator.next().getValue().asText();
		    	List<Double> categoriesScore = new ArrayList<Double>();
		    	while(fieldsIterator.hasNext()) {
		    		categoriesScore.add(Double.parseDouble(fieldsIterator.next().getValue().asText()));
		    	}
		    	movieEntries.add(new IFEMovieData(name, categoriesScore));
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
		return new File(IFEDataReader.class.getResource(MOVIE_ENTRIES_FILE_NAME).toURI());
	}
	
	public static void main(String[] args) {
		IFEDataReader.read().stream().forEach(System.out::println);
	}
}
