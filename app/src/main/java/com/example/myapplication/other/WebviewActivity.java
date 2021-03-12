package com.example.myapplication.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebviewActivity extends AppCompatActivity {
    private WebView myWebView;

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if ("m.douban.com".equals(Uri.parse(url).getHost())) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstancaeState) {
        super.onCreate(savedInstancaeState);
        myWebView = new WebView(this);
        setContentView(myWebView);
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl("https://movie.douban.com/celebrity/1027206/");
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()){
            myWebView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }


}

