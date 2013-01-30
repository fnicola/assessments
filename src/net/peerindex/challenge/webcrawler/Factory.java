package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.Iterator;

/**
 * Factory.
 */
public class Factory {

    public static KeyValueStore createKeyValueStore() {
        return new Store();
    }

    public static WebCrawler createWebCrawler() {
        return new Crawler();
    }

    public static Iterator<URL> createURLIterator() {
    	DataParser parser = new DataParser();
    	return new URLIterator("./resources", parser);
    }

}
