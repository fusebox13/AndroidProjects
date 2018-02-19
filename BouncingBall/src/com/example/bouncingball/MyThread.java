package com.example.bouncingball;

import android.os.Handler;
import android.util.Log;


public class MyThread extends Thread{

    float x_update=-1.0f, y_update=-1.0f;
    float x, y;
    float vx, vy;
    Handler guiThread;
    MyBox myBox;

    public MyThread(float x, float y, float vx, float vy, Handler guiThread, MyBox mb)
    {
        this.x=x;
        this.y=y;
        this.vx = vx;
        this.vy = vy;
        this.guiThread = guiThread;
        myBox = mb;
    }

    // Called in the View class touch method to report new location and speed for the ball
    public synchronized void update(float x, float y)
    {
        x_update =x;
        y_update=y;
        Log.d("Mine","update "+x + " "+y);
    }
    public synchronized void checkForUpdate()
    {
        if (x_update >= 0.0 && y_update >= 0.0)
        {
            x=x_update;
            y=y_update;
            x_update=y_update =-1.0f;
            Log.d("Mine","Update x,y");
        }  
    }

    public void run()
    {         
        while(true)
        {                              
            checkForUpdate();

            x += vx;
            y += vy;
            if (x > 100) // Check for hitting right boundary
            {
                x = 100;
                vx = - vx;
            }
            if (x < 0) // Check for hitting left boundary
            {
                x = 0;
                vx = -vx;
            }
            if (y > 100) // Check for hitting bottom boundary
            {
                y=100;
                vy=-vy;
            }
            if (y < 0)  // Check for hitting top boundary
            {
                y=0;
                vy=-vy;
            }
            //Log.d("Mine", "x="+x+" y="+y);

            Runnable work = new Runnable(){
                public void run(){
                    myBox.progressUpdate(x, y, vx, vy);
                }
            };
            guiThread.post(work);


            try
            {                 
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                Log.d("Mine","Interrupted Exception --- breaking Thread");
                break; 
            }
        }
        Log.d("Mine"," Done");

    }

}