package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WatchListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        Button edit = findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent e = new Intent(WatchListActivity.this, AddRemoveWatchListActivity.class);
                startActivity(e);
            }
        });


        Button l = findViewById(R.id.logout);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(WatchListActivity.this, LaunchActivity.class);
                startActivity(l);
                Toast.makeText(WatchListActivity.this,
                        "Logged Out!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
