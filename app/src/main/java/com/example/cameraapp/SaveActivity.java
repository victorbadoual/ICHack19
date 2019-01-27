package com.example.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SaveActivity extends AppCompatActivity {

    private ImageView imageView;
    private File imageFile;
    private boolean saved = false;
    private Button shareButton;
    private Button saveButton;
    private Button newPicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get intent that started this activity (camera).
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String imagePath = extras.getString(CameraActivity.IMAGE_PATH);
        assert imagePath != null;

        imageFile = new File(imagePath);

        if(imageFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
            imageView.setRotation(90);
        }

        shareButton = findViewById(R.id.share);
        assert shareButton != null;
        saveButton = findViewById(R.id.save);
        assert saveButton != null;
        newPicButton = findViewById(R.id.newPic);
        assert newPicButton != null;


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        newPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeNewPic();
            }
        });
    }

    protected void share() {

    }

    protected void saveImage() {
        saved = true;
        Toast.makeText(SaveActivity.this, "Saved Image: " + imageFile, Toast.LENGTH_SHORT).show();
    }

    protected void takeNewPic() {
        if (!saved) {
            boolean deleted = imageFile.delete();
        }
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

}
