package com.example.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SaveActivity extends AppCompatActivity {

    private ImageView imageView;
    private File imageFile;
    private boolean saved = false;

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

        Button shareButton = findViewById(R.id.share);
        assert shareButton != null;
        Button saveButton = findViewById(R.id.save);
        assert saveButton != null;
        Button newPicButton = findViewById(R.id.newPic);
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

    public String encrypt(byte[] image) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(image);

        byte[] encoded_bytes = md.digest();

        String hash = bytesToHex(encoded_bytes);

        return hash;
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    protected void share() {

        Drawable mDrawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);


        String path = MediaStore.Images.Media.insertImage(getContentResolver(), rotatedBitmap, "Image",
                null);
        Uri uri = Uri.parse(path);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/jpeg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        sharingIntent.setType("plain/text");
        String shareBody = "This image can be verified to it's 100% original content thanks to " +
                "FakeBlock.";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this genuine pic!");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
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
