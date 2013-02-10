package net.peerindex.challenge.webcrawler;

/**
 * Run crawl from command line.
 */
public class RunCrawl {

    public RunCrawl() {

        WebCrawler crawler = Factory.createWebCrawler();
        KeyValueStore store = Factory.createKeyValueStore();
        crawler.setKeyValueStore(store);
        crawler.setURLStream(Factory.createURLIterator());

        crawler.initialise();
        crawler.execute();
        crawler.shutdown();
    }

    /**
     * Run crawl.
     *
     * @param args none
     */
    public static void main(String[] args) {

        new RunCrawl();

    }


}
