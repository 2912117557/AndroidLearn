package com.example.myapplication.setting;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.myapplication.R;

public class SettingFragment2 extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences2, rootKey);
    }
}
