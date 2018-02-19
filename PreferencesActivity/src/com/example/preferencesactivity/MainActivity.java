package com.example.preferencesactivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;


public class MainActivity extends PreferenceActivity {
   

    public void onCreate(Bundle savedInstanceState) {
                 super.onCreate(savedInstanceState);
                 addPreferencesFromResource(R.xml.settings);
                 
                 boolean musicFlag =PreferenceManager.getDefaultSharedPreferences(this)
                                                 .getBoolean("music", true);
                 boolean hintFlag = PreferenceManager.getDefaultSharedPreferences(this)
                                                 .getBoolean("hint", true);
                 String name =      PreferenceManager.getDefaultSharedPreferences(this)
                                                 .getString("name", "Joe");
                 Log.d("Mine", "music="+musicFlag + " hint="+hintFlag+ " name="+name);
              }   
    
}