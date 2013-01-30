package net.peerindex.challenge.webcrawler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class URLIterator implements Iterator<URL> {

	private static final Logger LOG = LoggerFactory
			.getLogger(URLIterator.class);

	private File root;
	private List<File> filelist;
	private DataParser parser;
	private Iterator<File> fileIterator;
	private BufferedReader currentFileStream;
	private String currentLine;
	private URL currentURL;

	public URLIterator(String root, DataParser parser) {
		this.parser = parser;
		this.root = new File(root);
		if (this.root.isDirectory()) {
			// Assuming there is no sub directories
			this.filelist = Arrays.asList(this.root.listFiles());
			this.fileIterator = this.filelist.iterator();
		} else if (this.root.isFile()) {
			this.filelist = new ArrayList<File>();
			this.filelist.add(this.root);
			this.fileIterator = this.filelist.iterator();
		} else {
			LOG.warn("Not a file or directory: {} ", this.root);
			this.filelist = null;
		}
		this.currentFileStream = null;
		this.currentLine = null;

	}
	
	private boolean validURL(String line){
		
		String jsonUrl = parser.parse(line);
		try {
			URL url = new URL(jsonUrl);
			currentURL = url;
			return true;
		} catch (MalformedURLException e) {
			LOG.error("invalid URL {}", line);
		}
		return false;
	}

	private boolean hasLines() {
		currentLine = null;

		if (currentFileStream == null) {
			return false;
		}
		try {
			while (true) {
				currentLine = currentFileStream.readLine();
				if (currentLine != null) {
					if (validURL(currentLine)) {
						return true;
					}
				}
				else {
					// EOF
					return false;
				}
			}
		} catch (IOException e) {
			LOG.error("error reading for file: {} ", e);
		}
		return false;
	}

	private boolean hasFiles() {
		if (fileIterator == null) {
			return false;
		}
		while (true) {
			if (fileIterator.hasNext()) {
				File currentFile = null;
				currentFile = fileIterator.next();

				FileInputStream fileStream = null;
				try {
					fileStream = new FileInputStream(currentFile);
				} catch (FileNotFoundException e) {
					LOG.error("File not found but listed: {}", currentFile
							.getAbsolutePath());
				}
				DataInputStream dataInput = new DataInputStream(fileStream);
				currentFileStream = new BufferedReader(new InputStreamReader(dataInput));
				return true;
			} else {
				break;
			}
		}
		return false;
	}

	@Override
	public boolean hasNext() {
		if (hasLines()) {
			 return true;
		}
		else {
			if (hasFiles() && hasLines()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public URL next() {
		return currentURL;
	}

	@Override
	public void remove() {
		currentLine = null;
		currentURL = null;
	}
}
