package com.westminster.caitlynpeace.chirp;

import java.util.ArrayList;

public class User 
{
	
	private String email;
	private String hash;
	private String handle;
	private ArrayList<String> following;
	
	// Gson wants a default constructor
	public User() 
	{

	}
	
	public User(String email, String shaHash, String handle) {
		this.email = email;
		this.hash = shaHash;
		this.handle = handle;
	}
	
	@Override
	public String toString() {
		return email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public ArrayList<String> getFollowing() {
		return following;
	}

	public void setFollowing(ArrayList<String> following) {
		this.following = following;
	}
	
	public void addFollowing(String f)
	{
		if (getFollowing() != null)
		{
			getFollowing().add(f);
		}
		else
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add(f);
			setFollowing(a);
		}
	}
	
	@Override
	public boolean equals(Object o)
	{
		User other = ((User)o);
		if (!this.getEmail().equals(other.getEmail()))
		{
			return false;
		}
		else if (!this.getHandle().equals(other.getHandle()))
		{
			return false;
		}
		else if (!this.getHash().equals(other.getHash()))
		{
			return false;
		}
		else
		{
			if (this.getFollowing() == null && other.getFollowing() == null)
			{
				
			}
			else if (this.getFollowing() == null || other.getFollowing() == null)
			{
				return false;
			}
			else
			{
				for (String s : this.getFollowing())
				{
					boolean matched = false;
					for (String t : other.getFollowing())
					{
						if (s.equals(t))
						{
							matched = true;
						}
					}
					
					if (!matched)
					{
						return false;
					}
				}
			}
		}
		
		return true;
	}

}
