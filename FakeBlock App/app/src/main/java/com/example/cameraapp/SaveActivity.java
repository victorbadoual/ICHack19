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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.web3j.crypto.CipherException;

import java.io.File;
import java.io.IOException;

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

        if (imageFile.exists()) {
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


        shareButton.setOnClickListener(v -> share());

        saveButton.setOnClickListener(v -> saveImage());

        newPicButton.setOnClickListener(v -> takeNewPic());
    }

    private void share() {

        Drawable mDrawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

        uploadToBlockChain();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), rotatedBitmap, "Image",
                null);
        Uri uri = Uri.parse(path);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/jpeg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        sharingIntent.setType("plain/text");
        String shareBody = "This image can be verified to it's 100% original content thanks to " +
                "TrueBlock.";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this genuine pic!");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void saveImage() {
        saved = true;
        Toast.makeText(SaveActivity.this, "Saved Image: " + imageFile, Toast.LENGTH_SHORT).show();
        uploadToBlockChain();
    }

    private void takeNewPic() {
        if (!saved) {
            boolean deleted = imageFile.delete();
        }
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }


    private void uploadToBlockChain() {
        String hash = null;
        try {
            hash = ImageHasher.getHashForImage(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (hash != null) {
            try {
                BlockPusher.pushToBlock(hash);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
