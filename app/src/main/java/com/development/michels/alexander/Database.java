package com.development.michels.alexander;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Database implements Serializable
{
    private static final String DBFILE = "DATABASE_text_file";
    private boolean authenticated;
    private User u;

    ArrayList<User> users;
    ArrayList<Chirp> timeline;
    HashMap<String, Boolean> following;

    private static Database soleInstance;

    public static Database getDatabase()
    {
        if (soleInstance == null)
        {
            soleInstance = new Database();
        }
        return soleInstance;
    }

    private Database()
    {
        users = new ArrayList<>();
        timeline = new ArrayList<>();
        following = new HashMap<>();
    }

    static void load(Context c) throws IOException, ClassNotFoundException
    {
        FileInputStream fileInputStream = new FileInputStream(new File(c.getFilesDir(), getDBFile()));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        soleInstance = (Database) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();
    }


    public void save(Context c) throws IOException
    {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(c.getFilesDir(), getDBFile())));
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    public void logout()
    {
        authenticated = false;
    }

    public void login() { authenticated = true; }

    public static String getDBFile()
    {
        return DBFILE;
    }

    public ArrayList<User> getUsers()
    {

        return users;
    }

    public void setUsers(ArrayList<User> users)
    {

        this.users = users;
    }

    public boolean isAuthenticated()
    {

        return authenticated;
    }

    public String getUsername()
    {

        return getU().getEmail();
    }

    public String getHash()
    {

        return getU().getHash();
    }

    public User getU()
    {

        return u;
    }

    public void setU(User u)
    {

        this.u = u;
    }

    public ArrayList<Chirp> getTimeline()
    {

        return timeline;
    }

    public void setTimeline(ArrayList<Chirp> timeline)
    {

        this.timeline = timeline;
    }

    public int getTimelineSize()
    {
        return getTimeline().size();
    }

    public boolean isFollowing(String username)
    {
        return following.get(username);
    }

    public void setFollowing(String username, boolean bool)
    {
        following.put(username, bool);
    }

    public void reconcileFollowing()
    {
        for (User u : this.getUsers())
        {
            boolean isFollowing = false;
            loop: for (String s : getDatabase().getU().getFollowing())
            {
                if (u.getEmail().equals(s))
                {
                    isFollowing = true;
                    break loop;
                }
            }
            Database.getDatabase().setFollowing(u.getEmail(),isFollowing);
        }
    }
}
