package net.peerindex.challenge.webcrawler;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Iterator;

import net.peerindex.challenge.webcrawler.impl.KeyValueStoreImpl;
import net.peerindex.challenge.webcrawler.impl.URLIterator;
import net.peerindex.challenge.webcrawler.impl.WebCrawlerImpl;

/**
 * Factory.
 */
public class Factory {

    public static KeyValueStore createKeyValueStore() {
        return new KeyValueStoreImpl();
    }

    public static WebCrawler createWebCrawler() {
    	return new WebCrawlerImpl();
    }

    public static Iterator<URL> createURLIterator() {
        try {
			return new URLIterator("./testresource");
		} catch (FileNotFoundException e) {
			throw new AssertionError(e);
		}
    }

}
