package com.example.myapplication.search;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.myapplication.R;
import com.example.myapplication.room.AppDatabase;
import com.example.myapplication.room.User;

import java.util.List;
import java.util.Objects;

import static android.app.SearchManager.SUGGEST_COLUMN_ICON_1;
import static android.app.SearchManager.SUGGEST_COLUMN_ICON_2;
import static android.app.SearchManager.SUGGEST_COLUMN_INTENT_DATA;
import static android.app.SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA;
import static android.app.SearchManager.SUGGEST_COLUMN_TEXT_1;
import static android.app.SearchManager.SUGGEST_COLUMN_TEXT_2;
import static android.provider.BaseColumns._ID;

public class MyCustomSuggestionProvider extends ContentProvider {
    public final static String AUTHORITY = "com.example.myapplication.search.MyCustomSuggestionProvider";
    AppDatabase db;
    List<User> users;


    @Override
    public boolean onCreate() {
        db = Room.databaseBuilder(Objects.requireNonNull(getContext()),
                AppDatabase.class, "myDatabase")
                .fallbackToDestructiveMigration()
                .build();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        assert selectionArgs != null;
        String query = selectionArgs[0];
        Log.i("provide",query);
        try{
            int id = Integer.parseInt(query);
            users = db.userDao().getSpecificOneUser_id(id);
        }catch (NumberFormatException e){
            if(query.equals("true")|query.equals("false")){
                boolean isLogin = Boolean.parseBoolean(query);
                users = db.userDao().getSpecificOneUser_isLogin(isLogin);
            }
            else{
                users = db.userDao().getSpecificOneUser_username_NoLivedata(query);
            }
        }
        Log.i("provide",String.valueOf(users.size()));
        String[] COLUMN_NAME = {_ID, SUGGEST_COLUMN_TEXT_1, SUGGEST_COLUMN_TEXT_2, SUGGEST_COLUMN_ICON_1,
                SUGGEST_COLUMN_INTENT_DATA, SUGGEST_COLUMN_INTENT_EXTRA_DATA,};
        MatrixCursor matrixCursor=new MatrixCursor(COLUMN_NAME);
        for(int i=0;i<users.size();i++){
            User user = users.get(i);
            String  id = String.valueOf(user.getId());
            String username = user.getUsername();
            String isLogin = String.valueOf(user.isIs_login());
            String SUGGEST_COLUMN_TEXT_1 = id;
            String SUGGEST_COLUMN_TEXT_2 = username+" "+isLogin;
            int SUGGEST_COLUMN_ICON_1 = R.drawable.ic_launcher;
            String SUGGEST_COLUMN_INTENT_DATA = id;
            String SUGGEST_COLUMN_INTENT_EXTRA_DATA = username;
            Object[] row = new Object[]{id,SUGGEST_COLUMN_TEXT_1,SUGGEST_COLUMN_TEXT_2,SUGGEST_COLUMN_ICON_1,
                    SUGGEST_COLUMN_INTENT_DATA, SUGGEST_COLUMN_INTENT_EXTRA_DATA};
            matrixCursor.addRow(row);
        }
        return matrixCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


}
