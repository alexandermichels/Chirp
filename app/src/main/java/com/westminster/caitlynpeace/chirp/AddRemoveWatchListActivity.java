package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AddRemoveWatchListActivity extends AppCompatActivity
{
    private RecyclerView userList;
    private LinearLayoutManager userManager;
    private UserAdapter userAdapter;
    private Button timelineButton;
    private Button createChirpButton;
    private Button logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addremovewatchlist);

        userList = findViewById(R.id.editwatchlist_recyclerview);
        userManager = new LinearLayoutManager(this);
        userList.setLayoutManager(userManager);
        updateUI();

        timelineButton = findViewById(R.id.editwatchlist_timeline_button);
        timelineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AddRemoveWatchListActivity.this, ViewRecentChirpsActivity.class);
                startActivity(i);
            }
        });

        createChirpButton = findViewById(R.id.editwatchlist_create_chirp_button);
        createChirpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AddRemoveWatchListActivity.this, CreateChirpActivity.class);
                startActivity(i);
            }
        });

        logoutButton = findViewById(R.id.editwatchlist_logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Database.getDatabase().logout();
                Intent l = new Intent(AddRemoveWatchListActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(AddRemoveWatchListActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
            }
        });

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
                        //update to remove from list
                    }
                    else
                    {
                        //update to add to list
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

    public class UserAdapter extends RecyclerView.Adapter<AddRemoveWatchListActivity.UserViewHolder>
    {
        @Override
        public AddRemoveWatchListActivity.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(AddRemoveWatchListActivity.this);
            return new AddRemoveWatchListActivity.UserViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(AddRemoveWatchListActivity.UserViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return Database.getDatabase().getUsers().size();
        }
    }

    private void updateUI()
    {
        ServerConnector.get().sendListUsersRequest(this);
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
}
