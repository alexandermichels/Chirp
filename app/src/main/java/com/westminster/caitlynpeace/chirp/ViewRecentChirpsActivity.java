package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewRecentChirpsActivity extends AppCompatActivity implements TimelineHandler
{

    private RecyclerView timeline;
    private ChirpAdapter chirpAdapter;
    private Button editWatchingButton;
    private Button createChirpButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chirp_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecentchirps);

        timeline = findViewById(R.id.timeline_recyclerview);
        timeline.setLayoutManager(new LinearLayoutManager(this));

        editWatchingButton = findViewById(R.id.timeline_edit_watch);
        editWatchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(ViewRecentChirpsActivity.this, WatchListActivity.class);
                startActivity(e);
            }
        });

        createChirpButton = findViewById(R.id.timeline_create_chirp);
        createChirpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(ViewRecentChirpsActivity.this, CreateChirpActivity.class);
                startActivity(i);
            }
        });

        updateUI();
    }

    @Override
    public void handleTimelineResponse(Chirp[] timeline)
    {
       for (int i = 0; i < timeline.length-1; i++)
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

       if (chirpAdapter != null)
       {
           chirpAdapter.notifyDataSetChanged();
       }
    }

    @Override
    public void handleTimelineError()
    {
        Toast.makeText(ViewRecentChirpsActivity.this, "Something went very wrong", Toast.LENGTH_SHORT).show();
    }

    public static class ChirpViewHolder extends RecyclerView.ViewHolder
    {
        private TextView creatorTextView;
        private TextView messageTextView;
        private ImageView imageView;
        private int index;

        public ChirpViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.chirp, parent, false));
            creatorTextView = itemView.findViewById(R.id.chirp_creator);
            messageTextView = itemView.findViewById(R.id.chirp_message);
            imageView = itemView.findViewById(R.id.chirp_image);
        }

        public void bind(int i)
        {
            Chirp c = Database.getDatabase().getTimeline().get(i);
            creatorTextView.setText("&" + c.getCreator());
            messageTextView.setText(c.getMessage());
            if (c.getImage().length > 0)
            {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(c.getImage(), 0, c.getImage().length));
            }
            this.index = i;
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
            return Database.getDatabase().getTimelineSize();
        }
    }

    private void updateUI()
    {
        ServerConnector.get().sendTimelineRequest(this, this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout_button:
                Database.getDatabase().logout();
                Intent l = new Intent(ViewRecentChirpsActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(ViewRecentChirpsActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_create_chirp_button:
                Intent i = new Intent(ViewRecentChirpsActivity.this, CreateChirpActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_timeline_button:
                updateUI();
                return true;

            case R.id.menu_watching_button:
                Intent j = new Intent(ViewRecentChirpsActivity.this, WatchListActivity.class);
                startActivity(j);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        try
        {
            Database.getDatabase().save(this);
        }
        catch (IOException e)
        {

        }
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        try
        {
            Database.getDatabase().load(this);
        }
        catch (Exception e)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}

