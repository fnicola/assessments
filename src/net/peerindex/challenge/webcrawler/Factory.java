package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.Iterator;

/**
 * Factory.
 */
public class Factory {

    public static KeyValueStore createKeyValueStore() {
        return new BcKeyValueStore();
    }

    public static WebCrawler createWebCrawler() {
        return new BcWebCrawler();
    }

    public static Iterator<URL> createURLIterator() {
        return null; // not using the iterator, distributing input files to MapTasks directly to aid parallel execution
    }

}
