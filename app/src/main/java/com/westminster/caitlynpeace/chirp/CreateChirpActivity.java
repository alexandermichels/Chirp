package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateChirpActivity extends AppCompatActivity
{
    private EditText chirpMessage;
    private Button addPhotoButton;
    private Button chirpButton;

    private String message;

    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createchirp);

        chirpMessage = findViewById(R.id.createChirp_chirpMessage);
        chirpMessage.addTextChangedListener(new TextWatcher()
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
                message = editable.toString();
            }
        });

        addPhotoButton = findViewById(R.id.createChirp_chirp_photo);
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //open gallery
            }
        });

        chirpButton = findViewById(R.id.createChirp_chirp_button);
        chirpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (message == null)
                {
                    Toast.makeText(CreateChirpActivity.this, "There's nothing to Chirp", Toast.LENGTH_SHORT).show();
                }
                else if (message.length() > 281)
                {
                    Toast.makeText(CreateChirpActivity.this, "You can only Chirp up to 281 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //send chirp to server and update the timeline
                }
            }
        });
    }
}
