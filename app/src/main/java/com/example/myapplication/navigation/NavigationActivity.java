package com.example.myapplication.navigation;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.navigationNavHost);
//        Bundle bundle = new Bundle();
//        bundle.putString("init","myInit");
//        NavHostFragment.findNavController(fragment).setGraph(R.navigation.nav_graph,bundle);

        Toolbar toolbar = findViewById(R.id.navToolbar);
        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();
//        if (ab!=null){
//            ab.setHomeAsUpIndicator(android.R.drawable.ic_delete);
//            ab.setDisplayHomeAsUpEnabled(true);
//        }

//        NavController navController = Navigation.findNavController(this, R.id.navigationNavHost);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.navigationNavHost);
        NavController navController = NavHostFragment.findNavController(fragment);

        DrawerLayout drawerLayout = findViewById(R.id.navigationDrawer);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.navigationFragment1,R.id.navigationFragment3)
                        .setOpenableLayout(drawerLayout)
                        .build();
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);

        NavigationView navView = findViewById(R.id.navigationNavView);
        NavigationUI.setupWithNavController(navView,navController);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.navigationNavView);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



}