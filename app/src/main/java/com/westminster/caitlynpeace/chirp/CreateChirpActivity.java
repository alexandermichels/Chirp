package com.westminster.caitlynpeace.chirp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateChirpActivity extends AppCompatActivity implements ChirpHandler
{
    private int SELECT_IMAGE;
    private EditText chirpMessage;
    ImageView chirpImage;
    private Button addPhotoButton;
    private Button chirpButton;

    private byte [] image;
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

        chirpImage = findViewById(R.id.createChirp_chirpImage);

        addPhotoButton = findViewById(R.id.createChirp_chirp_photo);
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
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
                    Chirp c = new Chirp(Database.getDatabase().getU().getEmail(), message, image);
                    ServerConnector.get().sendCreateChirpRequest(CreateChirpActivity.this, c, CreateChirpActivity.this);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {
                        Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        int downsampleRatio = Math.min(image.getHeight(), image.getWidth());
                        if (downsampleRatio > 256)
                        {
                            downsampleRatio = 100 * 256/downsampleRatio;
                        }
                        else
                        {
                            downsampleRatio = 100;
                        }
                        image.compress(Bitmap.CompressFormat.JPEG, downsampleRatio, stream);
                        byte[] byteArray = stream.toByteArray();
                        chirpImage.setImageBitmap(image);

                        //downsample image?
                        this.image = byteArray;
                    }
                    catch (IOException e)
                    {
                        Toast.makeText(CreateChirpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        try
        {
            Database.getDatabase().save(this);
        }
        catch (IOException e)
        {

        }
    }
}
