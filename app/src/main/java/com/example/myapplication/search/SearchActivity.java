package com.example.myapplication.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.room.AppDatabase;
import com.example.myapplication.room.User;

import java.util.List;

import static android.app.SearchManager.EXTRA_DATA_KEY;

public class SearchActivity extends AppCompatActivity {
    SearchView mSearchView;
    String query;
    List<User> users;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("search", "create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.searchToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "myDatabase")
                .fallbackToDestructiveMigration()
                .build();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("search", "newIntent");

        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    public void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doMySearch(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Log.i("search", "actionView");
            String dataStr = intent.getDataString();
            String extra = intent.getStringExtra(EXTRA_DATA_KEY);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        assert dataStr != null;
                        int id = Integer.parseInt(dataStr);
                        users = db.userDao().getSpecificOneUser_id(id);
                        TextView textView = findViewById(R.id.searchTextView1);
                        textView.setVisibility(View.VISIBLE);
                        if (users.size() != 0) {
                            User user = users.get(0);
                            String text = user.getId() + " " + user.getUsername() + " " + user.isIs_login() + " " + extra;
                            textView.setText(text);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }

    Handler handler = new Handler();

    public void doMySearch(String query) {
        Log.i("search", query);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int id = Integer.parseInt(query);
                    users = db.userDao().getSpecificOneUser_id(id);
                } catch (NumberFormatException e) {
                    if (query.equals("true") | query.equals("false")) {
                        boolean isLogin = Boolean.parseBoolean(query);
                        users = db.userDao().getSpecificOneUser_isLogin(isLogin);
                    } else {
                        users = db.userDao().getSpecificOneUser_username_NoLivedata(query);
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("search", "setAdapter");
                        TextView textView = findViewById(R.id.searchTextView1);
                        if (users.size() == 0) {
                            textView.setText("没找到");
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            RecyclerView recyclerView = findViewById(R.id.searchRecycleView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                            recyclerView.setAdapter(new SearchUserAdapter(users));
                            textView.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }).start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("search", "createMenu");

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryRefinementEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuClearHistory:
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                        MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
                suggestions.clearHistory();
                return true;
            case R.id.menuAdd:
                Log.i("search", "add");
                User user1 = new User("a", true);
                User user2 = new User("b", true);
                User user3 = new User("c", true);
                User user4 = new User("d", true);
                User user5 = new User("e", false);
                User user6 = new User("f", true);
                User user7 = new User("g", false);

                User[] users1 = {user1, user2, user3, user4, user5, user6, user7};

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.userDao().insertAll(users1);
                    }
                }).start();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("search", "start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("search", "resume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}