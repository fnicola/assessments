package net.peerindex.challenge.webcrawler.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Queue;

import org.apache.commons.io.IOUtils;


import com.google.common.collect.Lists;
import com.google.gson.Gson;

/**
 * Possible improvements: Stream from file system (this is naively loading
 * everything into memory).
 */
public class URLIteratorImpl implements Iterator<URL> {

	Queue<URL> URLs = Lists.newLinkedList();
			
	public URLIteratorImpl(File inputFilesFolder) {
		Gson gson = new Gson();
		File inputDirectory = inputFilesFolder;

		for (File inputFile : inputDirectory.listFiles()) {
			System.out.println(inputFile.getAbsolutePath());
			try {
				if (inputFile.getName().startsWith("part-m-")) // Sanity check
				{
					InputStream inputStream = new FileInputStream(inputFile);
					
					for (String jsonURL : IOUtils.readLines(inputStream)) {
						URL url = gson.fromJson(jsonURL, URL.class);
						URLs.add(url);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean hasNext() {
		return !URLs.isEmpty();
	}

	@Override
	public URL next() {
		return URLs.remove();
	}

	@Override
	public void remove() {
		//no-op
	}

}
