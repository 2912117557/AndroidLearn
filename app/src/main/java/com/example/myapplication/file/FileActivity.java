package com.example.myapplication.file;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import android.Manifest;
import android.app.Activity;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.main.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
    }

    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    public void onButton1Click(View view) {
        String fileContents = "Hello world!";
        try (
                FileOutputStream fos1 = openFileOutput("interPriFile", Context.MODE_PRIVATE);
        ) {
            fos1.write(fileContents.getBytes(StandardCharsets.UTF_8));

            File file1 = File.createTempFile("cache-", null, getCacheDir());

            if (!isExternalStorageWritable()) {
                Log.e("file", "No SDCard");
            }

            File file3 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images");
            Log.i("file3", file3.getCanonicalPath());
            boolean res = file3.mkdirs();

            File[] externalStorageVolumes =
                    ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
            File primaryExternalStorage = externalStorageVolumes[1];
            File file4 = new File(primaryExternalStorage, "exterPriFile");
            Log.i("file4", file4.getCanonicalPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Images {
        public final Uri uri;
        public final String name;
        public final int size;
        public Bitmap bitmap;

        public Images(Uri uri, String name, int size) {
            this.uri = uri;
            this.name = name;
            this.size = size;
        }
    }

    public void onButton2Click(View view) {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            grantedProcess();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Log.i("file", "shouldShowRequestPermissionRationale");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            } else {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            } else {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    grantedProcess();
                } else {
                    Log.i("file", "No Access");
                }
        }
    }

    List<Images> imagesList = new ArrayList<Images>();

    public void grantedProcess() {
        ContentResolver resolver = getApplicationContext().getContentResolver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] projection = new String[]{
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE
                };
                Cursor cursor = resolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
                assert cursor != null;
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                int nameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idColumn);
                    String name = cursor.getString(nameColumn);
                    int size = cursor.getInt(sizeColumn);
                    Uri contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    Log.i("file", contentUri.toString());
                    imagesList.add(new Images(contentUri, name, size));
                }
                cursor.close();
                for (int i = 0; i < imagesList.size(); i++) {
                    Images image = imagesList.get(i);
                    Uri contentUri = image.uri;
                    int size = image.size;
                    byte[] bytes = new byte[size];
                    try (InputStream stream = resolver.openInputStream(contentUri)) {
                        assert stream != null;
                        int totalBytesNum = stream.read(bytes);
                        image.bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout linearLayout = findViewById(R.id.fileLinearLayout1);
                        for (int i = 0; i < imagesList.size(); i++) {
                            Images image = imagesList.get(i);
                            Log.i("file", image.name + "  " + image.size + "  " + image.uri);
                            ImageView imageView = new ImageView(FileActivity.this);
                            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                    100, 100));
                            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            imageView.setContentDescription("image");
                            imageView.setImageBitmap(image.bitmap);
                            linearLayout.addView(imageView);
                        }
                    }
                });
                Uri audioCollection;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    audioCollection = MediaStore.Images.Media.getContentUri(
                            MediaStore.VOLUME_EXTERNAL_PRIMARY);
                } else {
                    audioCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                ContentValues newSongDetails = new ContentValues();
                newSongDetails.put(MediaStore.Images.Media.DISPLAY_NAME,
                        "My Song");
                newSongDetails.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    newSongDetails.put(MediaStore.Images.Media.RELATIVE_PATH,
//                            Environment.DIRECTORY_DCIM);
                    newSongDetails.put(MediaStore.Images.Media.IS_PENDING, 1);
                }
                Uri myFavoriteSongUri = resolver
                        .insert(audioCollection, newSongDetails);
                assert myFavoriteSongUri != null;
                try (OutputStream stream = resolver.openOutputStream(myFavoriteSongUri)) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_1);
//                    int bytesCount = bitmap.getByteCount();
//                    ByteBuffer buffer = ByteBuffer.allocate(bytesCount);
//                    bitmap.copyPixelsToBuffer(buffer);
//                    byte[] bytes = buffer.array();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    assert stream != null;
                    stream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    newSongDetails.clear();
                    newSongDetails.put(MediaStore.Audio.Media.IS_PENDING, 0);
                    resolver.update(myFavoriteSongUri, newSongDetails, null, null);
                }

                deleteFile();
            }
        }).start();
    }


    public void deleteFile() {
        ContentResolver resolver = getApplicationContext().getContentResolver();

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Log.i("file", String.valueOf(Environment.isExternalStorageLegacy()));
            }
            Uri imageUri = imagesList.get(0).uri;
            int numImagesRemoved = resolver.delete(
                    imageUri,
                    null,
                    null);
        } catch (SecurityException securityException) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                RecoverableSecurityException recoverableSecurityException;
                if (securityException instanceof RecoverableSecurityException) {
                    recoverableSecurityException =
                            (RecoverableSecurityException) securityException;
                } else {
                    throw new RuntimeException(
                            securityException.getMessage(), securityException);
                }
                IntentSender intentSender = recoverableSecurityException.getUserAction()
                        .getActionIntent().getIntentSender();
                try {
                    startIntentSenderForResult(intentSender, 1,
                            null, 0, 0, 0, null);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException(
                        securityException.getMessage(), securityException);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    deleteFile();
                } else {
                    Log.i("file", "can not delete");
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        Log.i("file", String.valueOf(uri));
                    }
                }
                break;
            case 3:
                if (resultCode == Activity.RESULT_OK) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        Log.i("file", String.valueOf(uri));
                        final int takeFlags = data.getFlags()
                                & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        assert uri != null;
                        getContentResolver().takePersistableUriPermission(uri, takeFlags);
                        try (
                                Cursor cursor = getContentResolver()
                                        .query(uri, null, null, null, null, null);
                        ) {
                            assert cursor != null;
                            Log.i("file", String.valueOf(cursor.getCount()));
                            if (cursor.moveToFirst()) {
                                String displayName = cursor.getString(
                                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                String id = cursor.getString(
                                        cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID));
                                int flags = cursor.getInt(
                                        cursor.getColumnIndex(DocumentsContract.Document.COLUMN_FLAGS));
                                Log.i("file", "Display Name: " + displayName);
                                Log.i("file", "id " + id);
                                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                                String size = null;
                                if (!cursor.isNull(sizeIndex)) {
                                    size = cursor.getString(sizeIndex);
                                } else {
                                    size = "Unknown";
                                }
                                Log.i("file", "Size: " + size);
                                if ((flags & DocumentsContract.Document.FLAG_SUPPORTS_DELETE) != 0) {
                                    DocumentsContract.deleteDocument(getContentResolver(), uri);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
//                    }).start();
                break;
            case 4:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = null;
                    List<Uri> uris = new ArrayList<>();
                    if (data != null) {
                        uri = data.getData();
                        assert uri != null;
                        Log.i("file", String.valueOf(uri));
                        DocumentFile documentFile = DocumentFile.fromTreeUri(this, uri);
                        assert documentFile != null;
                        DocumentFile[] documentFiles = documentFile.listFiles();
                        listFiles(documentFiles, uris);
                        for (Uri uri1 : uris) {
                            Log.i("file", String.valueOf(uri1));
                        }
                    }
                }
                break;
            case 5:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getData();
                        assert uri != null;
                        Log.i("file", String.valueOf(uri));
                        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                        assert cursor != null;
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(nameIndex);
                            int size = cursor.getInt(sizeIndex);
                            byte[] bytes = new byte[size];
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                assert inputStream != null;
                                int totalBytesNum = inputStream.read(bytes);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                ((ImageView) findViewById(R.id.fileImage1)).setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        cursor.close();
                    }
                } else {
                    Log.i("file", "The selected file can't be shared");
                }

                break;
        }
    }

    public void listFiles(DocumentFile[] documentFiles, List<Uri> uris) {
        for (DocumentFile documentFile1 : documentFiles) {
            if (documentFile1.isDirectory()) {
                DocumentFile[] documentFiles1 = documentFile1.listFiles();
                listFiles(documentFiles1, uris);
            } else {
                uris.add(documentFile1.getUri());
            }
        }
    }

    public void onButton3Click(View view) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "invoice.pdf");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri uri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3ADCIM%2FCamera%2FIMG_20201126_152821.jpg");
            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
        }
        startActivityForResult(intent, 2);
    }

    public void onButton4Click(View view) {
        Intent intent2 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("image/*");
        startActivityForResult(intent2, 3);
    }

    public void onButton5Click(View view) {
        Intent intent3 = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent3, 4);
    }

    public void onButton6Click(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 5);
    }

}
