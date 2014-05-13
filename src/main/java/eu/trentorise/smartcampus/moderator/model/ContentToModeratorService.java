package eu.trentorise.smartcampus.moderator.model;

import java.io.Serializable;

public class ContentToModeratorService implements Serializable {

	private String _id;
	private boolean keywordApproved;
	private State manualApproved;
	private long timestamp;
	private String webappname;
	private String objectId;
	private String objectText;
	private String note;
	private String userid;
	private String subject;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContentToModeratorService() {
		this.timestamp = System.currentTimeMillis();
		this.setManualApproved(State.WAITING);
		this.setKeywordApproved(true);
	}

	// local validation
	public ContentToModeratorService(String webappname, String objectId,
			String entityText, String userid) {
		this.setWebappname(webappname);
		this.timestamp = System.currentTimeMillis();
		this.setManualApproved(State.WAITING);
		this.setKeywordApproved(true);
		this.setObjectId(objectId);
		this.setObjectText(entityText);
		this.setUserid(userid);
	}

	// remote validation
	public ContentToModeratorService(String webappname, String objectId,
			String entityText, String subject, String userid) {
		this.setWebappname(webappname);
		this.timestamp = System.currentTimeMillis();
		this.setManualApproved(State.WAITING);
		this.setKeywordApproved(true);
		this.setObjectId(objectId);
		this.setObjectText(entityText);
		this.setSubject(subject);
		this.setUserid(userid);
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWebappname() {
		return webappname;
	}

	public void setWebappname(String webappname) {
		this.webappname = webappname;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public boolean isKeywordApproved() {
		return keywordApproved;
	}

	public void setKeywordApproved(boolean keywordApproved) {
		this.keywordApproved = keywordApproved;
	}

	public State getManualApproved() {
		return manualApproved;
	}

	public void setManualApproved(State manualApproved) {
		this.manualApproved = manualApproved;
	}

	public String getObjectText() {
		return objectText;
	}

	public void setObjectText(String objectText) {
		this.objectText = objectText;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getSubject() {
		return subject;
	}
	
	

}