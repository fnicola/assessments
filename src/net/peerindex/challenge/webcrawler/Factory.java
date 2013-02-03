package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.Iterator;

/**
 * Factory.
 */
public class Factory {

    public static KeyValueStore createKeyValueStore() {
        return new MyKeyValueStore();
    }

    public static WebCrawler createWebCrawler() {
        return new MyWebCrawler();
    }

    public static Iterator<URL> createURLIterator() {
        return new URLStream();
    }

}
