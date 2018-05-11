package com.development.michels.alexander;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements UserHandler
{
    private final String USEREMAIL = "USEREMAIL";
    private final String USERPASSWORD = "USERPASSWORD";
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
        emailEditText = findViewById(R.id.loginActivity_UserEmail);
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
        passwordEditText = findViewById(R.id.loginActivity_UserPassword);
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
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        if (savedInstanceState != null)
        {
            try
            {
                userEmail = savedInstanceState.getString(USEREMAIL);
                userPassword = savedInstanceState.getString(USERPASSWORD);
            }
            catch (Exception e)
            {
                Log.d("Login Activity", "Error pulling from bundle");
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putString(USEREMAIL, userEmail);
        bundle.putString(USERPASSWORD, userPassword);
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
        Toast.makeText(LoginActivity.this, "The server isn't working, try again later", Toast.LENGTH_SHORT).show();
    }
}
