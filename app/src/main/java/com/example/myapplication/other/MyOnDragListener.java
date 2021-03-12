package com.example.myapplication.other;

import android.content.ClipDescription;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

public class MyOnDragListener implements View.OnDragListener {
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    v.setBackgroundColor(Color.BLUE);
                    return true;
                }
                return false;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundColor(Color.GREEN);
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundColor(Color.BLUE);
                return true;
            case DragEvent.ACTION_DROP:
                CharSequence text = event.getClipData().getItemAt(0).getText();
                if (v instanceof TextView) {
                    String newText = ((TextView) v).getText() + " " + text;
                    ((TextView) v).setText(newText);
                }
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundColor(Color.WHITE);
                if (event.getResult()) {
                    Log.i("drag", "success");
                } else {
                    Log.i("drag", "fail");
                }
                return true;
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                return false;
        }
    }
}
