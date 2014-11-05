package org.oyvang.library;

/**
 * Created by GeirMorten on 01.10.2014.
 */


import android.os.Bundle;
import android.preference.PreferenceActivity;


public class UserSettingActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}


