package com.example.myapplication.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.myapplication.R;
import com.example.myapplication.regAndLogin.MyConfig;

public class MainActivityLifecycleObserver implements LifecycleEventObserver {


    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event== Lifecycle.Event.ON_CREATE){

        }

        else if (event== Lifecycle.Event.ON_START){

        }

    }



}
