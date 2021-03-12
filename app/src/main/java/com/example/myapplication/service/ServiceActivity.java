package com.example.myapplication.service;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;

public class ServiceActivity extends AppCompatActivity {
    BackgroundService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BackgroundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.i("serviceActivity","connected");
            BackgroundService.LocalBinder binder = (BackgroundService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.setCallback(new BackgroundService.MyCallback() {
                @Override
                public void sendMessage(String data) {
                    Log.i("serviceActivity",data);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("serviceActivity","disConnected");
            mBound = false;
        }
    };

    public void onButton1Click(View view){
        Intent intent = new Intent(this, BackgroundService.class);
        intent.putExtra("type",1);
        startService(intent);
    }

    public void onButton2Click(View view){
        Intent intent = new Intent(this, BackgroundService.class);
        stopService(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onButton3Click(View view){
        Intent intent = new Intent(this, BackgroundService.class);
        intent.putExtra("type",2);
        startForegroundService(intent);
    }

    public void onButton4Click(View view){
        Intent intent = new Intent(this, BackgroundService.class);
        intent.putExtra("type",3);
        startService(intent);
    }

    public void onButton5Click(View v) {
        if (mBound) {
            mService.stopFore();
        }
    }

    public void onButton6Click(View v) {
        unbindService(connection);
        mBound = false;
    }

    public void onButton7Click(View v) {
        if(mBound){
            mService.updateNot("new Text");
        }
    }

    public void onButton8Click(View v) {
        Intent intent = new Intent(this, BackgroundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void onButton9Click(View v) {
        Intent intent = new Intent(this, BackgroundService.class);
        intent.putExtra("type",4);
        startService(intent);
    }


}