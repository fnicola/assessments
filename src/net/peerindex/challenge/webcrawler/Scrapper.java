package net.peerindex.challenge.webcrawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/* very basic scrapper */
public class Scrapper {

	public static List<String> getText(String html) {
		List<String> strings = new ArrayList<String>();
		
		Document doc = Jsoup.parse(html);
		for (org.jsoup.nodes.Element element : doc.getAllElements()) {
			if (element.hasText() && !element.ownText().trim().isEmpty()) {
				strings.add(element.ownText().trim());
			}
		}
		return strings;
	}
}
