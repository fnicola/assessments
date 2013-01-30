package net.peerindex.challenge.webcrawler;

import java.io.File;

/**
 * Run crawl from command line.
 */
public class RunCrawl {

    public RunCrawl(File inputFilesFolder) {

        WebCrawler crawler = Factory.createWebCrawler();
        crawler.setKeyValueStore(Factory.createKeyValueStore());
        crawler.setURLStream(Factory.createURLIterator(inputFilesFolder));

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

    	//No input validation for now
    	File inputFilesFolder = new File(args[0]);
    	
        new RunCrawl(inputFilesFolder);

    }


}
