package net.peerindex.challenge.webcrawler.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Preconditions;

import net.peerindex.challenge.webcrawler.KeyValueStore;


public class KeyValueStoreImpl implements KeyValueStore{
	private final ConcurrentMap<String, String> store = new ConcurrentHashMap<String, String>();

	@Override
	public boolean contains(String key) {
		Preconditions.checkArgument(key!=null,"key cannot be null");
		return store.containsKey(key);
	}

	@Override
	public String get(String key) {
		Preconditions.checkArgument(key!=null,"key cannot be null");
		return store.get(key);
	}

	@Override
	public boolean put(String key, String value) {
		Preconditions.checkArgument(key!=null,"key cannot be null");
		Preconditions.checkArgument(value!=null,"value cannot be null");
		String previous = store.put(key,value);
		boolean noPrevious = previous == null;
		return noPrevious;
	}

	@Override
	public boolean delete(String key) {
		String previous = store.remove(key);
		boolean noPrevious = previous == null;
		return !noPrevious;
	}
	
	@Override
	public String toString(){
		return store.toString();
	}

}
