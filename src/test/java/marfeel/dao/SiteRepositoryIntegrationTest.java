package marfeel.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import marfeel.MainApplication;
import marfeel.model.Site;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplication.class)
@WebAppConfiguration
public class SiteRepositoryIntegrationTest {
	@Autowired
	SiteRepository siteRepository;
	private Site google;
	private Site yahoo;
	private Site microsoft;

	@Before
	public void setUp() {
		this.siteRepository.deleteAll();

		google = new Site(true, "www.google.com", 1);
		this.siteRepository.save(google);
		yahoo = new Site(true, "www.yahoo.com", 2);
		this.siteRepository.save(yahoo);
		microsoft = new Site(true, "www.microsoft.com", 3);
		this.siteRepository.save(microsoft);
	}

	@Test
	public void canFetchGoogle() {
		Site site = this.siteRepository.findOne(1l);
		assertEquals("www.google.com", site.getUrl());
		assertTrue(site.isMarfeelizable());
		assertTrue(site.getRank() > 1);
	}
}
