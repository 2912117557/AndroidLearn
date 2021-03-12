package com.example.myapplication.file;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ShareFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_file);
    }



    public void onButton1Click(View view) {
        File privateRootDir = getExternalFilesDir(null);
        File imagesDir = new File(privateRootDir, "images");
        File[] imageFiles = imagesDir.listFiles();
        assert imageFiles != null;
        File requestFile = imageFiles[0];
        Uri fileUri = null;
        Intent resultIntent = new Intent();
        try {
            fileUri = FileProvider.getUriForFile(
                    this,
                    "com.example.myapplication.fileprovider",
                    requestFile);
        } catch (IllegalArgumentException e) {
            Log.e("File Selector",
                    "The selected file can't be shared: " + requestFile.toString());
        }
        if (fileUri != null) {
            resultIntent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
            resultIntent.setDataAndType(
                    fileUri,
                    getContentResolver().getType(fileUri));
            setResult(Activity.RESULT_OK,
                    resultIntent);
        } else {
            resultIntent.setDataAndType(null, "image/*");
            setResult(RESULT_CANCELED,
                    resultIntent);
        }
        finish();
    }

    public void onButton2Click(View view){
        try {
            File file3 = new File(getExternalFilesDir(null), "images");
            boolean res = file3.mkdirs();
            File file = new File(file3,"ic_4.png");
            OutputStream outputStream = new FileOutputStream(file);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_4);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            outputStream.write(bytes);
            byteArrayOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
