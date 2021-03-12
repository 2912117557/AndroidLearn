package com.example.myapplication.other;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Objects;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;
import static androidx.core.content.ContextCompat.getSystemService;

public class CopyCallBack implements ActionMode.Callback {
    private TextView view;
    private ClipboardManager clipboard;
    private Context context;

    public CopyCallBack(Context context,TextView view) {
        this.view = view;
        this.context =context;
        clipboard= getSystemService(context,ClipboardManager.class);
    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        Log.i("copy","createActionMode");

        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_copy, menu);


        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        Log.i("copy","prepare");

        MenuItem pasteItem = menu.findItem(R.id.it_paste);
        menu.removeItem(android.R.id.selectAll);
        menu.removeItem(android.R.id.cut);
        menu.removeItem(android.R.id.copy);
        menu.removeItem(android.R.id.paste);
        menu.removeItem(android.R.id.replaceText);
        menu.removeItem(android.R.id.shareText);

        if (!(clipboard.hasPrimaryClip())) {
            pasteItem.setEnabled(false);
        } else if (!(Objects.requireNonNull(clipboard.getPrimaryClipDescription()).hasMimeType(MIMETYPE_TEXT_PLAIN))) {
            pasteItem.setEnabled(false);
        } else {
            pasteItem.setEnabled(true);
        }


        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId()){
            case R.id.it_all:
                Log.i("copy","all");
                if(view instanceof EditText){
                    ((EditText) view).selectAll();
                }

                return true;
            case R.id.it_copy:
                Log.i("copy","copy");
                int selectionStart = view.getSelectionStart();
                int selectionEnd = view.getSelectionEnd();
                String txt = view.getText().toString();
                String substring = txt.substring(selectionStart, selectionEnd);
                clipboard.setPrimaryClip(ClipData.newPlainText("text",substring));
                mode.finish();
                return true;
            case R.id.it_cut:
                Log.i("copy","cut");
                int selectionStart2 = view.getSelectionStart();
                int selectionEnd2 = view.getSelectionEnd();
                String txt2 = view.getText().toString();
                String substring2 = txt2.substring(selectionStart2, selectionEnd2);
                clipboard.setPrimaryClip(ClipData.newPlainText("text",substring2));
                txt2 = txt2.replace(substring2,"");
                view.setText(txt2);
                mode.finish();
                return true;
            case R.id.it_paste:
                Log.i("copy","paste");
                ClipData.Item myItem = Objects.requireNonNull(clipboard.getPrimaryClip()).getItemAt(0);
                CharSequence pasteData = myItem.getText();
                if (pasteData != null) {
                    view.setText(pasteData);
                } else {
                    Uri pasteUri = myItem.getUri();
                    if (pasteUri != null) {
                        pasteData = "";
                        view.setText(pasteData);
                    } else {
                        Log.e("copy", "Clipboard contains an invalid data type");
                        return false;
                    }
                }
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        Log.i("copy","destory");
    }
}
