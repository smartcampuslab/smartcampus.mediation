package eu.trentorise.smartcampus.moderator.model;

import eu.trentorise.smartcampus.profileservice.model.BasicProfile;

public class Moderator {
	
	private String userId;	
	private long startTime;
	private long endTime;
	private String name;
	private String surname;
	
	public Moderator(BasicProfile profile, long startTime2, long endtTime) {
		this.endTime=endtTime;
		this.startTime=startTime2;
		this.userId=profile.getUserId();
		this.setName(profile.getName());
		this.setSurname(profile.getSurname());
	}
	public Moderator() {
		// TODO Auto-generated constructor stub
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
