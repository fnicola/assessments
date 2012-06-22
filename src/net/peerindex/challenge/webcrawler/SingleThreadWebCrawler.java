package net.peerindex.challenge.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

public class SingleThreadWebCrawler implements WebCrawler {

	Logger log = Logger.getLogger("SingleThreadWebCrawler");
	
	private Iterator<URL> iterator;
	private KeyValueStore store;

	@Override
	public void setKeyValueStore(KeyValueStore store) {
		this.store = store;
	}

	@Override
	public void setURLStream(Iterator<URL> iterator) {
		this.iterator = iterator;
	}

	@Override
	public void initialise() {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute() {
		while (iterator.hasNext())
		{
			URL url = iterator.next();
			System.out.println(url.getHost());
			try {
				// This has several issues with timeouts I'd maybe replace with httpclient going forward
				String content = IOUtils.toString(url);
				store.put(url.toString(), content);
				
			} catch (IOException e) {
				log.warning("Failed to download content from "+url+" due to "+e.getMessage());
			}
		}

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
