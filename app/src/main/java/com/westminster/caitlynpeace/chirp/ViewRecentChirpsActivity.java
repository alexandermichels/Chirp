package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewRecentChirpsActivity extends AppCompatActivity {

    private RecyclerView timeline;
    private ChirpAdapter chirpAdapter;
    private LinearLayoutManager timelineManager;
    private Button logoutButton;
    private Button editWatchingButton;
    private Button createChirpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecentchirps);

        ServerConnector.get().sendTimelineRequest(this);
        timeline = findViewById(R.id.timeline_recyclerview);
        timelineManager = new LinearLayoutManager(this);
        timeline.setLayoutManager(timelineManager);
        updateUI();

        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database.getDatabase().logout();
                Intent l = new Intent(ViewRecentChirpsActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(ViewRecentChirpsActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
            }
        });


        editWatchingButton = findViewById(R.id.edit_watch);
        editWatchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(ViewRecentChirpsActivity.this, AddRemoveWatchListActivity.class);
                startActivity(e);
            }
        });

        createChirpButton = findViewById(R.id.create_chirp);
        createChirpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(ViewRecentChirpsActivity.this, CreateChirpActivity.class);
                startActivity(i);
            }
        });
    }

    public static class ChirpViewHolder extends RecyclerView.ViewHolder
    {
        private TextView creatorTextView;
        private TextView messageTextView;
        private int index;

        public ChirpViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.chirp, parent, false));
            creatorTextView = itemView.findViewById(R.id.chirp_creator);
            messageTextView = itemView.findViewById(R.id.chirp_message);
            //set on click listener later?
        }

        public void bind(int index)
        {
            Chirp c = Database.getDatabase().getTimeline().get(index);
            creatorTextView.setText("&" + c.getCreator());
            messageTextView.setText(c.getMessage());
            this.index = index;
        }
    }

    public class ChirpAdapter extends RecyclerView.Adapter<ChirpViewHolder>
    {
        @Override
        public ChirpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(ViewRecentChirpsActivity.this);
            return new ChirpViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(ChirpViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return Database.getDatabase().getTimeline().size();
        }
    }

    private void updateUI()
    {
        ServerConnector.get().sendTimelineRequest(this);
        if (chirpAdapter == null)
        {
            chirpAdapter = new ChirpAdapter();
            timeline.setAdapter(chirpAdapter);
        }
        else
        {
            chirpAdapter.notifyDataSetChanged();
        }
    }
}

