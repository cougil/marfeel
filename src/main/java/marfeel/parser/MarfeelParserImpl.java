package marfeel.parser;

import java.io.IOException;

import marfeel.controller.dto.Site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarfeelParserImpl implements MarfeelParser {

	private static final Logger log = LoggerFactory
			.getLogger(MarfeelParserImpl.class);

	public MarfeelParserImpl() {
	}

	@Override
	public String parseTitle(Site site) {
		Document doc = null;
		String title = null;
		try {
			StringBuffer url = new StringBuffer(site.getUrl());
			if (!site.getUrl().startsWith("http://")) {
				url = url.insert(0, "http://");
			}
			doc = Jsoup.connect(url.toString()).get();
			title = doc.title();
		} catch (IOException e) {
			log.error(e.fillInStackTrace().toString());
		}
		return title;
	}

}
