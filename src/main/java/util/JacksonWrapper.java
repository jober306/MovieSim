package util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonWrapper {
	
	
	public static String getObjAsString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void writeObject(File f, Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(f, obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
