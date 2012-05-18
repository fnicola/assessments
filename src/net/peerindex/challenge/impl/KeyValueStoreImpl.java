package net.peerindex.challenge.impl;

import gnu.trove.map.hash.THashMap;

import java.util.Map;

import net.peerindex.challenge.webcrawler.KeyValueStore;

public class KeyValueStoreImpl implements KeyValueStore {

	//TODO Replace with HBASE/hsql/something persistent
	Map<String,String> map;
	
	public KeyValueStoreImpl()
	{
		map = new THashMap<String,String>(1000000);
	}

	public boolean contains(String key) {
		return map.containsKey(key);
	}

	public String get(String key) {
		return map.get(key);
	}

	public boolean put(String key, String value) {
		String newValue = map.put(key,value);
		return (newValue==null ? true:false);		
	}

	public boolean delete(String key) {
		if (map.containsKey(key))
		{
			map.remove(key);
			return true;
		}
		return false;
	}

}
