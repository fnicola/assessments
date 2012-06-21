package net.peerindex.challenge.webcrawler.impl;

import java.util.List;

public class URLRecord {
	public String url;
	public List<Integer> top;

	@Override
	public String toString() {
		return "url:" + url + " top: " + top;
	}
}
