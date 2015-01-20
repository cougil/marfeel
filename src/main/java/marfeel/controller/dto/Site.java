package marfeel.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Site {
	private String url;
	private long rank;

	public Site() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public long getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return String.format("Site [url=%s, rank=%s]", url, rank);
	}
}