package com.example.myapplication.room;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MyViewModel extends ViewModel {
    private LiveData<List<User>> users;
    int i=1;
    AppDatabase db;

    public MyViewModel(AppDatabase db){
        this.db=db;
        new Thread(new Runnable() {
            @Override
            public void run() {
                users=db.userDao().getSpecificOneUser_username("b");
            }
        }).start();
    }

    public LiveData<List<User>> getUsers()
    {
        return users;
    }

    Handler handler =new Handler();
    Thread a;
    public void ToastTest(){

        a=new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    Log.i("viewModel",String.valueOf(i++));
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Log.i("viewModel","exception");
                        break;
                    }
                }
                Log.i("viewModel","stop");
            }
        });
       a.start();

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("viewModel","clear");
//        a.interrupt();
    }
}
