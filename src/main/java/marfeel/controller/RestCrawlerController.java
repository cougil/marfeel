package marfeel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCrawlerController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello!";
	}
}
