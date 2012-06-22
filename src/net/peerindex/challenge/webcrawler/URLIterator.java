package net.peerindex.challenge.webcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class URLIterator implements Iterator<URL> {

	private Iterator<File> fileList;

	private Iterator<URL> urlList = null;
	
	Logger log = Logger.getLogger("URLIterator");
	

	public URLIterator(String resource) {
		fileList = Arrays.asList(new File(resource).listFiles()).iterator();
	}

	@Override
	public boolean hasNext() {
		
		if (urlList == null || !urlList.hasNext()) {
			
			if (! fileList.hasNext()){
				return false;
			}
			
			File file = fileList.next();
			
			log.info("Creating url iterator from "+file);

			try {
				urlList = getURLIterator(file);
			} catch (FileNotFoundException e) {
				log.warning(e.getMessage());
			} catch (IOException e) {
				log.warning(e.getMessage());
			}
		}
		
		return true;
	}

	@Override
	public URL next() {

		return urlList.next();
	}

	private static Iterator<URL> getURLIterator(File file) throws FileNotFoundException, IOException {
		
		List l = new ArrayList();
		
		List<String> lines = IOUtils.readLines(new FileInputStream(file));
		for (String line : lines) {
			
			//this throws and exception so I'm going to used a quick and dirty pass assumption is 		
			/*
			Gson gson = new Gson();
			  
			Type mapType = new TypeToken<Map<String,String>>() {}.getType();
			 
			Map<String, String> map = gson.fromJson(line, mapType);
			l.add(new URL(map.get("url"));
			*/
			//todo replace with correct json code
			String s = StringUtils.substringBetween(line,"{\"url\":\"", "\",");
			l.add(new URL(s));
		}
		return l.iterator();
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
