package com.example.guihandlerapproach;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.os.Handler;
import java.util.Date;


public class MainActivity extends Activity implements Runnable{
    /** Called when the activity is first created. */
    TextView text;

    private Handler guiThread;

    String countMsg = "The Count is counting ";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.myText);

        guiThread = new Handler(); 
        Thread t= new Thread(this);
        t.start();
        
        
    }
    public void run()
    {
        for (int i=2; i < 24; i++)
        {
            try
            {
                Thread.sleep(1000);
                Date d = new Date();
                String str =i +" : " + d.toString();
                // Try changing guiSetText to guiSetText2 and then try guiSetText3 and see what happens:
               
                guiSetText(text, str);

                Log.d("Mine","run: "+i);
            }catch (InterruptedException e){}
        }
    }

  
    private void guiSetText(final TextView view, final String text) {
        
        Runnable work = new Runnable(){
           public void run() {
              view.append(text+'\n');
           }
        };
        guiThread.post(work);
     }

/*  **************  Alternative variations to consider:*/


    // runOnUiThread is a method that is part of Activity
    private void guiSetText2(final TextView view, final String text) {
        Runnable work = new Runnable(){
           public void run() {
              view.append(text+'\n');
           }
        };
        runOnUiThread(work);
     }
    private void guiSetText3(final TextView view, final String text) {
        Runnable work = new Runnable(){
           public void run() {
              view.append(text+'\n');
           }
        };
        guiThread.postDelayed(work, 5000);
     }
   
}