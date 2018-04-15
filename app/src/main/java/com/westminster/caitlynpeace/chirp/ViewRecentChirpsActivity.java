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

    public static final String LABEL_KEY = "LABELKEY" ;
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PASSWORD = "USER_PASSWORD";

    private String userEmail;
    private String userPassword;

    //just a change of scope local variables->instance variables
    private RecyclerView timeline;
    private ChirpAdapter chirpAdapter;
    private LinearLayoutManager timelineManager;
    private Button logoutButton;
    private Button editWatchingButton;
    private Button createChirpButton;

    private static ArrayList<Chirp> chirps;


    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecentchirps);


        getChirps();
        timeline = findViewById(R.id.timeline_recyclerview);
        timelineManager = new LinearLayoutManager(this);
        timeline.setLayoutManager(timelineManager);
        updateUI();

        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(ViewRecentChirpsActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(ViewRecentChirpsActivity.this,
                        "Logged Out!", Toast.LENGTH_LONG).show();
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

    public static void getChirps()
    {
        //figure this out.
        ArrayList<Chirp> cs = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            cs.add(new Chirp("test" + i + "@example.com", "Random words"));
            cs.add(new Chirp("test" + i + "@example.com", "I really like &test" + ((i+4)%10) + ", they're a cool person"));
            cs.add(new Chirp("test" + i + "@example.com", "&test" + ((i+7)%10) + " is an asshole"));
            cs.add(new Chirp("test" + i + "@example.com", "Someone really boring: &test" + ((i+2)%10)));
        }

        chirps = cs;
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
            Chirp c = chirps.get(index);
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
            return chirps.size();
        }
    }

    private void updateUI()
    {
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

