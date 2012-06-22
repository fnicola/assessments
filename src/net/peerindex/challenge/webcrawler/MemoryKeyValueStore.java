package net.peerindex.challenge.webcrawler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MemoryKeyValueStore implements KeyValueStore {

	Map<String,String> map = Collections.synchronizedMap(new HashMap<String,String>());
	
	@Override
	public boolean contains(String key) {
		return map.containsKey(key);
	}

	@Override
	public String get(String key) {
		return map.get(key);
	}

	@Override
	public boolean put(String key, String value) {
		
		map.put(key, value);
		
		return true;
	}

	@Override
	public boolean delete(String key) {
		// TODO Auto-generated method stub
		return false;
	}

}
