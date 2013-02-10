package net.peerindex.challenge.webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MapTask {

    private final LinkedList<String> urlRecords = new LinkedList<String>();
    private final Map<String, String> results = new HashMap<String, String>();
    private final BcWebCrawler masterCrawler;
    
    public MapTask(final File urlSource, final BcWebCrawler masterCrawler) throws IOException {
        this.masterCrawler = masterCrawler;
        BufferedReader reader = new BufferedReader(new FileReader(urlSource));
        String line;

        while((line = reader.readLine())!= null) {
            urlRecords.add(line);
        }
    }
    
    public void run() {
        for (String record : urlRecords) {
            // urls are kept as JSON objects: {"url":"http://www.free-lance.ru/blogs/view.php?tr=686868","top":[]}
            String url = record.substring("{\"url\":\"".length(), record.indexOf("\",\""));
            
            Document doc;
            try {
                doc = Jsoup.parse(new URL(url), 3*1000);
                
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
        
        masterCrawler.reportResults(results);
    }
}
