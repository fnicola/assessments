package net.peerindex.challenge.webcrawler.impl;

import java.net.URI;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

import net.peerindex.challenge.webcrawler.ConfigStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import net.peerindex.challenge.webcrawler.KeyValueStore;

public class Fetcher {
	private final PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
	private final HttpClient httpclient = new DefaultHttpClient(cm);
	private final ExecutorService executor;
	private final KeyValueStore store;
	private static final AtomicInteger SEQ = new AtomicInteger();

	public Fetcher(KeyValueStore store) {
		cm.setMaxTotal(ConfigStore.FETCHER_THREAD_NUM);
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(
				ConfigStore.FETCHER_QUEUE_SIZE);
		executor = new ThreadPoolExecutor(ConfigStore.FETCHER_THREAD_NUM,
				ConfigStore.FETCHER_THREAD_NUM, 10, TimeUnit.MINUTES, workQueue);
		this.store = store;
	}

	public void shutdown() {
		try {
			executor.shutdown();
			executor.awaitTermination(100, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			throw new AssertionError("Execution has timed out", e);
		}
		httpclient.getConnectionManager().shutdown();
	}

	public KeyValueStore getStore() {
		return store;
	}

	public void fetch(URI uri) {
		Runnable getTask = new GetTask(httpclient, uri, SEQ.getAndIncrement(),
				store);
		executor.execute(getTask);
	}

	/**
	 * A thread that performs a GET.
	 */
	static class GetTask implements Runnable {

		private final HttpClient httpClient;
		private final HttpContext context;
		private final int id;
		private final URI uri;
		private final KeyValueStore store;

		public GetTask(HttpClient httpClient, URI uri, int id,
				KeyValueStore store) {
			this.httpClient = httpClient;
			this.context = new BasicHttpContext();
			this.uri = uri;
			this.id = id;
			this.store = store;
		}

		/**
		 * Executes the GetMethod and prints some status information.
		 */
		@Override
		public void run() {

			System.out.println(id + " - about to get something from " + uri);
			HttpGet httpget = new HttpGet(uri);

			// TODO error handling
			try {

				// execute the method
				HttpResponse response = httpClient.execute(httpget, context);

				System.out.println(id + " - get executed");
				// get the response body as an array of bytes
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity);
					System.out.println(id + " - " + result.length()
							+ " characters read");
					store.put(uri.toString(), result);
				} else {
					throw new NoResponseException("No response for id:" + id
							+ " uri:" + uri);
				}

			} catch (Exception e) {
				httpget.abort();
				System.out.println(id + " - error: " + e);
			}
		}

	}

}
