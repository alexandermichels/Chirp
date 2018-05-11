package com.development.michels.alexander;

import java.util.ArrayList;

public interface ListUsersHandler
{
    void handleListUsersResponse(ArrayList<User> users);

    void handleListUsersError();
}
