package net.peerindex.challenge.webcrawler.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import net.peerindex.challenge.webcrawler.KeyValueStore;

/**
 * Key value store implementation backed by HashMap
 */
public class KeyValueStoreImpl implements KeyValueStore {

	Map<String, String> keyValueStoreMap = Maps.newHashMap();

	@Override
	public boolean contains(String key) {
		return keyValueStoreMap.containsKey(key);
	}

	@Override
	public String get(String key) {
		return keyValueStoreMap.get(key);
	}

	@Override
	public boolean put(String key, String value) {
		if (this.contains(key)) {
			return false;
		}
		keyValueStoreMap.put(key, value);
		return true;
	}

	@Override
	public boolean delete(String key) {
		if (keyValueStoreMap.remove(key) != null) {
			return true;
		}
		return false;
	}

}
