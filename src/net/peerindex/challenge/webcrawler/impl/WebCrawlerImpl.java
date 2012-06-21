package net.peerindex.challenge.webcrawler.impl;

import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.peerindex.challenge.webcrawler.KeyValueStore;
import net.peerindex.challenge.webcrawler.WebCrawler;

public class WebCrawlerImpl implements WebCrawler {
	private KeyValueStore store;
	private Iterator<URL> iterator;
	private ExecutorService main = Executors.newSingleThreadExecutor();
	private Fetcher fetcher;
	private Future<String> handle;

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
		// init check
		this.fetcher = new Fetcher(store);

	}

	@Override
	public void execute() {
		Callable<String> exec = new Callable<String>() {
			@Override
			public String call() throws Exception {
				while (iterator.hasNext()) {
					URI uri = new URI(iterator.next().toString());
					fetcher.fetch(uri);
				}
				
				return "DONE";
			}
		};
		handle = main.submit(exec);
	}

	@Override
	public void shutdown() {
		main.shutdown();
		try {
			main.awaitTermination(100, TimeUnit.MINUTES);
		} catch (InterruptedException e1) {
			System.out.println("Execution has timed out");
			main.shutdownNow();
		}
		
		fetcher.shutdown();
		try {
			handle.get();
			System.out.println("We are done. Got:" + store);
		} catch (Exception e) {
			throw new RuntimeException("Encountered RuntimeException while queueing files",e);
		}
	}

}
