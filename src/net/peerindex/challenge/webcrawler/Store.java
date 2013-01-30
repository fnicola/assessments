package net.peerindex.challenge.webcrawler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Store implements KeyValueStore {

	private HashMap<String, String> store;
	private HashMap<String, Long> storeWords;
	
	public Store() {
		this.store = new HashMap<String, String>();
		this.storeWords = new HashMap<String, Long>();
	}
	
	@Override
	public boolean contains(String key) {
		return store.containsKey(key);
	}

	@Override
	public boolean delete(String key) {
		store.remove(key);
		return true;
	}

	@Override
	public String get(String key) {
		return store.get(key);
	}

	@Override
	public boolean put(String key, String value) {
		store.put(key, value);
		return true;
	}
	
	public Iterator<String> iterator() {
		return store.values().iterator();
	}
	
	public void updateWordCounter(List<String> words) {
		for (String word : words) {
			word = word.toLowerCase();
			if (storeWords.containsKey(word)) {
				Long counter = storeWords.get(word);
				counter += 1;
				storeWords.put(word, counter);
			}
			else {
				storeWords.put(word, Long.valueOf(1));
			}
		}
	}
	
	private HashMap<String, Long> wordCount(String url) {
		
		if (!store.containsKey(url)) {
			return null;
		}
		HashMap<String, Long> wordCount = new HashMap<String, Long>();
		List<String> words = Scrapper.getText(store.get(url));
		
		for (String word : words) {
			if (wordCount.containsKey(word.toLowerCase())){
				Long counter = wordCount.get(word.toLowerCase());
				counter += 1;
				wordCount.put(word.toLowerCase(), counter);
			}
			else
			{
				wordCount.put(word.toLowerCase(), Long.valueOf(1));
			}
		}
		return wordCount;
	}
	
	private HashMap<String, Double> TFID(String url) {
		// TODO
		return null;
	}
}
