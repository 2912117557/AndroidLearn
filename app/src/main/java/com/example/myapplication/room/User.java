package com.example.myapplication.room;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.io.FileNotFoundException;
import java.io.IOException;

@Fts4
@Entity
public class User {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private int id;

    private String username;

    private boolean is_login;

    public User(String username, boolean is_login) {
        this.username = username;
        this.is_login = is_login;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIs_login() {
        return is_login;
    }

    public void setIs_login(boolean is_login) {
        this.is_login = is_login;
    }

}









