package eu.trentorise.smartcampus.moderator.model;

import eu.trentorise.smartcampus.profileservice.model.BasicProfile;

public class Moderator {
	
	private String userId;	
	private String name;
	private String surname;
	
	public Moderator(BasicProfile profile) {
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
