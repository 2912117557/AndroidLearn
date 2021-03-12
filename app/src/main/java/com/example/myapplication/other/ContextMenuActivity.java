package com.example.myapplication.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ContextMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);

        Toolbar toolbar = findViewById(R.id.contextMenuToolbar);
        setSupportActionBar(toolbar);

        List<String> myStringArray = new ArrayList<>();
        myStringArray.add("1号");
        myStringArray.add("2号");
        myStringArray.add("3号");
        myStringArray.add("4号");
        myStringArray.add("5号");
        myStringArray.add("6号");
        myStringArray.add("7号");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myStringArray);

        ListView listView = (ListView) findViewById(R.id.contextMenuListView);
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener(){
            int sum=0;
            List<Integer> selected = new ArrayList<>();
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                Log.i("contextMenu","select");
                if(!checked){
                    sum--;
                    selected.remove((Integer)position);
                    mode.setTitle("select "+sum);
                }
                else{
                    sum++;
                    selected.add(position);
                    mode.setTitle("select "+sum);
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                Log.i("context","createActionMode");

                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_context_menu, menu);
                sum=0;
                selected.clear();
                mode.setTitle("select "+sum);
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                Log.i("context","prepare");
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contextMenu1:
                        Log.i("contextMenu","set");
                        for(int i=0;i<selected.size();i++){
                            int index = selected.get(i);
                            myStringArray.remove(index);
                        }
                        adapter.notifyDataSetChanged();
                        return true;
                    case R.id.contextMenu2:
                        Log.i("contextMenu","set2");
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mode = null;
                Log.i("contextMenu","destoryActionMode");
            }
        });
    }

    public void onPopupMenuClick(View view){
        ListView listView = (ListView) findViewById(R.id.contextMenuListView);
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popupMenu1:
                        Log.i("popUp","clickItem");
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}