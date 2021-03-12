package com.example.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao{

    @Insert
    void insertAll(User... users);

    @Insert
    void insertOne(User user);

    @Insert
    long[] insertArray(User[] users);

    @Query("SELECT rowid, * FROM user WHERE username=:username")
    LiveData<List<User>> getSpecificOneUser_username(String username);

    @Query("SELECT rowid, * FROM user WHERE username=:username")
    List<User> getSpecificOneUser_username_NoLivedata(String username);

    @Query("SELECT rowid, * FROM user WHERE is_login=:is_login")
    List<User> getSpecificOneUser_isLogin(boolean is_login);


    @Query("SELECT rowid, * FROM user WHERE rowid=:id")
    List<User> getSpecificOneUser_id(int id);

    @Query("SELECT rowid, * FROM user")
    List<User> getAll();

    @Update
    int UpadteSpecificOneUser(User user);

}