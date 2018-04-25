package com.westminster.caitlynpeace.chirp;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RegisterActivity extends AppCompatActivity implements UserHandler
{
    public static final String LABEL_KEY = "LABELKEY" ;
    private EditText inputEmail;
    private EditText inputHandle;
    private EditText inputPass;
    private EditText confirmPass;
    private Button registerButton;

    private String userEmail;
    private String userHandle;
    private String userPassword;
    private String userConfirmPass;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chirp_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get newly registered user's email
        inputEmail = (EditText) findViewById(R.id.registerActivity_userEmail);
        inputEmail.addTextChangedListener(new TextWatcher()
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

        //Get newly registered user's handle
        inputHandle = (EditText) findViewById(R.id.registerActivity_userHandle);
        inputHandle.addTextChangedListener(new TextWatcher()
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
                userHandle = editable.toString();
            }
        });

        // Get newly registered users' password
        inputPass = (EditText) findViewById(R.id.registerActivity_userPassword);
        inputPass.addTextChangedListener(new TextWatcher()
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

        //Get confirmed password & check if it is identical
        confirmPass = (EditText) findViewById(R.id.registerActivity_confirmUserPassword);
        confirmPass.addTextChangedListener(new TextWatcher()
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
                userConfirmPass = editable.toString();
            }
        });

        registerButton = findViewById(R.id.registerActivity_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if (isValidUser())
                {
                    String h = StringUtil.applySha256(userEmail+userPassword);
                    Database.getDatabase().setU(new User(userEmail, h, userHandle));
                    ServerConnector.get().sendRegisterRequest(RegisterActivity.this, RegisterActivity.this);
                }
            }
        });

    }

    public boolean isValidUser()
    {
        if (userEmail == null)
        {
            Toast.makeText(RegisterActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (userHandle == null)
        {
            Toast.makeText(RegisterActivity.this, "Please enter a handle", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (userPassword == null)
        {
            Toast.makeText(RegisterActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (userConfirmPass == null)
        {
            Toast.makeText(RegisterActivity.this, "Please enter a confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (userHandle.length() > 12)
        {
            Toast.makeText(RegisterActivity.this, "Your handle can be at most 12 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (userPassword.length() > 24)
        {
            Toast.makeText(RegisterActivity.this, "Your password can be at most 24 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!userPassword.equals(userConfirmPass))
        {
            Toast.makeText(RegisterActivity.this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            for (char c : userHandle.toCharArray())
            {
                if (!Character.isLetterOrDigit(c))
                {
                    Toast.makeText(RegisterActivity.this, "Your handle must be alpha-numeric", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void handleUserResponse(User u)
    {
        Database.getDatabase().logout();
        Database.getDatabase().setU(u);
        Intent r = new Intent(RegisterActivity.this, ViewRecentChirpsActivity.class);
        startActivity(r);
    }

    @Override
    public void handleUserError()
    {
        Toast.makeText(RegisterActivity.this, "Something went very wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout_button:
                Database.getDatabase().logout();
                Intent l = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(RegisterActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
