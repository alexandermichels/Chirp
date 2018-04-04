package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // Get user's email
        EditText ue = (EditText) findViewById(R.id.UserEmail);
        Editable userEmail = ue.getText();

        //Get user's password
        EditText up = (EditText) findViewById(R.id.UserPassword);
        Editable userPassword = up.getText();
        
        Button l = findViewById(R.id.LoginButton);
        l.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent l = new Intent(LaunchActivity.this, ViewRecentChirpsActivity.class);
                l.putExtra(RegisterActivity.LABEL_KEY,"View Recent Chirps");
                startActivity(l);
            }
        });


        Button r = findViewById(R.id.RegisterButton);
        r.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent r = new Intent(LaunchActivity.this, RegisterActivity.class);
                r.putExtra(RegisterActivity.LABEL_KEY,"Let's Register for a Chirp Account!");
                startActivity(r);
            }
        });

    }
}
