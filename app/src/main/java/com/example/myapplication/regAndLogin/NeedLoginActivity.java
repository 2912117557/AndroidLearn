package com.example.myapplication.regAndLogin;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class NeedLoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needlogin);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            String info = bundle.getString("needLogin");
            TextView textView = findViewById(R.id.needLoginTextView);
            textView.setText(info);
        }


    }




}
