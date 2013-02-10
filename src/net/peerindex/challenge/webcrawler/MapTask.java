package net.peerindex.challenge.webcrawler;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MapTask implements Runnable {

    private final File urlSource;
    private final BcWebCrawler masterCrawler;
    
    public MapTask(final File urlSource, final BcWebCrawler masterCrawler) throws IOException {
        this.masterCrawler = masterCrawler;
        this.urlSource = urlSource;
    }
    
    public void run() {
        List<String> urls;
        Map<String, String> results = new HashMap<String, String>();
        try {
            urls = UrlExtractor.extractUrlsFrom(urlSource);
        
            for (String url : urls) {
                Document doc;
                try {
                    doc = Jsoup.parse(new URL(url), 3 * 1000);
                    
                    if (doc == null || doc.body() == null) {
                        // sink unsuccessful attempts
                        continue;
                    }
                    
                    String text = doc.body().text();
                    results.put(url, text);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    // sink unsuccessful attempts
                }
            }
        } catch (IOException e1) {}
        
        masterCrawler.reportResults(results, this);
    }
}
