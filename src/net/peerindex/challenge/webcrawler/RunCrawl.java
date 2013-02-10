package net.peerindex.challenge.webcrawler;

import java.io.IOException;

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
        
        try {
            new TfIdfEvaluator(store, UrlExtractor.extractUrlsFrom("resources/part-m-00000"));
        } catch (IOException e) {
            System.out.println(">>>>>Could not evaluate TF.IDF");
            e.printStackTrace();
        }
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
