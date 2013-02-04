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
        return new MyWebCrawler(50);
    }

    public static Iterator<URL> createURLIterator() {
        return new URLStream(167);
    }

}
