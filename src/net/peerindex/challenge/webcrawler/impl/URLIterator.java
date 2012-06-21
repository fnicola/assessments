package net.peerindex.challenge.webcrawler.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.io.LineIterator;

import com.google.gson.Gson;

import net.peerindex.challenge.webcrawler.ConfigStore;

public class URLIterator implements Iterator<URL> {
	static final ThreadLocal<Gson> GSON_SRC = new ThreadLocal<Gson>() {
		@Override
		public Gson initialValue() {
			return new Gson();
		}
	};

	private final Iterator<File> files;
	private LineIterator lineIt;

	public URLIterator(String path) throws FileNotFoundException {
		File folder = new File(path);
		files = Arrays.asList(folder.listFiles()).iterator();
		lineIt = lineIterator();
	}

	@Override
	public boolean hasNext() {
		if(!lineIt.hasNext()){
			LineIterator.closeQuietly(lineIt);
			if(files.hasNext()){
				try {
					lineIt = lineIterator();
				} catch (FileNotFoundException e) {
					throw new AssertionError(e);
				}
			}
			return lineIt.hasNext();
		}else{
			return true;
		}
	}

	@Override
	public URL next() {
		try {
			if (!lineIt.hasNext()) {
				LineIterator.closeQuietly(lineIt);
				lineIt = lineIterator();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonRecord = lineIt.next();
		URLRecord record = GSON_SRC.get().fromJson(jsonRecord, URLRecord.class);
		URL url;
		try {
			url = new URL(record.url);
		} catch (MalformedURLException e) {
			throw new AssertionError("Got malformed url:" + record.url ,e);
		}
		return url;

	}

	private LineIterator lineIterator() throws FileNotFoundException {
		File nextFile = files.next();
		return new LineIterator(new FileReader(nextFile));
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();

	}

}
