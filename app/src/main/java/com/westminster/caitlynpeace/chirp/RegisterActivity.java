package com.westminster.caitlynpeace.chirp;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RegisterActivity extends AppCompatActivity
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
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent r = getIntent();
        String label = (String) r.getSerializableExtra(LABEL_KEY);
        TextView tv = findViewById(R.id.text);
        tv.setText(label);

        // Get newly registered user's email
        inputEmail = (EditText) findViewById(R.id.userEmail);
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
        inputHandle = (EditText) findViewById(R.id.userHandle);
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
        inputPass = (EditText) findViewById(R.id.userPassword);
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
        confirmPass = (EditText) findViewById(R.id.confirmUserPassword);
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

        registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if (userConfirmPass.equals(userPassword) && userEmail != null && userHandle != null)
                {
                    Intent r = new Intent(RegisterActivity.this, ViewRecentChirpsActivity.class);
                    r.putExtra(RegisterActivity.LABEL_KEY, "View Recent Chirps");
                    startActivity(r);
                }
                else if (userConfirmPass.equals(userPassword))
                {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if (userEmail != null)
                {
                    Toast.makeText(getApplicationContext(), "Please enter an email", Toast.LENGTH_SHORT).show();
                }
                else if (userHandle != null)
                {
                    Toast.makeText(getApplicationContext(), "Please enter a handle", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
