package com.westminster.caitlynpeace.chirp;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RegisterActivity extends AppCompatActivity
{
    public static final String LABEL_KEY = "LABELKEY" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent r = getIntent();
        String label = (String) r.getSerializableExtra(LABEL_KEY);
        TextView tv = findViewById(R.id.text);
        tv.setText(label);

        // Get newly registered user's email
        EditText inputEmail = (EditText) findViewById(R.id.userEmail);
        Editable userEmail = inputEmail.getText();

        //Get newly registered user's handle
        EditText inputHandle = (EditText) findViewById(R.id.userHandle);
        Editable userHandle = inputEmail.getText();

        // Get newly registered users' password
        EditText inputPass = (EditText) findViewById(R.id.userPassword);
        Editable userPassword = inputPass.getText();

        //Get confirmed password & check if it is identical
        EditText confirmPass = (EditText) findViewById(R.id.confirmUserPassword);
        Editable confPass = inputPass.getText();

        if(!inputPass.equals(confirmPass))
        {
            Toast.makeText(RegisterActivity.this,
                    "Passwords do NOT match!", Toast.LENGTH_LONG).show();
        }
        else if(inputPass.equals(confirmPass))
        {
            Button reg = findViewById(R.id.RegisterButton);
            reg.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    Intent r = new Intent(RegisterActivity.this, ViewRecentChirpsActivity.class);
                    r.putExtra(RegisterActivity.LABEL_KEY,"View Recent Chirps");
                    startActivity(r);
                }
            });
        }

        
    }
}
