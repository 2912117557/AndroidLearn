package com.example.myapplication.main;

import android.view.View;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private View.OnTouchListener onTouchListener;

    public View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

}
