package marfeel.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import marfeel.controller.dto.Site;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class RestCrawlerControllerTest {

	private MockMvc mvc;
	private RestTemplate restTemplate;
	private String json;

	@Before
	public void setUp() throws IOException {
		mvc = MockMvcBuilders.standaloneSetup(new RestCrawlerController())
				.build();

		restTemplate = new TestRestTemplate();

		Path path = FileSystems.getDefault().getPath("target/test-classes",
				"sites.json");
		// System.out.println(path + " " + path.toAbsolutePath());

		byte[] bytes = Files.readAllBytes(path);
		this.json = new String(bytes);

	}

	@Test
	public void getHello() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/hello")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(is("hello!")));
	}

	@Test
	public void getArrayOfSites() throws Exception {
		
		// mvc.perform(MockMvcRequestBuilders.get("/")
		// .accept(MediaType.APPLICATION_JSON))
		// .andExpect(status().isOk())
		// .andExpect(content().string(is(json)));
				
		MockRestServiceServer mockServer = MockRestServiceServer
				.createServer(restTemplate);
		mockServer.expect(requestTo("/")).andRespond(
				withSuccess(json, MediaType.APPLICATION_JSON));

		ResponseEntity<Site[]> response = restTemplate.getForEntity("/", Site[].class);
		Site[] sites = response.getBody();

		assertTrue(sites.length > 0);

		for (Site site : sites) {
			System.out.println(site);
			assertTrue(new URL("http://" + site.getUrl()) != null);
			assertTrue(site.getRank() > 0);
		}
	}
}
