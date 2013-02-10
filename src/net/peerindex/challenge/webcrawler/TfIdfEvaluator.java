package net.peerindex.challenge.webcrawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TfIdfEvaluator {
    
    private KeyValueStore store;
    private Map<String, Map<String, Integer>> documentWordFrequencies = new HashMap<String, Map<String, Integer>>();
    private Map<String, Integer> mostFrequent = new HashMap<String, Integer>(); // most frequent occurrence in given document
    
    /**
     * 
     * @param store
     * @param keys - set of keys in the store, the values for which should be used for TF.IDF evaluation
     */
    public TfIdfEvaluator(final KeyValueStore store, final List<String> keys) {
        this.store = store;
        
        for (String key : keys) {
            getWordFrequenciesForDocument(key);
        }
        
        // just one word, running out of time
        String word = "to";
        // TF for the word in document 0 (first key)
        double tf_0 = ((double)documentWordFrequencies.get(keys.get(0)).get(word)) / mostFrequent.get(keys.get(0));
        
        int numberOfOccurrences = presentInDocuments(word);
        double idf_word = Math.log((double)keys.size() / numberOfOccurrences) / Math.log(2);
        
        System.out.println(">>>>TF.IDF for " + word + ": " + (tf_0 * idf_word));
    }
    
    private int presentInDocuments(String word) {
        int result = 0;
        for (String doc : documentWordFrequencies.keySet()) {
            if (documentWordFrequencies.get(doc).keySet().contains(word)) {
                result++;
            }
        }
        return result;
    }

    private void getWordFrequenciesForDocument(String key) {
        String text = store.get(key);
        if (text == null) {
            return;
        }
        String[] words = text.split("\\s+");
        //words = removeStopWords();
        int mostFrequent = 0;
        
        Map<String, Integer> documentStats = new HashMap<String, Integer>();
        
        for (String word : words) {
            Integer freq = documentStats.get(word);
            Integer newValue = 0;
            if (freq == null) {
                newValue = 1;
            } else {
                newValue = freq + 1;                
            }
            
            if (newValue > mostFrequent) {
                mostFrequent = newValue;
            }
            documentStats.put(word, newValue);                
        }
        
        documentWordFrequencies.put(key, documentStats);
        this.mostFrequent.put(key, mostFrequent);
    }

    private String[] removeStopWords() {
        // TODO Auto-generated method stub
        return null;
    }
}
