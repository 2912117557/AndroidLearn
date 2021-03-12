package com.example.myapplication.regAndLogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    private Handler myHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1://失败
                    Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 2://成功
                    try{
                        Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                        MyConfig.is_login=true;
                        String myUsername=(String)msg.obj;
                        MyConfig.username=myUsername;
                        SharedPreferences mySP=getSharedPreferences("mySP",MODE_PRIVATE);
                        mySP.edit().putBoolean("is_login",true).apply();
                        mySP.edit().putString("username",myUsername).apply();
                        LoginCarrier invoker = (LoginCarrier) getIntent().getParcelableExtra(LoginInterceptor.mINVOKER);
                        if (invoker!=null){
                            invoker.invoke(LoginActivity.this);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
        }
    };

    private ActivityLoginBinding binding;
    MyConfig user=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Toolbar toolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab!=null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Button loginSubmit=findViewById(R.id.loginSubmit);
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText usernameEdit=findViewById(R.id.loginUsernameEdit);
                final EditText passwordEdit=findViewById(R.id.loginPasswordEdit);
                final String username = usernameEdit.getText().toString();
                final String password = passwordEdit.getText().toString();
                new RegLoginOnClickListener(username,password,LoginActivity.this,
                        "http://10.0.2.2:8080/login",myHandler).myClick();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
