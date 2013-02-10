package net.peerindex.challenge.webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UrlExtractor {
    
    public static List<String> extractUrlsFrom(final File file) throws IOException {
        List<String> result = new ArrayList<String>();
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while((line = reader.readLine())!= null) {
            // urls are kept as JSON objects: {"url":"http://www.free-lance.ru/blogs/view.php?tr=686868","top":[]}
            String url = line.substring("{\"url\":\"".length(), line.indexOf("\",\""));
            result.add(url);
        }
        
        return result;
    }
}
