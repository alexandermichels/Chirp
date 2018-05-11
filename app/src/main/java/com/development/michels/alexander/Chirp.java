package com.development.michels.alexander;

import java.io.Serializable;
import java.util.Date;

public class Chirp implements Serializable
{
	private String creatorEmail;
	private Date time;
	private String message;
	private byte [] image;
	
	public Chirp(String email, String m, byte [] image)
	{
		this.creatorEmail = email;
		this.time = new Date();
		this.message = m;
		this.image = image;
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

	public byte [] getImage() { return image; }
}
