package net.peerindex.challenge.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.peerindex.challenge.webcrawler.KeyValueStore;
import net.peerindex.challenge.webcrawler.WebCrawler;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebCrawlerImpl implements WebCrawler {

	Logger log = Logger.getLogger(URLIteratorImpl.class);
	KeyValueStore store;
	Iterator<URL> urlIterator;
	final WebClient webClient = new WebClient();
	Map<String,Throwable> errors = new HashMap<String,Throwable>();	
	
	public void setKeyValueStore(KeyValueStore store_) {
		store=store_;
	}

	public void setURLStream(Iterator<URL> iterator_) {			
		urlIterator=iterator_;
	}

	public void initialise() {
		// TODO Auto-generated method stub
	}

	public void execute() {
		while (urlIterator.hasNext()) {
			URL url = (URL) urlIterator.next();
			log.info("Processing " + url);
			process(url);
		}
	}

	private void process(URL url) {
	    try {
			final HtmlPage page = webClient.getPage(url);
			store.put(url.toString(), page.asText());
			//TODO Just save to disk?
			//FileUtils.writeStringToFile(new File("store/"+url.getFile()), page.asText());
 
		} catch (Throwable e) {
			// TODO Process errors, maybe retry x times?
			errors.put(url.toString(), e);
		}
	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
