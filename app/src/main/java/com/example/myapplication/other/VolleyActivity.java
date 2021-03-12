package com.example.myapplication.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.myLib.MySingleton;

public class VolleyActivity extends AppCompatActivity {

    private RequestQueue queue;
    private NetworkReceiver receiver;
    public static boolean isOnline = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        updateConnectedFlags();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);

    }

    public void onButton1Click(View view) {
        TextView textView1 = findViewById(R.id.volleyTextView1);
        if (isOnline) {
            String url = "http://www.baidu.com/";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            textView1.setText(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    textView1.setText("Error");
                }
            });
            stringRequest.setTag(this);
            queue.add(stringRequest);
        } else {
            textView1.setText("No internet");
        }
    }

    public void updateConnectedFlags() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        boolean wifiConnected = false;
        boolean mobileConnected = false;
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            isOnline = true;
        } else {
            isOnline = false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (queue != null) {
            queue.cancelAll(this);
        }
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }

    }
}

class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        boolean wifiConnected = false;
        boolean mobileConnected = false;
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            VolleyActivity.isOnline = true;
        } else {
            VolleyActivity.isOnline = false;
        }
        Log.i("volley", "receiver " + VolleyActivity.isOnline);
    }
}



