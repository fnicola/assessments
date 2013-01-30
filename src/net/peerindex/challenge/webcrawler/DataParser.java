package net.peerindex.challenge.webcrawler;

import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataParser {
	
	private JsonFactory jsonFactory;
	private static final Logger LOG = LoggerFactory.getLogger(DataParser.class);
	
	public DataParser() {
		this.jsonFactory = new JsonFactory();		
	}
	
	public String parse(String json) {
		
		if (json == null) {
			return null;
		}
		
		if (json.trim().isEmpty()) {
			LOG.warn("empty json line: {}", json);
			return null;
		}
		
		try {
			JsonParser parser = jsonFactory.createJsonParser(json);
			parser.nextToken();
			String fieldValue = null;
			while (parser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = parser.getCurrentName();
      		  	parser.nextToken();
                if (fieldName.equals("url")) {
                	fieldValue = parser.getText();
                	return fieldValue;
                }
			}
			// no url field found
			return null; 
			
		} catch (JsonParseException e) {
			LOG.error("fail to parse line: {}\n{}", json, e);
			return null;
		} catch (IOException e) {
			LOG.error("fail to parse line: {}\n{}", json, e);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
