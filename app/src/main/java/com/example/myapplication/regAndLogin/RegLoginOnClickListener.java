package com.example.myapplication.regAndLogin;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class RegLoginOnClickListener{
    private String username;
    private String password;
    private Context myContext;
    private String myUrl;
    private Handler myHandler;

    public RegLoginOnClickListener(String username, String password, Context myContext, String myUrl, Handler myHandler) {
        this.username = username;
        this.password = password;
        this.myContext = myContext;
        this.myUrl = myUrl;
        this.myHandler = myHandler;
    }

    public void myClick() {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(myContext,"用户名和密码必填",Toast.LENGTH_SHORT).show();
        }
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    HttpURLConnection connection=null;
                    try {
                        URL url = new URL(myUrl);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(8000);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                        OutputStream out=null;
                        try{
                            out = connection.getOutputStream();
                            String content="username="+ URLEncoder.encode(username,"utf-8")+"&"+
                                    "password="+URLEncoder.encode(password,"utf-8");
                            out.write(content.getBytes(StandardCharsets.UTF_8));
                            out.flush();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            if(out!=null){
                                out.close();
                            }
                        }
                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader reader=null;
                            try{
                                reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                StringBuilder response = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    response.append(line);
                                }
                                String result = response.toString();
                                JSONObject jsonObject = new JSONObject(result);
                                int code = (int) jsonObject.get("code");
                                if(code==0){
                                    msg.what=2;//成功
                                }
                                else{
                                    msg.what=1;//失败
                                }
                            }finally {
                                if(reader!=null){
                                    reader.close();
                                }
                            }
                        }
                        else{
                            msg.what=1;
                        }
                    } catch (Exception e) {
                        msg.what=1;
                    } finally {
                        if(connection!=null){
                            connection.disconnect();
                        }
                        msg.obj=username;
                        myHandler.sendMessage(msg);
                    }
                }
            }).start();
        }
    }
}
