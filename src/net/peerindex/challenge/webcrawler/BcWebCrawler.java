package net.peerindex.challenge.webcrawler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BcWebCrawler implements WebCrawler {

    private static final String FILENAME_PREFIX = "resources/part-m-";
    private static final int MAX_FILENAME_INDEX = 167;
    private static final int MAX_NUMBER_OF_CONCURRENT_TASKS = 4;
    private KeyValueStore store;
    private boolean finished = false;
    private int currentFileIndex = -1;
    private List<MapTask> activeTasks = new ArrayList<MapTask>(MAX_NUMBER_OF_CONCURRENT_TASKS);
    
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
        try {
            for (int i = 0; i < MAX_NUMBER_OF_CONCURRENT_TASKS && i < MAX_FILENAME_INDEX + 1; i++) {
                startNewTaskIfNecessary();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void shutdown() {
        // this will avoid starvation in case everything has finished before shutdown was called
        if (!finished) {
            try {
                wait();
            } catch (InterruptedException e) {
                doShutdown();
            }
        }
        
        doShutdown();
    }
    
    private void doShutdown() {
        System.out.println(">>>>>Successful shutdown");
    }

    public void reportResults(final Map<String, String> results, final MapTask task) {
        for (String key : results.keySet()) {
            String result = results.get(key);
            
            if (result != null) {
                store.put(key, result);
            }
        }
        
        try {
            activeTasks.remove(task);
            startNewTaskIfNecessary();
            
            if (activeTasks.size() == 0 && !finished) {
                // last finished task - notify the thread waiting to enter shutdown()
                synchronized (this) {
                    finished = true;
                    notify();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * If all files have been handled, it is not necessary to start a new map task
     * @throws IOException
     */
    private void startNewTaskIfNecessary() throws IOException {
        File file = getNextFile();
        
        if (file != null){
            MapTask newTask = new MapTask(file, this);
            activeTasks.add(newTask);
            new Thread(newTask).start();
        }
    }
    
    private synchronized File getNextFile() {
        if (++currentFileIndex <= MAX_FILENAME_INDEX) {
            File result = new File(generateNextFileName());
            
            if (result.exists()) {
                return result;
            }
        }
        
        return null;
    }
    
    private String generateNextFileName() {
        if (currentFileIndex > 10000) {
            return FILENAME_PREFIX + currentFileIndex;
        } else if (currentFileIndex > 1000) {
            return FILENAME_PREFIX + "0" + currentFileIndex;
        } else if (currentFileIndex > 100) {
            return FILENAME_PREFIX + "00" + currentFileIndex;
        } else if (currentFileIndex > 10) {
            return FILENAME_PREFIX + "000" + currentFileIndex;
        } else { // if (currentFileNumber > 0) {
            return FILENAME_PREFIX + "0000" + currentFileIndex;
        }
    }
}
