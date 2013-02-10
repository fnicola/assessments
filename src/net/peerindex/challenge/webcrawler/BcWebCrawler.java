package net.peerindex.challenge.webcrawler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class BcWebCrawler implements WebCrawler {

    private static final String FILENAME_PREFIX = "resources/part-m-";
    private static final int MAX_FILENAME_INDEX = 167;
    private KeyValueStore store;
    private int currentFileNumber = 0;
    
    @Override
    public void setKeyValueStore(KeyValueStore store) {
        this.store = store;
    }

    @Override
    public void setURLStream(Iterator<URL> iterator) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialise() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void execute() {
        File file = new File("resources/part-m-00000");
        try {
            
            new MapTask(file, this).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub
        
    }

    public void reportResults(Map<String, String> results) {
        for (String key : results.keySet()) {
            store.put(key, results.get(key));
        }
        
        File nextFile = getNextFile(); 
        if (nextFile != null) {
            try {
                new MapTask(nextFile, this).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private synchronized File getNextFile() {
        if (currentFileNumber < MAX_FILENAME_INDEX) {
            File result = new File(generateNextFileName());
            
            if (result.exists()) {
                return result;
            }
        }
        
        return null;
    }
    
    private String generateNextFileName() {
        currentFileNumber++;
        if (currentFileNumber > 10000) {
            return FILENAME_PREFIX + currentFileNumber;
        } else if (currentFileNumber > 1000) {
            return FILENAME_PREFIX + "0" + currentFileNumber;
        } else if (currentFileNumber > 100) {
            return FILENAME_PREFIX + "00" + currentFileNumber;
        } else if (currentFileNumber > 10) {
            return FILENAME_PREFIX + "000" + currentFileNumber;
        } else { // if (currentFileNumber > 0) {
            return FILENAME_PREFIX + "0000" + currentFileNumber;
        }
    }
}
