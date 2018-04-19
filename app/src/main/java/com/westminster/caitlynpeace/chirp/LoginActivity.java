package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                Database db = Database.getDatabase();
                db.setUsername(userEmail);
                db.setHash(StringUtil.applySha256(userEmail+userPassword));
                ServerConnector.get().sendLoginRequest(LoginActivity.this);
                db = Database.getDatabase();
                if (db.getU() == null)
                {
                    emailEditText.setText("");
                    passwordEditText.setText("");
                    userEmail = "";
                    userPassword = "";
                    Toast.makeText(LoginActivity.this, "Login attempt failed, please try again", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent l = new Intent(LoginActivity.this, ViewRecentChirpsActivity.class);
                    startActivity(l);
                }
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
}
