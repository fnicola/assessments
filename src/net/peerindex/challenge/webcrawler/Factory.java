package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.Iterator;

/**
 * Factory.
 */
public class Factory {

    public static KeyValueStore createKeyValueStore() {
        return new MemoryKeyValueStore();
    }

    public static WebCrawler createWebCrawler() {
        return new MultiThreadWebCrawler();
    }

    public static Iterator<URL> createURLIterator() {
        return new URLIterator("C:/Users/developer/Assessments/resources");
    }
}
