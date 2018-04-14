package com.westminster.caitlynpeace.chirp;

import java.util.Date;

public class Chirp 
{
	private String creatorEmail;
	private Date time;
	private String message;
	
	public Chirp(String email, String m)
	{
		this.creatorEmail = email;
		this.time = new Date();
		this.message = m;
	}
	
	public String getCreator()
	{
		return creatorEmail;
	}
	
	public Date getTime()
	{
		return time;
	}
	
	public void setTime(long i)
	{
		this.time = new Date(i);
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getID()
	{
		return (String)(this.getCreator()+this.getTime().getTime());
	}
}
