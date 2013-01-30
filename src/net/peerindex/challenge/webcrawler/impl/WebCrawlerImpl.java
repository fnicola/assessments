package net.peerindex.challenge.webcrawler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import net.peerindex.challenge.webcrawler.KeyValueStore;
import net.peerindex.challenge.webcrawler.WebCrawler;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class WebCrawlerImpl implements WebCrawler {

	private Iterator<URL> urlStream;
	private KeyValueStore urlKVStore;

	@Override
	public void setKeyValueStore(KeyValueStore store) {
		this.urlKVStore = store;
	}

	@Override
	public void setURLStream(Iterator<URL> iterator) {
		this.urlStream = iterator;
	}

	@Override
	public void initialise() {
		// Spin up threadpool
	}

	@Override
	public void execute() {
		HttpClient client = new DefaultHttpClient();

		while (urlStream.hasNext()) {
			URL url = urlStream.next();
			String urlString = url.toString();
			try {
				HttpUriRequest request = new HttpGet(url.toURI());
				HttpResponse response = client.execute(request);
				InputStream contentInputStream = response.getEntity()
						.getContent();
				StringWriter writer = new StringWriter();
				IOUtils.copy(contentInputStream, writer,
						Charset.defaultCharset());
				urlKVStore.put(urlString, writer.toString());
				System.out.println("Successfully crawled " + urlString);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void shutdown() {
		// If this were threaded, wait for all threads to join
	}

}
