package com.westminster.caitlynpeace.chirp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Database
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
        try
        {
            load(new File(DBFILE));
        }
        catch (Exception e)
        {
            authenticated = false;
        }
        users = new ArrayList<>();
        timeline = new ArrayList<>();
        following = new HashMap<>();
    }

    static void load(File f) throws IOException, ClassNotFoundException
    {
        FileInputStream fileInputStream = new FileInputStream(f);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        soleInstance = (Database) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();
    }


    public void save(File f) throws IOException
    {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(f));
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    public void logout()
    {
        authenticated = false;
    }

    public void login() { authenticated = true; }

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
    

    public boolean isFollowing(String username)
    {
        return following.get(username);
    }

    public void setFollowing(String username, boolean bool)
    {
        following.put(username, bool);
    }
}
