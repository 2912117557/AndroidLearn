package com.example.myapplication.setting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;

import com.example.myapplication.R;

import javax.net.ssl.HttpsURLConnection;

public class SettingActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback{
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.settingToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab!=null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingContainer, new SettingFragment1())
                .commit();
        toolbar.setTitle("Pref1");
    }


    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                pref.getFragment());
        fragment.setArguments(args);
        // Replace the existing Fragment with the new Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settingContainer, fragment)
                .addToBackStack(null)
                .commit();
        toolbar.setTitle("Pref2");
        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}