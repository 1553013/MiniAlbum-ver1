package com.example.huynhxuankhanh.minialbum.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.Utility;
import com.example.huynhxuankhanh.minialbum.views.SecurityConfigDialog;

/**
 * Created by dunarctic on 11/23/2017.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //create back button on top-left of toolbar
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Display the fragment as the main content.
        getSupportFragmentManager().beginTransaction().replace(R.id.pref_container, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
        private Preference pref_security;
        private Preference pref_others;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.app_preferences);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initSettings();
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        private void initSettings() {
            pref_security = findPreference("pref_security");
            pref_security.setOnPreferenceClickListener(this);
            pref_others = findPreference("pref_others");
            pref_others.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            return true;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()) {
                case "pref_security": {

//                    if (!PreferenceManager.getDefaultSharedPreferences(getContext())
//                            .getBoolean("security_del", false))
//                        new SecurityConfigDialog(getContext()).show();
//                    else {
//                        Toast.makeText(getActivity(), "restrict settings", Toast.LENGTH_LONG)
//                                .show();
//                    }
                    new SecurityConfigDialog(getContext()).show();
                    break;
                }
                case "pref_others": {
                    Toast.makeText(getContext(), Utility.PASSWORD_KEY, Toast.LENGTH_LONG).show();
                    break;
                }
            }
            return false;
        }
    }
}
