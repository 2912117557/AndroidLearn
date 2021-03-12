package com.example.myapplication.main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;

import com.example.myapplication.BanActivity;
import com.example.myapplication.other.WebviewActivity;
import com.example.myapplication.other.LocationActivity;
import com.example.myapplication.other.VolleyActivity;
import com.example.myapplication.other.TouchActivity;
import com.example.myapplication.file.FileActivity;
import com.example.myapplication.service.ServiceActivity;
import com.example.myapplication.animator.AnimatorActivity;
import com.example.myapplication.other.ContextMenuActivity;
import com.example.myapplication.setting.SettingActivity;
import com.example.myapplication.dialog.DialogActivity;
import com.example.myapplication.other.NotificationActivity;
import com.example.myapplication.navigation.NavigationActivity;
import com.example.myapplication.pagedList.PagedListActivity;
import com.example.myapplication.room.DatabaseActivity;
import com.example.myapplication.recycleView.RecycleViewActivity;
import com.example.myapplication.work.WorkActivity;

public class BindingFunction {

    private Activity myActivity;

    public BindingFunction(Activity myActivity){
        this.myActivity=myActivity;
    }

    public void onButton1Click(View v) {
        Intent i=new Intent(myActivity, RecycleViewActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton2Click(View v) {
        Intent i=new Intent(myActivity, DatabaseActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton3Click(View v) {
        Intent i=new Intent(myActivity, PagedListActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton4Click(View v) {
        Intent i=new Intent(myActivity, WorkActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton5Click(View v) {
        Intent i=new Intent(myActivity, NavigationActivity.class);
        myActivity.startActivity(i);
    }


    public void onButton6Click(View v) {
        Intent i=new Intent(myActivity, NotificationActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton9Click(View v) {
        Intent i=new Intent(myActivity, DialogActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton10Click(View v) {
        Intent i=new Intent(myActivity, ContextMenuActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton11Click(View v) {
        Intent i=new Intent(myActivity, SettingActivity.class);
        myActivity.startActivity(i);
    }
    public void onButton12Click(View v) {
        Intent i=new Intent(myActivity, AnimatorActivity.class);
        myActivity.startActivity(i,ActivityOptions.makeSceneTransitionAnimation(myActivity).toBundle());
    }

    public void onButton14Click(View v) {
        Intent i=new Intent(myActivity, ServiceActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton15Click(View v) {
        Intent i=new Intent(myActivity, FileActivity.class);
        myActivity.startActivity(i);
    }
    public void onButton16Click(View v) {
        Intent i=new Intent(myActivity, TouchActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton17Click(View v) {
        Intent i=new Intent(myActivity, VolleyActivity.class);
        myActivity.startActivity(i);
    }

    public void onButton18Click(View v) {
        Intent i=new Intent(myActivity, LocationActivity.class);
        myActivity.startActivity(i);
    }
    public void onButton19Click(View v) {
        Intent i=new Intent(myActivity, WebviewActivity.class);
        myActivity.startActivity(i);
    }
    public void onButton20Click(View v) {
        Intent i=new Intent(myActivity, BanActivity.class);
        myActivity.startActivity(i);
    }
}
