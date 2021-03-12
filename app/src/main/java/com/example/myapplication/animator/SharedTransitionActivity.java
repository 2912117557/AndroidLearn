package com.example.myapplication.animator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.example.myapplication.R;

public class SharedTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_transition);

        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
        getWindow().setSharedElementEnterTransition(new ChangeTransform());
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        getWindow().setAllowEnterTransitionOverlap(false);
    }


    public void onButton1Click(View view){
        finish();
    }
}