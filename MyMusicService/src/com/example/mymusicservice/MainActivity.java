package com.example.mymusicservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {
    TextView display=null;    

    int[] button_resources={R.id.reslist, R.id.next, R.id.stop, R.id.stopService};
    MyReceiver myReceiver=null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set up click listeners for all the buttons

        for (int i=0; i < button_resources.length; i++)
        {
            Button b = (Button)findViewById(button_resources[i]);
            b.setOnClickListener(this);
        }
        display = (TextView) findViewById(R.id.display);
    }

    // ...
    public void onClick(View v) {
        int index = v.getId();

        String cmd = null;
        switch(index)
        {
        case R.id.reslist:
            cmd = "play";
            break;

        case R.id.next:
            cmd = "next";
            break;
        case R.id.stop:
            cmd = "stop";
            break;

        case R.id.stopService:
            cmd = "stopService";
            break;
            // Alternatively, you could replace the above 2 lines with the 
            // following 2 lines
            //stopService(myIntent); 
            //return;
        }
        if (cmd != null)
        {
            Intent myIntent = new Intent(this, MyAudioService.class);
            myIntent.putExtra("cmd", cmd);
            startService(myIntent);
            // Note this Intent will be received in the Service onStartCommand.  If the Service hasn't been started,
            //     it will automatically be created.  
        }

    }

    @Override 
    public void onResume() {

        if (myReceiver == null)
        {
            IntentFilter filter;
            filter = new IntentFilter(MyAudioService.MY_NEXT_SONG_BROADCAST);
            myReceiver = new MyReceiver();
            registerReceiver(myReceiver, filter);
            Log.d("Mine", "onResume: registering MyReceiver");
        }
        super.onResume();
    }

    @Override
    public void onPause() {

        //If you Don't want to receive broadcasts when you don't own the screen
        //   Then uncomment out the following 2 lines of code
        // unregisterReceiver(myReceiver);
        // myReceiver=null;
        Log.d("Mine", "onPause: UNregistering Receiver");

        super.onPause();
    }
    @Override
    public void onDestroy(){
        // Clean up any resources including ending threads, 
        // closing database connections etc.
        super.onDestroy();
        if (myReceiver != null)
            unregisterReceiver(myReceiver);
        Log.d("Mine", "onDestroy");
    }


    //  *******************  INNER CLASS *******************
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int songNum = intent.getIntExtra("songNum", 0);
            double duration = intent.getIntExtra("duration", -1);
            duration /= 1000.0;
            String songName = intent.getStringExtra("songName");
            String str ="("+songNum+") "+songName + " duration="+duration;
            display.setText(str);
        }
    }
}