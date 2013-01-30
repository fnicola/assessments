package net.peerindex.challenge.webcrawler;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import net.peerindex.challenge.webcrawler.impl.KeyValueStoreImpl;
import net.peerindex.challenge.webcrawler.impl.URLIteratorImpl;
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

    public static Iterator<URL> createURLIterator(File inputFilesFolder) {
    	return new URLIteratorImpl(inputFilesFolder);	
    }

}
