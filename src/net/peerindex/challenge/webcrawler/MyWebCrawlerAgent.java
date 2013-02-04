package net.peerindex.challenge.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.io.InputStream;




public class MyWebCrawlerAgent implements Runnable {
	private KeyValueStore _kvstore;
	private Thread _t;
	private String _agentname; 
	private Iterator<URL> _iter;
	
	public MyWebCrawlerAgent(KeyValueStore kvstore, Iterator<URL> _urlstream, String agentname){
		_iter = _urlstream;
		_kvstore = kvstore;
		_agentname = agentname;
	    _t = new Thread(this, _agentname);
	    System.out.println("Agent " + _agentname + " was created ");
	}
	
	public void run() {
		URL url;
		int statuscode=0;
		HttpURLConnection http;
		String encoding;
		URLConnection con;
		BufferedReader br;
    	String line;
    	StringBuilder sb;
    	
		while (_iter.hasNext()) {
			
			url = _iter.next();
			if(!_kvstore.contains(url.toString())){
				try {

					con = url.openConnection();
					http = (HttpURLConnection)url.openConnection();
					statuscode = http.getResponseCode();
					InputStream in = (InputStream) http.getInputStream();
					encoding = con.getContentEncoding();
					encoding = encoding == null ? "UTF-8" : encoding;
					 
			    	//read it with BufferedReader
			    	br = new BufferedReader(new InputStreamReader(in));
			    	sb = new StringBuilder();
			 
			    	line = "";
			    	while ((line = br.readLine()) != null) {
			    		sb.append(line);
			    	}
			    
			    	//System.out.println(""+statuscode +" : " + url.toString() + " : " + sb.toString());
			    	
			    	if(statuscode == 200)
						_kvstore.put(url.toString(), sb.toString());	
			    	br.close();
			    	in.close();
				} catch (IOException e) {
					statuscode = 0;
				}
				
				
				
			}
		}
	}
	
	public void WaitToFinish() throws InterruptedException{
		_t.start();
		_t.join();
		System.out.println("\nAgent " + _agentname + " is dead");
	}
}
