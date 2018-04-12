package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    //we want to declare them here so they are fields/instance variables, different scope than a local variable
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get user's email
        emailEditText = (EditText) findViewById(R.id.UserEmail);
        //we need to use a TextWatcher to grab the string anytime they stop typing
        emailEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                userEmail = editable.toString();
            }
        });

        //Get user's password
        passwordEditText = (EditText) findViewById(R.id.UserPassword);
        //Added Text change listener
        passwordEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                userPassword = editable.toString();
            }
        });
        
        loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                //try to log in
                Intent l = new Intent(LoginActivity.this, ViewRecentChirpsActivity.class);
                l.putExtra(ViewRecentChirpsActivity.LABEL_KEY,"View Recent Chirps");
                l.putExtra(ViewRecentChirpsActivity.USER_EMAIL, userEmail);
                l.putExtra(ViewRecentChirpsActivity.USER_PASSWORD, userPassword);
                startActivity(l);
            }
        });


        registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent r = new Intent(LoginActivity.this, RegisterActivity.class);
                r.putExtra(RegisterActivity.LABEL_KEY,"Let's Register for a Chirp Account!");
                startActivity(r);
            }
        });

    }
}
