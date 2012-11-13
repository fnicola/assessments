package net.peerindex.challenge.webcrawler;

public class Factory {

    private static final KeyValueStore store = new MemoryKVStore();
    private static final WebCrawler crawler = new AsyncCrawler(store, URLList.getURLs().iterator());

    public static KeyValueStore getStore() {
        return store;
    }

    public static WebCrawler getCrawler() {
        return crawler;
    }
}
