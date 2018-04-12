package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class AddRemoveWatchListActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addremovewatchlist);

        Button add = findViewById(R.id.addUButton);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Toast.makeText(AddRemoveWatchListActivity.this,
                        "User Added!", Toast.LENGTH_LONG).show();
            }
        });

        Button remove = findViewById(R.id.removeUButton);
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Toast.makeText(AddRemoveWatchListActivity.this,
                        "User Removed!", Toast.LENGTH_LONG).show();
            }
        });

        Button l = findViewById(R.id.logout);
        l.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent l = new Intent(AddRemoveWatchListActivity.this, LaunchActivity.class);
                startActivity(l);
                Toast.makeText(AddRemoveWatchListActivity.this,
                        "Logged Out!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
