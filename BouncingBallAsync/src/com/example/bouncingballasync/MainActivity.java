package com.example.bouncingballasync;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    MyBox myBox ;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBox = (MyBox) findViewById(R.id.mybox);
        myBox.setOnTouchListener(myBox);

    }
    
    @Override
    public void onResume(){
        super.onResume();
        myBox.startThread();
        Log.d("Mine", "onResume");
    }
    
    @Override
    public void onPause(){
        super.onPause();

        myBox.stopThread();
        Log.d("Mine", "onPause");
    }

}