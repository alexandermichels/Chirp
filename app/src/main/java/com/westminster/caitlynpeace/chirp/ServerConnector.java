package com.westminster.caitlynpeace.chirp;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServerConnector {

    private static ServerConnector soleInstance;

    public static ServerConnector get() {
        if (soleInstance == null) soleInstance = new ServerConnector();
        return soleInstance;

    }

    private static final String BASE_URL = "http://10.43.8.75:5010";
    private RequestQueue requestQueue;

    public void sendLoginRequest(Context c) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/login";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP Login", "Response is: "+ response);
                        Gson gson = new Gson();
                        User u = gson.fromJson(response, User.class);
                        UserHandler handler = new UserHandler();
                        handler.handleResponse(u);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Log.d("HTTP Login", error.getMessage());
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                Database db = Database.getDatabase();
                params.put("username", db.getUsername());
                params.put("hash", db.getHash());
                if (db.isAuthenticated())
                {
                    params.put("authenticated", "true");
                }
                else
                {
                    params.put("authenticated", "false");
                }
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void sendRegisterRequest(Context c) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/register";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP Register", "Response is: "+ response);
                        Gson gson = new Gson();
                        User u = gson.fromJson(response, User.class);
                        UserHandler handler = new UserHandler();
                        handler.handleResponse(u);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Database.getDatabase().setU(null);
                        Log.d("HTTP Register", "That didn't work!");
                        Log.d("HTTP Register", error.getMessage());
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                Database db = Database.getDatabase();
                params.put("username", db.getU().getEmail());
                params.put("handle", db.getU().getHandle());
                params.put("hash", db.getU().getHash());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void sendTimelineRequest(Context c) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP Timeline", "Response is: "+ response);
                        Gson gson = new Gson();
                        Chirp[] chirps = gson.fromJson(response, Chirp[].class);
                        TimelineHandler handler = new TimelineHandler();
                        handler.handleResponse(chirps);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Log.d("HTTP Timeline", error.getMessage());
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                Database db = Database.getDatabase();
                params.put("username", db.getUsername());
                params.put("hash", db.getHash());
                if (db.isAuthenticated())
                {
                    params.put("authenticated", "true");
                }
                else
                {
                    params.put("authenticated", "false");
                }
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void sendListUsersRequest(Context c) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP List Users", "Response is: "+ response);
                        Gson gson = new Gson();
                        User[] users = gson.fromJson(response, User[].class);
                        ListUsersResponseHandler handler = new ListUsersResponseHandler();
                        handler.handleResponse(new ArrayList<User>(Arrays.asList(users)));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP List Users",error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    @NonNull
    private RequestQueue getRequestQueue(Context c) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(c);
        return requestQueue;
    }

    public class ListUsersResponseHandler
    {
        public void handleResponse(ArrayList<User> users)
        {
            Database.getDatabase().setUsers(users);
        }
    }

    public class UserHandler
    {
        public void handleResponse(User u)
        {
            Database.getDatabase().setU(u);
        }
    }

    public class TimelineHandler
    {
        public void handleResponse(Chirp [] timeline)
        {
            for (int i = 0; i < timeline.length; i++)
            {
                int newest = i;
                for (int j = i+1; j < timeline.length; j++)
                {
                    if (timeline[j].getTime().before(timeline[newest].getTime()))
                    {
                        newest = j;
                    }
                }
                Chirp temp = timeline[newest];
                timeline[newest] = timeline[i];
                timeline[i] = temp;
            }

            Database.getDatabase().setTimeline(new ArrayList<Chirp>(Arrays.asList(timeline)));
        }
    }

}