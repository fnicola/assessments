package net.peerindex.challenge.webcrawler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crawler implements WebCrawler{
	
	private static final Logger LOG = LoggerFactory
	.getLogger(Crawler.class);
	private Iterator<URL> urlIterator;
	private KeyValueStore keyValueStore;
	private static final Integer timeout = 10*1000; // 10s
	
	public Crawler() {
		
	}
	
	private String downloadDocument(URL url) {
		
	    HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		HttpConnectionParams.setSoTimeout(params, timeout);
		HttpClient httpclient = new DefaultHttpClient(params);
		
		HttpResponse response = null;
		String data = null;
		try {
			HttpGet method = new HttpGet(url.toURI());
	    	response = httpclient.execute(method);
			data = new BasicResponseHandler().handleResponse(response);
		} catch (ClientProtocolException e) {
			LOG.error("Error retrieving: {}\n{}", url, e);
            httpclient.getConnectionManager().shutdown();
		} catch (IOException e) {
			LOG.error("Error retrieving: {}\n{}", url, e);
            httpclient.getConnectionManager().shutdown();
		} catch (URISyntaxException e) {
			LOG.error("Error retrieving: {}\n{}", url, e);
		}

		if (response == null) 
		{
			return null;
		}
		
		// FIXME: handle other status codes with content-Length != 0
		//        handle redirects
		if (response.getStatusLine().getStatusCode() != 200) {
			return null;
		}
		
		// FIXME: handle charset 
		Header[] contenType = response.getHeaders("Content-Type");
		
        httpclient.getConnectionManager().shutdown();
		return data;
	}

	@Override
	public void execute() {

		while (urlIterator.hasNext()) 
		{
			URL url = urlIterator.next();
			String data = this.downloadDocument(url);
			keyValueStore.put(url.toString(), data);
		}
	}

	@Override
	public void initialise() {
		//FIXME:
	}

	@Override
	public void setKeyValueStore(KeyValueStore store) {
		keyValueStore = store;
	}

	@Override
	public void setURLStream(Iterator<URL> iterator) {
		this.urlIterator = iterator;
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
