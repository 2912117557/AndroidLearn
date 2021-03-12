package com.example.myapplication.main;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.app.usage.StorageStatsManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.regAndLogin.MyConfig;
import com.example.myapplication.R;
import com.example.myapplication.search.MySuggestionProvider;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SearchRecentSuggestions;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;


public class MainActivity extends AppCompatActivity {
    GestureDetectorCompat mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("main","create");
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        getWindow().setAllowEnterTransitionOverlap(false);

        this.getLifecycle().addObserver(new MainActivityLifecycleObserver());

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).hide();
        MainActivityTabFragment1 fragment1 = new MainActivityTabFragment1();
        MainActivityTabFragment2 fragment2 = new MainActivityTabFragment2();
        MainActivityTabFragment3 fragment3 = new MainActivityTabFragment3();

        List<Fragment> mFragments = new ArrayList<Fragment>();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);

        List<String> mTitles = new ArrayList<String>();
        mTitles.add("主页一");
        mTitles.add("主页二");
        mTitles.add("我的");

        ViewPager2 mViewPager= (ViewPager2) findViewById(R.id.tabViewPager);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),mFragments);
        mViewPager.setAdapter(myFragmentPagerAdapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabTabLayout);
        new TabLayoutMediator(mTabLayout,mViewPager,((tab, position) -> tab.setText(mTitles.get(position)))).attach();

        mTitles.set(2,"我的修改");
        myFragmentPagerAdapter.notifyItemChanged(2);

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // The system bars are visible.
                            Log.i("main","statusShow");
                        } else if((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0){
                            Log.i("main","navShow");

                        }
                    }
                });
        mGestureDetector = new GestureDetectorCompat(this,new simpleGestureListener());
        MyViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        myViewModel.setOnTouchListener(onTouchListener);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("main","resume");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.i("main","hasFocus");
        }
        else{
            Log.i("main","loseFocus");
        }
    }

    public View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i("main","onTouch");
            Log.i("main",String.valueOf(event));
            return mGestureDetector.onTouchEvent(event);
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ViewPager2 mViewPager= (ViewPager2) findViewById(R.id.tabViewPager);
        mViewPager.setCurrentItem(2);
        TextView textView=findViewById(R.id.fragment3TextViewUsername);
        textView.setText(MyConfig.username);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("main","firstCreateMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) searchItem.getActionView();

        ComponentName componentName = new ComponentName("com.example.myapplication","com.example.myapplication.search.SearchActivity");
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(componentName);
        mSearchView.setSearchableInfo(searchableInfo);

        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryRefinementEnabled(true);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.menuMain1){
            Log.i("menu","main1");
            return true;
        }
        else if(id == R.id.menuMain1_1){
            Log.i("menu","main1_1");
            return true;
        }
        else if(id == R.id.menuMain2){
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;
        }
        else if(id == R.id.menuMain3){
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;
        }
        else if (id == R.id.menuClearHistory){
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.clearHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i("menu","invalidate");
        return super.onPrepareOptionsMenu(menu);
    }

    boolean isVis = false;
    private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View decorView = getWindow().getDecorView();
            ActionBar actionBar = getSupportActionBar();

            if(isVis){
                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptions);
                actionBar.hide();
                isVis=false;
            }
            else{
                decorView.setSystemUiVisibility(0);
                actionBar.show();
                isVis=true;
            }
            return super.onSingleTapUp(e);
        }
    }


    public void onButton8Click(View view){
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.fragment1Button8),
                "snackBar", Snackbar.LENGTH_INDEFINITE);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("main","dismiss");
                mySnackbar.dismiss();
            }
        };
        mySnackbar.setAction("set", onClickListener);
        mySnackbar.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("main","pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("main","stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("main","destory");
    }
}

