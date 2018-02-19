package com.example.asyncapp;

import android.os.Handler;
import android.util.Log;

public class Cps251AsyncTask<Parm, Progress, Result> {
    Handler guiThread = new Handler();
    Thread thread=null;
    boolean isCancelledFlag = false;

    final public void execute(final Parm ...parameter)
    {
        Log.d("Mine", "Using Cps251AsyncTask");
        onPreExecute();
        thread = new Thread()
        {
            public void run()
            {
                final Result result = doInBackground(parameter);
                Runnable work = new Runnable()
                {
                    public void run()
                    {
                        onPostExecute(result);    
                    }
                };
                guiThread.post(work);
            }
        };
        thread.start();
    }
    public void cancel(boolean mayInterruptIfRunning)
    {
        isCancelledFlag=true;
    }
    public boolean isCancelled()
    {
        return isCancelledFlag;
    }
    protected void onPreExecute()
    { 
        // This code executes in the GUI Thread and will be called before doInBackground starts
    }
    protected Result doInBackground(Parm ... parameter) {
        return null;
    }
    protected void publishProgress(final Progress ... progressParms)
    {
        Runnable work = new Runnable()
        {
            public void run()
            {
                onProgressUpdate(progressParms);        
            }
        };
        guiThread.post(work);
    }
    protected void onProgressUpdate(Progress ... progressParms) {
        // This method executes in the GUI Thread and is used to report progress
    }


    protected void onPostExecute(Result results) {
        // This method executes in the GUI Thread and is called when doInBackground completes
    }



}