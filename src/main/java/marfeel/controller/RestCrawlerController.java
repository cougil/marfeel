package marfeel.controller;

import java.util.List;

import marfeel.business.MarfeelizableService;
import marfeel.controller.dto.Site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCrawlerController {

	private static final String OK = "ok";

	private static final Logger log = LoggerFactory
			.getLogger(RestCrawlerController.class);

	@Autowired
	MarfeelizableService marfeelizableService;

	@RequestMapping("/hello")
	public String hello() {
		return "hello!";
	}

	@RequestMapping(value = "/getSites", method = RequestMethod.POST)
	public String getSites(@RequestBody List<Site> sites) {
		log.debug(sites != null ? sites.size() + " sites" : "no sites");
		marfeelizableService.checkSites(sites);
		return OK;
	}
}
