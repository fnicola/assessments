package net.peerindex.challenge.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

public class MultiThreadWebCrawler implements WebCrawler {

	Logger log = Logger.getLogger("MultiThreadWebCrawler");

	ExecutorService executor = Executors.newFixedThreadPool(10);

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
		while (iterator.hasNext()) {
			URL url = iterator.next();

			executor.execute(new Job(url));
		}
	}

	@Override
	public void shutdown() {
		executor.shutdown();
	}

	class Job implements Runnable {

		private URL url;

		public Job(URL url) {
			this.url = url;
		}

		@Override
		public void run() {
			System.out.println(url.getHost());
			try {
				String content = IOUtils.toString(url);
				store.put(url.toString(), content);

			} catch (IOException e) {
				log.warning("Failed to download content from " + url
						+ " due to " + e.getMessage());
			}
		}

	}
}
