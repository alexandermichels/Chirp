package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateChirpActivity extends AppCompatActivity implements ChirpHandler
{
    private EditText chirpMessage;
    private Button addPhotoButton;
    private Button chirpButton;

    private String message;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chirp_menu, menu);
        return true;
    }

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
                    ServerConnector.get().sendCreateChirpRequest(CreateChirpActivity.this, message, CreateChirpActivity.this);
                }
            }
        });
    }

    @Override
    public void handleChirpResponse()
    {
        //call the getChirps from Server
        Intent i = new Intent(CreateChirpActivity.this, ViewRecentChirpsActivity.class);
        startActivity(i);
    }

    @Override
    public void handleChirpError()
    {
        Toast.makeText(CreateChirpActivity.this, "You fucked something up", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout_button:
                Database.getDatabase().logout();
                Intent l = new Intent(CreateChirpActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(CreateChirpActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_create_chirp_button:
                return true;

            case R.id.menu_timeline_button:
                Intent i = new Intent(CreateChirpActivity.this, ViewRecentChirpsActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_watching_button:
                Intent j = new Intent(CreateChirpActivity.this, WatchListActivity.class);
                startActivity(j);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
