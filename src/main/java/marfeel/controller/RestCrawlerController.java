package marfeel.controller;

import java.util.List;

import marfeel.controller.dto.Site;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCrawlerController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello!";
	}

	@RequestMapping(value = "/getSites", method = RequestMethod.POST)
	public String getSites(@RequestBody List<Site> sites) {
		for (Site site : sites) {
			System.out.println(site);
		}
		return "ok!";
	}
}
