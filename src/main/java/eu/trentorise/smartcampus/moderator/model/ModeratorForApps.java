package eu.trentorise.smartcampus.moderator.model;

import eu.trentorise.smartcampus.profileservice.model.BasicProfile;

public class ModeratorForApps extends Moderator{
	
	private String appId;
	private String clientId;
	private String parentUserId;

	
	public ModeratorForApps() {	
		super();
	}
	
	public ModeratorForApps(BasicProfile user, String app,String parent, long startTime, long endtTime,String clientIdOfApp) {
		super(user,startTime,endtTime);		
		this.appId=app;
		this.parentUserId=parent;		
		this.clientId=clientIdOfApp;
		
	}

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getParentUserId() {
		return parentUserId;
	}
	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
	}


}