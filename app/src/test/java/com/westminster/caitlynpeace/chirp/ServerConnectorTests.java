package com.westminster.caitlynpeace.chirp;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class ServerConnectorTests
{
    Context context;

    @Before
    public void init()
    {
        context = new RegisterActivity();
    }

    @Test
    public void registerUserWithServer()
    {
        User u = new User("test@example.com", StringUtil.applySha256("test@example.compassword"), "test");
        Database.getDatabase().setU(u);
        ServerConnector.get().sendRegisterRequest(context);
        Database db = Database.getDatabase();
        assert(db.getU().getEmail().equals("test@example.com"));
        assert(db.getU().getHandle().equals("test"));
        assert(db.getU().getHash().equals(StringUtil.applySha256("test@example.compassword")));
    }


}
