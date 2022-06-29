package com.rb.core.picturechoose;

import static com.rb.core.picturechoose.PicturePickUtil.CROP_PHOTO;
import static com.rb.core.picturechoose.PicturePickUtil.PICK_PHOTO;
import static com.rb.core.picturechoose.PicturePickUtil.TAKE_PHOTO;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class PictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoAlbum.choosePicture(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            finish();
            overridePendingTransition(0, 0);
        }

        if ((requestCode == PICK_PHOTO || requestCode == CROP_PHOTO || requestCode == TAKE_PHOTO)) {
            PhotoAlbum.getPhoto(requestCode, resultCode, this, data, new PhotoAlbum.CamerabakListener() {
                @Override
                public void getFile(File file) {
                    PicturePickUtil.pickResult(file);
                    finish();
                }
            });
        }

    }
}
