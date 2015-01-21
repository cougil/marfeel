package marfeel.controller;

import java.util.List;

import marfeel.controller.dto.Site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCrawlerController {

	private static final Logger log = LoggerFactory
			.getLogger(RestCrawlerController.class);

	@RequestMapping("/hello")
	public String hello() {
		return "hello!";
	}

	@RequestMapping(value = "/getSites", method = RequestMethod.POST)
	public String getSites(@RequestBody List<Site> sites) {
		for (Site site : sites) {
			log.debug(site.toString());
		}
		return "ok!";
	}
}
