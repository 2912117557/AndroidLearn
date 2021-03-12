package com.example.myapplication.setting;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.example.myapplication.R;

public class SettingFragment1 extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences1, rootKey);
        EditTextPreference countingPreference = findPreference("pref1EditText2");
        if (countingPreference != null) {
            countingPreference.setSummaryProvider(new Preference.SummaryProvider<EditTextPreference>() {
                @Override
                public CharSequence provideSummary(EditTextPreference preference) {
                    String text = preference.getText();
                    if (TextUtils.isEmpty(text)){
                        return "Not set";
                    }
                    return "Length of saved value: " + text.length();
                }
            });
        }

        EditTextPreference numberPreference = findPreference("pref1EditText1");
        if (numberPreference != null) {
            numberPreference.setOnBindEditTextListener(
                    new EditTextPreference.OnBindEditTextListener() {
                        @Override
                        public void onBindEditText(@NonNull EditText editText) {
                            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        }
                    });

            numberPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Log.i("set","pref1EditText1 "+newValue);
                    return true;
                }
            });
        }

    }
}
