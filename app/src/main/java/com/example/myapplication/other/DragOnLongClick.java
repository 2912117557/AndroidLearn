package com.example.myapplication.other;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.R;

public class DragOnLongClick implements View.OnLongClickListener {
    Context context;

    public DragOnLongClick(Context context){
        this.context = context;
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData clipData = ClipData.newPlainText("text","dragText");
        TextView textView = ((Activity)context).findViewById(R.id.fragment1Button2);
        View.DragShadowBuilder dragShadowBuilder =new View.DragShadowBuilder(textView);
        v.startDrag(clipData,dragShadowBuilder,null,0);
        return true;
    }
}
