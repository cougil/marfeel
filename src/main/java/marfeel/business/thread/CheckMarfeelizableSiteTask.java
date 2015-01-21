package marfeel.business.thread;

import java.util.List;

import marfeel.controller.dto.Site;
import marfeel.dao.SiteRepository;
import marfeel.parser.MarfeelParser;
import marfeel.parser.MarfeelParserImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CheckMarfeelizableSiteTask implements Runnable {

	private List<Site> sites;
	private marfeel.parser.MarfeelParser parser;
	private String name;
	@Autowired
	private SiteRepository siteRepository;

	private static final Logger log = LoggerFactory
			.getLogger(CheckMarfeelizableSiteTask.class);


	public CheckMarfeelizableSiteTask() {
		this.name = System.currentTimeMillis() + "";
	}

	public CheckMarfeelizableSiteTask(List<Site> sites) {
		this();
		this.sites = sites;
		this.parser = buildParser();
	}

	@Autowired
	private MarfeelParser buildParser() {
		return new MarfeelParserImpl();
	}

	@Override
	public void run() {
		log.debug("Thread [" + name + "] is running");
		for (Site site : sites) {
			String title = this.parser.parseTitle(site);
			boolean isMarfeelizable = this.isSiteMarfeelizable(title);
			log.info("Site: " + site + " isSiteMarfeelizable "
					+ isMarfeelizable);
			this.saveSite(site, isMarfeelizable);
		}
		log.debug("Thread [" + name + "] finished running ");
	}

	private void saveSite(Site dtoSite, boolean isMarfeelizable) {
		marfeel.model.Site site = new marfeel.model.Site(isMarfeelizable,
				dtoSite.getUrl(), dtoSite.getRank());
		site = this.siteRepository.save(site);
		log.info("Site " + site.toString() + " saved!");
		// log.debug("Total sites: " + this.siteRepository.count());
	}

	private boolean isSiteMarfeelizable(String title) {
		return (title != null && title.length() > 0)
				&& (title.toLowerCase().contains("news") || title.toLowerCase()
						.contains("noticias"));
	}
}
