package com.example.myapplication.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.regAndLogin.LoginInterceptor;
import com.example.myapplication.regAndLogin.MyConfig;
import com.example.myapplication.regAndLogin.RegisterActivity;

import static android.content.Context.MODE_PRIVATE;

public class MainActivityTabFragment3 extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment3, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences mySP=requireActivity().getSharedPreferences("mySP",MODE_PRIVATE);
        String myUsername=mySP.getString("username",null);
        if(myUsername!=null){
            boolean myIs_login=mySP.getBoolean("is_login",false);
            MyConfig.username=myUsername;
            MyConfig.is_login=myIs_login;
            TextView textView = view.findViewById(R.id.fragment3TextViewUsername);
            textView.setText(MyConfig.username);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Button loginButton= requireActivity().findViewById(R.id.fragment3LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LoginInterceptor.interceptor(getActivity(),"com.example.myapplication.MainFragment3",null);
            }
        });

        Button regButton= requireActivity().findViewById(R.id.fragment3RegisterButton);
        regButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), RegisterActivity.class);
                String title = getResources().getString(R.string.chooser_title);
                Intent chooser = Intent.createChooser(intent, title);
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

        Button needLoginButton=requireActivity().findViewById(R.id.fragment3NeedLoginButton);
        needLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("needLogin","success");
                LoginInterceptor.interceptor(getActivity(),"com.example.myapplication.NeedLogin",bundle);
            }
        });

    }
}