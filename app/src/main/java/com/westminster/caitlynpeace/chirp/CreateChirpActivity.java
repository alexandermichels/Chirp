package com.westminster.caitlynpeace.chirp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by caitlynpeace on 3/26/18.
 */

public class CreateChirpActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createchirp);

        Button l = findViewById(R.id.logout);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(CreateChirpActivity.this, LaunchActivity.class);
                startActivity(l);
                Toast.makeText(CreateChirpActivity.this,
                        "Logged Out!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
