package eu.trentorise.smartcampus.moderator.model;

import java.util.ArrayList;
import java.util.List;

public class KeyWord {

	private String id;
	private String keyword;
	private long timeupdate;
	private List<String> apps;

	public KeyWord() {

	}

	public KeyWord(String key, String app) {
		this.apps = new ArrayList<String>();
		this.setKeyword(key);
		this.apps.add(app);
		this.setTimeupdate(System.currentTimeMillis());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getApps() {
		return apps;
	}

	public void setApps(List<String> apps) {
		this.apps = apps;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public long getTimeupdate() {
		return timeupdate;
	}

	public void setTimeupdate(long timeupdate) {
		this.timeupdate = timeupdate;
	}

}
