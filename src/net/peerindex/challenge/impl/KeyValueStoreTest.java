package net.peerindex.challenge.impl;

import static org.junit.Assert.*;
import net.peerindex.challenge.webcrawler.Factory;
import net.peerindex.challenge.webcrawler.KeyValueStore;

import org.junit.Test;

public class KeyValueStoreTest {

	@Test
	public void testPutOneThing() {
		
		KeyValueStore ksStore = Factory.createKeyValueStore();
		String key= "aUrl";
		String value="<HTML><HEAD></HEAD><BODY></BODY></HTML>";			
		ksStore.put(key, value);		
		assertTrue(ksStore.get(key).equals(value));

	}


	

}
