package net.peerindex.challenge.webcrawler;

import java.util.Hashtable;
import java.util.Map;

public class BcKeyValueStore implements KeyValueStore {

    private Map<String, String> map = new Hashtable<String, String>();
    
    @Override
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("BcKeyStore::contains() - null key");
        }
        return map.containsKey(key);
    }

    @Override
    public String get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("BcKeyStore::get() - null key");
        }
        return map.get(key);
    }

    @Override
    public boolean put(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("BcKeyStore::put() - null key");
        } else if (value == null) {
            throw new IllegalArgumentException("BcKeyStore::put() - null value");
        }
        
        System.out.println(key);
        System.out.println(value);
        return map.put(key, value) == null;
    }

    @Override
    public boolean delete(String key) {
        if (key == null) {
            throw new IllegalArgumentException("BcKeyStore::delete() - null key");
        }
        return map.remove(key) != null;
    }

}
