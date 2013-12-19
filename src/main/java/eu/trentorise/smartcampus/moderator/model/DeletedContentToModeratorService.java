package eu.trentorise.smartcampus.moderator.model;

import java.io.Serializable;

public class DeletedContentToModeratorService implements Serializable {

	private ContentToModeratorService contentToModeratorService;
	private long timeDeleted;
	private String byApp;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public DeletedContentToModeratorService(ContentToModeratorService c,String webApp) {
		setContentToModeratorService(c);
		setTimeDeleted(System.currentTimeMillis());
		setByApp(webApp);
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

	
	

	

}