package marfeel.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private boolean marfeelizable;
	private String url;
	private long rank;

	public Site() {
	}
	public Site(boolean marfeelizable, String url, long rank) {
		super();
		this.marfeelizable = marfeelizable;
		this.url = url;
		this.rank = rank;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isMarfeelizable() {
		return marfeelizable;
	}

	public void setMarfeelizable(boolean marfeelizable) {
		this.marfeelizable = marfeelizable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return String.format("Site [id=%s, marfeelizable=%s, url=%s, rank=%s]",
				id, marfeelizable, url, rank);
	}
}
