package net.peerindex.challenge.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class URLIteratorImpl implements Iterator<URL> {
	
	File[] files = null;
	File currentFile=null;	
	int fileNumber;
	List<String> currentLines;	
	Iterator<String> lineIterator;
	Logger log = Logger.getLogger(URLIteratorImpl.class);
	
	/*
	 * Iterates the directory specified, examining file for urls
	 */
	public URLIteratorImpl(String directory_) {
	
		log.info(" searching directory " + directory_);
        File dir = new File(directory_);  
        files = dir.listFiles(new FilenameFilter() {  
                 public boolean accept(File dir, String filename) 
                      { return filename.startsWith("part-m-"); } 
        } ); 	 	
        if (files==null || files.length==0) 
        {
        	throw new RuntimeException("No files found in " + directory_);
        }
        log.info("Found " + files.length +" files");
        nextLine();
	}

	public static void main(String[] args)
	{
		URLIteratorImpl uiter = new URLIteratorImpl("resources");
		uiter.next();
	}
	public boolean hasNext() {
		return (haveMoreFiles() || lineIterator.hasNext());
	}

	private boolean haveMoreFiles() {
		if (fileNumber<files.length)
		{
			return true;			
		}
		return false;
	}

	public URL next() {		
		nextLine();									
		String url = getUrl(lineIterator.next());		
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			// FIXME What to do here? skip url?
			e.printStackTrace();
		}
		return null;	
	}

	private void nextLine() {
		if (currentFile==null || !lineIterator.hasNext())
		{			
			//TODO What happens when we can't read the file
			currentFile = files[fileNumber++];
			log.info("processing "+ currentFile);
			try {
				currentLines= FileUtils.readLines(currentFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			lineIterator= currentLines.iterator();
		}
	}

	private String getUrl(String line) {	
		int start=nthOccurrence(line,'"',2);
		int end=nthOccurrence(line,'"',3);
		return line.substring(start+1,end);		
	}
	
	public static int nthOccurrence(String str, char c, int n) { 
	    int pos = str.indexOf(c, 0); 
	    while (n-- > 0 && pos != -1) 
	    {
	        pos = str.indexOf(c, pos+1);
	    }
	    return pos; 
	} 

	public void remove() {
		// TODO Auto-generated method stub

	}
	
	

}


