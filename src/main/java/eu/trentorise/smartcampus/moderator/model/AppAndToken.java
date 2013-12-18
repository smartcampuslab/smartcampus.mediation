package eu.trentorise.smartcampus.moderator.model;

public class AppAndToken {
	
	private String appId;
	private String appToken;
	
	public AppAndToken() {
	
	}
	
	public AppAndToken(String appId2, String token) {
		this.appId=appId2;
		this.appToken=token;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppToken() {
		return appToken;
	}
	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

}
