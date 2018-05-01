package com.westminster.caitlynpeace.chirp;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.List;
import java.util.Map;

public class ServerConnector {

    private static ServerConnector soleInstance;

    public static ServerConnector get() {
        if (soleInstance == null) soleInstance = new ServerConnector();
        return soleInstance;

    }

    private static final String BASE_URL = "http://10.0.2.2:5000";
    private RequestQueue requestQueue;

    public void sendLoginRequest(Context c, final UserHandler handler) {
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
                        handler.handleUserResponse(u);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Log.d("HTTP Login", "Something when wrong");
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
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

    public void sendRegisterRequest(Context c, final UserHandler handler) {
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
                        handler.handleUserResponse(u);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Database.getDatabase().setU(null);
                        handler.handleUserError();
                        Log.d("HTTP Register", "That didn't work!");
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", Database.getDatabase().getU().getEmail());
                params.put("handle", Database.getDatabase().getU().getHandle());
                params.put("hash", Database.getDatabase().getU().getHash());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void sendTimelineRequest(Context c, final TimelineHandler handler) {
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
                        handler.handleTimelineResponse(chirps);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Log.d("HTTP Timeline", "Something when wrong");
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                Database db = Database.getDatabase();
                params.put("username", db.getU().getEmail());
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

    public void sendListUsersRequest(Context c, final ListUsersHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP List Users", "Response is: "+ response);
                        Gson gson = new Gson();
                        User[] users = gson.fromJson(response, User[].class);
                        handler.handleListUsersResponse(new ArrayList<User>(Arrays.asList(users)));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.handleListUsersError();
                Log.d("HTTP List Users","Something when wrong");
            }
        });

        queue.add(stringRequest);
    }

    public void sendCreateChirpRequest(Context c, final Chirp chirp, final ChirpHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/createChirp";
        CreateChirpRequest stringRequest = new CreateChirpRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP CreateChirp", "Response is: "+ response);
                        handler.handleChirpResponse();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.handleChirpError();
                Log.d("HTTP Create Chirp","Something when wrong");
            }
        }, chirp);

        queue.add(stringRequest);
    }

    public void sendFollowRequest(Context c, final String toFollow, final UpdateFollowingHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/" + toFollow + "/follow";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP To Follow", "Response is: "+ response);
                        handler.handleUpdateFollowingResponse();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.handleUpdateFollowingError();
                Log.d("HTTP To Follow","Something when wrong");
            }
        })
        {
            Map<String, String> params = new HashMap<>();

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                params.put("username", Database.getDatabase().getU().getEmail());
                params.put(":username", toFollow);
                return params;
            }
        }
                ;

        queue.add(stringRequest);
    }

    public void sendUnfollowRequest(Context c, final String toUnfollow, final UpdateFollowingHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/" + toUnfollow + "/unfollow";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP To Unfollow", "Response is: "+ response);
                        Gson gson = new Gson();
                        handler.handleUpdateFollowingResponse();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.handleUpdateFollowingError();
                Log.d("HTTP To Unfollow","Something when wrong");
            }
        })
        {
            Map<String, String> params = new HashMap<>();

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                params.put("username", Database.getDatabase().getU().getEmail());
                params.put(":username", toUnfollow);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public class CreateChirpRequest extends StringRequest
    {
        Map<String, String> params;

        @Override
        public Map<String, String> getParams() throws AuthFailureError
        {
            return params;
        }

        public CreateChirpRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Chirp c)
        {
            super(method, url, listener, errorListener);
            params = new HashMap<>();
            params.put("creator", c.getCreator());
            params.put("message", c.getMessage());
            params.put("image", c.getImage().toString());
        }
    }

    @NonNull
    private RequestQueue getRequestQueue(Context c) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(c);
        return requestQueue;
    }

}