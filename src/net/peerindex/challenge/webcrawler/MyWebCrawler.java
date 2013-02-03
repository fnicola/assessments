package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Make HTTP requests as fast as possible using a stream of URL's as input, and storing
 * the HTTP response in a key value store.
 *
 * Failed HTTP requests do not need to be logged.
 *
 */
public class MyWebCrawler implements WebCrawler{

	private KeyValueStore _store;
	private Iterator<URL> _urlstream;
	
	private final int _numberofagents = 100;
	private ArrayList<MyWebCrawlerAgent> _agentlist;
	
	
    public void setKeyValueStore(KeyValueStore store){
    	_store = store;
    }

    public void setURLStream(Iterator<URL> iterator){
    	_urlstream = iterator;
    }

    public void initialise(){
    	_agentlist = new ArrayList<MyWebCrawlerAgent>();
    	
		for(int j = 0 ; j<_numberofagents ; j++){
			_agentlist.add(new MyWebCrawlerAgent(_store, _urlstream, " "+j));
		}
    }

    public void execute(){
    	
		for(int j = 0 ; j<_numberofagents ; j++){
			try {
				_agentlist.get(j).WaitToFinish();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

    public void shutdown(){}

}
