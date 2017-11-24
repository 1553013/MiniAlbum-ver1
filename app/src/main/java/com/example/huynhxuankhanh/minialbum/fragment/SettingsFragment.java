package com.example.huynhxuankhanh.minialbum.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.huynhxuankhanh.minialbum.R;

/**
 * Created by dunarctic on 11/24/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load Preferences từ file xml
        addPreferencesFromResource(R.xml.app_preferences);
    }

}
