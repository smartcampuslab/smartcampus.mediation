package eu.trentorise.smartcampus.moderator.model;

import eu.trentorise.smartcampus.profileservice.model.BasicProfile;

public class ModeratorForApps extends Moderator{
	
	private String appId;
	private String clientId;
	private String parentUserId;
	private String _id;

	
	public ModeratorForApps() {	
		super();
	}
	
	public ModeratorForApps(BasicProfile user, String app,String parent,String clientIdOfApp) {
		super(user);		
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

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}


}
