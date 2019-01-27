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

import java.io.File;

public class SaveActivity extends AppCompatActivity {

    private ImageView imageView;
    private File imageFile;
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
        String imagePath = extras.getString(CameraActivity.IMAGE_PATH);
        assert imagePath != null;

        imageFile = new File(imagePath);

        if(imageFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);

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

//    final File file = new File(Environment.getExternalStorageDirectory()+"/pic.jpg");

//    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
//    byte[] bytes = new byte[buffer.capacity()];
//                        buffer.get(bytes);
//    save(bytes);

//    private void save(byte[] bytes) throws IOException {
//        OutputStream output = null;
//        try {
//            output = new FileOutputStream(file);
//            output.write(bytes);
//        } finally {
//            if (null != output) {
//                output.close();
//            }
//        }
//    }

    protected void saveImage() {

    }

    protected void takeNewPic() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

}
