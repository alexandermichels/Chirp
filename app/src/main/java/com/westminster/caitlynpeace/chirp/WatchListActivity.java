package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class WatchListActivity extends AppCompatActivity implements ListUsersHandler, ListFollowersHandler, UpdateFollowingHandler
{
    private RecyclerView userList;
    private UserAdapter userAdapter;
    private Button timelineButton;
    private Button createChirpButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chirp_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        ServerConnector.get().sendListUsersRequest(this, this);
        ServerConnector.get().sendFollowersRequest(this,this);

        userList = findViewById(R.id.watchlist_recyclerview);
        userList.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

        timelineButton = findViewById(R.id.watchlist_timeline_button);
        timelineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(WatchListActivity.this, ViewRecentChirpsActivity.class);
                startActivity(i);
            }
        });

        createChirpButton = findViewById(R.id.watchlist_create_chirp_button);
        createChirpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(WatchListActivity.this, CreateChirpActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void handleUpdateFollowingResponse()
    {
        updateUI();
    }

    @Override
    public void handleUpdateFollowingError()
    {
        Toast.makeText(this, "Fuck you dude", Toast.LENGTH_SHORT).show();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder
    {
        private TextView handleTextView;
        private Button followButton;
        private User u;
        private int index;

        public UserViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.chirp, parent, false));
            handleTextView = itemView.findViewById(R.id.chirp_creator);
            followButton = itemView.findViewById(R.id.chirp_message);
            followButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    if (Database.getDatabase().isFollowing(u.getEmail()))
                    {
                        //unfollow
                    }
                    else
                    {
                        //follow
                    }
                }
            });
        }

        public void bind(int index)
        {
            u = Database.getDatabase().getUsers().get(index);
            handleTextView.setText("&" + u.getHandle());
            if (Database.getDatabase().isFollowing(u.getEmail()))
            {
                followButton.setText("Unfollow");
            }
            else
            {
                followButton.setText("Follow");
            }
            this.index = index;
        }
    }

    public class UserAdapter extends RecyclerView.Adapter<WatchListActivity.UserViewHolder>
    {
        @Override
        public WatchListActivity.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(WatchListActivity.this);
            return new WatchListActivity.UserViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(WatchListActivity.UserViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return Database.getDatabase().getUsers().size();
        }
    }

    private void updateUI()
    {
        ServerConnector.get().sendListUsersRequest(this, this);
        ServerConnector.get().sendFollowersRequest(this, this);
        if (userAdapter == null)
        {
            userAdapter = new UserAdapter();
            userList.setAdapter(userAdapter);
        }
        else
        {
            userAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void handleListUsersResponse(ArrayList<User> users)
    {
        Database.getDatabase().setUsers(users);
    }

    @Override
    public void handleListUsersError()
    {
        Toast.makeText(WatchListActivity.this, "Something fucked up", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleFollowersResponse(ArrayList<String> users)
    {
        Database.getDatabase().reconcileFollowing(users);
    }

    @Override
    public void handleFollowersError()
    {
        Toast.makeText(WatchListActivity.this, "You fucked up", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout_button:
                Database.getDatabase().logout();
                Intent l = new Intent(WatchListActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(WatchListActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_create_chirp_button:
                Intent k = new Intent(WatchListActivity.this, CreateChirpActivity.class);
                startActivity(k);
                return true;

            case R.id.menu_timeline_button:
                Intent i = new Intent(WatchListActivity.this, ViewRecentChirpsActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_watching_button:
                updateUI();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
