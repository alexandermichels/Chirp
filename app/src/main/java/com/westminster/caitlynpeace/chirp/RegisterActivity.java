package com.westminster.caitlynpeace.chirp;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
