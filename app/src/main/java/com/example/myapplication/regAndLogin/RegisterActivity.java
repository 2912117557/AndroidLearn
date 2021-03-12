package com.example.myapplication.regAndLogin;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import com.example.myapplication.R;

public class RegisterActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    private Handler myHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1://失败
                    Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 2://成功
                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("reg","create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.registerToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab!=null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Button regSubmit=findViewById(R.id.regSubmit);
        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText usernameEdit=findViewById(R.id.regUsernameEdit);
                final EditText passwordEdit=findViewById(R.id.regPasswordEdit);
                final String username = usernameEdit.getText().toString();
                final String password = passwordEdit.getText().toString();
                new RegLoginOnClickListener(username,password,RegisterActivity.this,
                        "http://10.0.2.2:8080/register",myHandler).myClick();


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("reg","resume");

    }



}
