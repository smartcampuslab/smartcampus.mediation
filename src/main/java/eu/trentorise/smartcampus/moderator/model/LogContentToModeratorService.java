package eu.trentorise.smartcampus.moderator.model;

import java.io.Serializable;

public class LogContentToModeratorService implements Serializable {
	
	public static final String ACTION_DELETE="DELETE";
	public static final String ACTION_CHANGE_STATE = "CHANGE";
	public static final String ACTION_ADD = "ADD";

	private ContentToModeratorService contentToModeratorService;
	private long timeDeleted;
	private String byApp;
	private String action;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	
	

	public LogContentToModeratorService(ContentToModeratorService c,String webApp,String action) {
		setContentToModeratorService(c);
		setTimeDeleted(System.currentTimeMillis());
		setByApp(webApp);
		setAction(action);
	}



	public ContentToModeratorService getContentToModeratorService() {
		return contentToModeratorService;
	}



	public void setContentToModeratorService(ContentToModeratorService contentToModeratorService) {
		this.contentToModeratorService = contentToModeratorService;
	}



	public long getTimeDeleted() {
		return timeDeleted;
	}



	public void setTimeDeleted(long timeDeleted) {
		this.timeDeleted = timeDeleted;
	}



	public String getByApp() {
		return byApp;
	}



	public void setByApp(String byApp) {
		this.byApp = byApp;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}

	
	

	

}