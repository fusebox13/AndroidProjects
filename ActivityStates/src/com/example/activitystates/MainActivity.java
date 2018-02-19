package com.example.activitystates;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity  implements OnClickListener{    
    int count = 0;  // we will demo how to restore our value of count

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.myfinal);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.myweb);
        b.setOnClickListener(this);
        Log.d("Mine", "onCreate count="+count);
        
        if (savedInstanceState != null)
            Log.d("Mine", "onCreate value in savedInstanceState.count="+savedInstanceState.getInt("count"));
  /*      
        MainActivity a = (MainActivity)getLastNonConfigurationInstance();
        if (a != null)
        {
            Log.d("Mine", "onCreate value in getLastNonConfigurationInstance.count="+ a.count);
        }
  */
    }

/*
    public Object onRetainNonConfigurationInstance()
    {
        Log.d("Mine", "onRetainNonConfigurationInstance count="+count);
        return this; // returning our Activity object;
    }
*/
    public void onClick(View v)
    {
        switch(v.getId())
        {
        case R.id.myfinal:
            Log.d("Mine", "finish");
            finish();
            break;
        case R.id.myweb:
            Log.d("Mine", "myweb");
            Uri uri = Uri.parse("http://wccnet.edu");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            count +=1;
            Log.d("Mine", "onClick count="+count);
            break;
        }
    }

    // Called at the start of the visible lifetime.  This indicates the activity is about to 
    // be displayed to the user. 
    @Override
    public void onStart(){
        super.onStart();
        // Apply any required UI change now that the Activity is visible.
        Log.d("Mine", "onStart count="+count);
    }
    // Called at the start of the active lifetime.  This is where the activity can 
    // start to interact with the user.  This is a good place to restart music and animations
    @Override
    public void onResume(){
        super.onResume();
        // Resume any paused UI updates, threads, or processes required
        // by the activity but suspended when it was inactive.
        Log.d("Mine", "onResume count="+count);
    }

    // Called at the end of the active lifetime. This is when the activity is about
    // to go into the background, usually because another activity has been launched
    // in front of it.  This is where you should save your program's persistent 
    // state, such as a database record being edited.
    @Override
    public void onPause(){
        // Suspend UI updates, threads, or CPU intensive processes 
        // that don’t need to be updated when the Activity isn’t 
        // the active foreground activity.
        super.onPause();
        Log.d("Mine", "onPause count="+count);
    }


    // Called before subsequent visible lifetimes 
    // for an activity process.  If this method is called, it indicates your activity
    // is being redisplayed to the user from a stopped state. 
    @Override
    public void onRestart(){
        super.onRestart();
        // Load changes knowing that the activity has already
        // been visible within this process.
        Log.d("Mine", "onRestart count="+count);
    }




    // Called to save UI state changes at the  end of the active lifecycle.
    // Android will call this method to allow the activity to save per-instance state, 
    // such as a cursor position within a text field.  Usually you won't need to override
    // it because the default implementation saves the state for all your user interface
    // controls automatically (IF THEY CONTAIN AN ID). 
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {    
        // Save UI state changes to the savedInstanceState. 
        // This bundle will be passed to onCreate if the process is 
        // killed and restarted.
        savedInstanceState.putInt("count", count);
        Log.d("Mine","count in onSaveInstance="+count);
        super.onSaveInstanceState(savedInstanceState);
    }


    // Called after onCreate has finished, use to restore UI state.  This is called when the 
    // activity is being reinitialized from a state previously saved by the onSaveInstanceState()
    // method.  The default implementation restores the state of your user interface.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState. 
        // This bundle has also been passed to onCreate.
        
        count = savedInstanceState.getInt("count");
        Log.d("Mine","count in onRestoreInstance="+count);
    }

    // Called at the end of the visible lifetime.  This is called when your activity is no longer
    // visible to the user and it won't be needed for a while.  If memory is tight,
    // onStop() may never be called (the system may simply terminate your process).
    @Override
    public void onStop(){    
        // Suspend remaining UI updates, threads, or processing 
        // that aren’t required when the Activity isn’t visible. 
        // Persist all edits or state changes 
        // as after this call the process is likely to be killed.
        super.onStop();
        Log.d("Mine", "onStop count="+count);
    }

    // Called at the end of the full lifetime.  This is called right before your activity
    // is destroyed.  If memory is tight, onDestroy() may never be called (the activity 
    // may simply terminate your process).
    @Override
    public void onDestroy(){
        // Clean up any resources including ending threads, 
        // closing database connections etc.
        super.onDestroy();
        Log.d("Mine", "onDestroy count="+count);
    }
}
