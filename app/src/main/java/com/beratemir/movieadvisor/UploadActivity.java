package com.beratemir.movieadvisor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    ImageView onizlemeView;

Bitmap selectedImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        onizlemeView = findViewById(R.id.onizlemeView);



    }

    public void selectFromGallery(View view) {

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },1);
        } else {
            pickImage();

        }
    }

    private void pickImage() {
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(this, "İzin Reddedildi ..!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

if(resultCode == 2 && requestCode==RESULT_OK && data!=null){

    Uri imgData=data.getData();
    try {
        if(Build.VERSION.SDK_INT>=28){
            ImageDecoder.Source source=ImageDecoder.createSource(this.getContentResolver(),imgData);
            selectedImg=ImageDecoder.decodeBitmap(source);
            onizlemeView.setImageBitmap(selectedImg);
        }else{
            selectedImg= MediaStore.Images.Media.getBitmap(this.getContentResolver(),imgData);
            onizlemeView.setImageBitmap(selectedImg);
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

}
super.onActivityResult(requestCode,resultCode,data);
    }



}