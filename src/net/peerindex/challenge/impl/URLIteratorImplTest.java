package net.peerindex.challenge.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

public class URLIteratorImplTest {

	@Test
	public void testIteratorOneFile() {
		int expectedCount=6;
		int actual=0;
		URLIteratorImpl urlIter = new URLIteratorImpl("test-resources");
				
		while (urlIter.hasNext())
		{
			URL url = urlIter.next();
			assertNotNull(url);
			actual++;			
		}
		assertEquals(expectedCount,actual);
	}

	public static void debug(String s)
	{
		System.out.println(s);		
	}

	
}
