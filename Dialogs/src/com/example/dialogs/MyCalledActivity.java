package com.example.dialogs;

import android.app.Activity;
import android.os.Bundle;


public class MyCalledActivity extends Activity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.called_activity);
   }
}