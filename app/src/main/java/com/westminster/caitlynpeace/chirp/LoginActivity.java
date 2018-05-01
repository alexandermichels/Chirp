package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements UserHandler, ListUsersHandler {
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

        try
        {
            Database.load(this);
            if (Database.getDatabase().isAuthenticated())
            {
                startActivity(new Intent(this, ViewRecentChirpsActivity.class));
            }
        }
        catch (Exception e)
        {
            Log.d("Login Activity", "Database won't load");
            Log.d("Login", e.getMessage());
        }


        // Get user's email
        emailEditText = (EditText) findViewById(R.id.loginActivity_UserEmail);
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
        passwordEditText = (EditText) findViewById(R.id.loginActivity_UserPassword);
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
        
        loginButton = findViewById(R.id.loginActivity_LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Database.getDatabase().setU(new User(userEmail, StringUtil.applySha256(userEmail+userPassword), null));
                ServerConnector.get().sendLoginRequest(LoginActivity.this, LoginActivity.this);
            }
        });


        registerButton = findViewById(R.id.loginActivity_RegisterButton);
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

    @Override
    public void handleUserResponse(User u)
    {
        Database.getDatabase().login();
        Database.getDatabase().setU(u);
        startActivity(new Intent(LoginActivity.this, ViewRecentChirpsActivity.class));
    }

    @Override
    public void handleUserError()
    {
        Toast.makeText(LoginActivity.this, "Something went very wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleListUsersResponse(ArrayList<User> users)
    {
        Toast.makeText(this, "Response handlers", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleListUsersError()
    {
        Toast.makeText(this, "Error handlers", Toast.LENGTH_SHORT).show();
    }
}
