package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.Iterator;

import net.peerindex.challenge.impl.KeyValueStoreImpl;
import net.peerindex.challenge.impl.URLIteratorImpl;
import net.peerindex.challenge.impl.WebCrawlerImpl;

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
        return new URLIteratorImpl("resources"); //TODO
    }

}
