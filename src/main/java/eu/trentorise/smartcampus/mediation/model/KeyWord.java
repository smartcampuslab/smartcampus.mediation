package eu.trentorise.smartcampus.mediation.model;

import java.util.ArrayList;
import java.util.List;

public class KeyWord {
	
	private String id;
	private String key;	
	private long timestamp;
	private List<String> apps;
	
	public KeyWord() {
		
	}

	public KeyWord(String key,String app) {
		this.apps=new ArrayList<String>();
		this.key=key;
		this.apps.add(app);
		this.timestamp=System.currentTimeMillis();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	

	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public List<String> getApps() {
		return apps;
	}


	public void setApps(List<String> apps) {
		this.apps = apps;
	}
	



}
