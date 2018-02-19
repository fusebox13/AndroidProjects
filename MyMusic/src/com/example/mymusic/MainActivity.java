package com.example.mymusic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {
    private MediaPlayer mp;
    TextView display=null;

    int[] resources={R.id.baby_love, R.id.gitarzan, R.id.lady_godiva, 
                        R.id.peter_piper, R.id.she_sells, R.id.numbers, R.id.stop /* no resource associated with this one*/}; 


    int[] mp_resources={R.raw.baby_love, R.raw.gitarzan, R.raw.lady_godiva, 
                        R.raw.peter_piper, R.raw.she_sells, R.raw.numbers}; 



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set up click listeners for all the buttons

        for (int i=0; i < resources.length; i++)
        {
            Button b = (Button)findViewById(resources[i]);
            b.setOnClickListener(this);
        }


        display = (TextView) findViewById(R.id.display);
    }

    // ...
    public void onClick(View v) {
        int index = v.getId();
        if (index == R.id.stop)
        {
            if (mp != null)
                mp.release();
            return;
        }
        for (int i=0; i < resources.length; i++)
        {
            if (index == resources[i])
            {
                String str = ((Button)v).getText().toString();
                display.setText("Playing:" + str);
                play(mp_resources[i]);
                return;
            }
        }
        display.setText(" Not sure what is playing here?");
    }


    void play(int resId)
    {
        // Release any resources from previous MediaPlayer
        if (mp != null) {
            mp.release(); 
        }

        // Create a new MediaPlayer to play this sound
        Log.d("Mine","resid="+resId);
        mp = MediaPlayer.create(this, resId); 
        mp.start();
    }

}
