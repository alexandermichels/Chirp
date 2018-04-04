package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by caitlynpeace on 3/23/18.
 */

public class ViewRecentChirpsActivity extends AppCompatActivity {

    public static final String LABEL_KEY = "LABELKEY" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecentchirps);



        Button l = findViewById(R.id.logout);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(ViewRecentChirpsActivity.this, LaunchActivity.class);
                startActivity(l);
                Toast.makeText(ViewRecentChirpsActivity.this,
                        "Logged Out!", Toast.LENGTH_LONG).show();
            }
        });


        Button w = findViewById(R.id.edit_watch);
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(ViewRecentChirpsActivity.this, AddRemoveWatchListActivity.class);
                startActivity(e);
            }
        });

    }
}

