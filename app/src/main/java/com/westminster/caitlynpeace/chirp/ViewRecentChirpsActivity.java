package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by caitlynpeace on 3/23/18.
 */

public class ViewRecentChirpsActivity extends AppCompatActivity {

    public static final String LABEL_KEY = "LABELKEY" ;
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PASSWORD = "USER_PASSWORD";

    //just a change of scope local variables->instance variables
    private RecyclerView timeline;
    private LinearLayoutManager timelineManager;
    private
    private Button logoutButton;
    private Button editWatchingButton;

    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecentchirps);

        timeline = findViewById(R.id.timeline_recyclerview);


        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(ViewRecentChirpsActivity.this, LaunchActivity.class);
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

    }
}

