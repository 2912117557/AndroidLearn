package com.example.myapplication.file;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Point;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.DocumentsContract.Root;
import android.provider.DocumentsContract.Document;
import android.provider.DocumentsProvider;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class MyDocumentProvider extends DocumentsProvider {
    private Context mContext;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        if (mContext != null) {
            Log.i("DP", mContext.toString());
        }
        return true;
    }
    @Override
    public Cursor queryRoots(String[] projection) throws FileNotFoundException {
        MatrixCursor result =
                new MatrixCursor(resolveRootProjection(projection));
        MatrixCursor.RowBuilder row = result.newRow();
        row.add(Root.COLUMN_ROOT_ID, "rootId");
        row.add(Root.COLUMN_SUMMARY, "summary");
        row.add(Root.COLUMN_FLAGS, Root.FLAG_SUPPORTS_CREATE);
        row.add(Root.COLUMN_TITLE, "title");
        Log.i("PD",mContext.getExternalFilesDir(null).getPath());
        row.add(Root.COLUMN_DOCUMENT_ID, mContext.getExternalFilesDir(null).getPath());
        row.add(Root.COLUMN_ICON, R.drawable.ic_launcher);
        return result;
    }

    private String[] resolveRootProjection(String[] projection) {
        if (projection != null) {
            return projection;
        }
        return new String[]{Root.COLUMN_DOCUMENT_ID, Root.COLUMN_FLAGS,
                Root.COLUMN_ICON, Root.COLUMN_ROOT_ID, Root.COLUMN_SUMMARY,
                Root.COLUMN_TITLE, Root.COLUMN_MIME_TYPES, Root.COLUMN_QUERY_ARGS,
                Root.COLUMN_AVAILABLE_BYTES, Root.COLUMN_CAPACITY_BYTES};
    }

    @Override
    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder) throws FileNotFoundException {
        MatrixCursor result = new
                MatrixCursor(resolveDocumentProjection(projection));
        Log.i("PD",parentDocumentId);
        File parent = new File(parentDocumentId);
        for (File file : Objects.requireNonNull(parent.listFiles())) {
            includeFile(result, null, file);
        }
        return result;
    }

    private String[] resolveDocumentProjection(String[] projection) {
        if (projection != null) {
            return projection;
        }
        return new String[]{Document.COLUMN_DISPLAY_NAME, Document.COLUMN_DOCUMENT_ID, Document.COLUMN_FLAGS,
                Document.COLUMN_ICON, Document.COLUMN_LAST_MODIFIED, Document.COLUMN_MIME_TYPE, Document.COLUMN_SIZE,
                Document.COLUMN_SUMMARY};
    }

    private void includeFile(MatrixCursor result, String documentId, File file1) {
        File file = file1;
        if (documentId != null) {
            file = new File(documentId);
        }
        MatrixCursor.RowBuilder row = result.newRow();
        row.add(Document.COLUMN_DISPLAY_NAME, file.getName());
        row.add(Document.COLUMN_DOCUMENT_ID, file.getPath());
        row.add(Document.COLUMN_SIZE,file.getTotalSpace()-file.getFreeSpace());
        row.add(Document.COLUMN_ICON,R.drawable.panda);
        row.add(Document.COLUMN_LAST_MODIFIED, System.currentTimeMillis());
        if (file.isDirectory()) {
            row.add(Document.COLUMN_FLAGS, Document.FLAG_DIR_SUPPORTS_CREATE);
            row.add(Document.COLUMN_MIME_TYPE, Document.MIME_TYPE_DIR);
        } else {
            row.add(Document.COLUMN_FLAGS, null);
            row.add(Document.COLUMN_MIME_TYPE, "image/*");
        }
    }

    @Override
    public Cursor queryDocument(String documentId, String[] projection) throws FileNotFoundException {
        MatrixCursor result = new
                MatrixCursor(resolveDocumentProjection(projection));
        includeFile(result, documentId, null);
        return result;
    }

    @Override
    public ParcelFileDescriptor openDocument(String documentId, String mode, @Nullable CancellationSignal signal) throws FileNotFoundException {
        File file = new File(documentId);
        int accessMode = ParcelFileDescriptor.parseMode(mode);
        return ParcelFileDescriptor.open(file, accessMode);
    }

    @Override
    public String createDocument(String documentId, String mimeType, String displayName)
            throws FileNotFoundException {
        File parent = new File(documentId);
        File file = new File(parent.getPath(), displayName);
        try {
            file.createNewFile();
            file.setWritable(true);
            file.setReadable(true);
        } catch (IOException e) {
            throw new FileNotFoundException("Failed to create document with name " +
                    displayName +" and documentId " + documentId);
        }
        return file.getPath();
    }

}
