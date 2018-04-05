package com.westminster.caitlynpeace.chirp;

import java.util.ArrayList;

public class User 
{
	
	private String email;
	private String fullName;
	private ArrayList<String> following;
	
	// Gson wants a default constructor
	public User() 
	{

	}
	
	public User(String email, String fullName) {
		this.email = email;
		this.fullName = fullName;
	}
	
	@Override
	public String toString() {
		return email+" "+fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public ArrayList<String> getFollowing() {
		return following;
	}

	public void setFollowing(ArrayList<String> following) {
		this.following = following;
	}
	
	public void addFollowing(String f)
	{
		getFollowing().add(f);
	}

}
