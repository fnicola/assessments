package net.peerindex.challenge.webcrawler;

import org.apache.log4j.Logger;

/**
 * Run crawl from command line.
 */
public class RunCrawl {
	Logger logger = Logger.getLogger(RunCrawl.class);

    public RunCrawl() {
   	        
    	WebCrawler crawler = Factory.createWebCrawler();
        
        crawler.setKeyValueStore(Factory.createKeyValueStore());
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
