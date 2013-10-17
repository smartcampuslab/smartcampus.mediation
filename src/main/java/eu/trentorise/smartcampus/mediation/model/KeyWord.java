package eu.trentorise.smartcampus.mediation.model;

public class KeyWord {
	
	private String id;
	private String key;
	private boolean enabled;
	

	public KeyWord(String key) {
		this.key=key;
		this.enabled=true;
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


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
