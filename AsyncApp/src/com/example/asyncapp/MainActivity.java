package com.example.asyncapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import java.util.Date;



public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    TextView text;
    String countMsg = "The Count is counting ";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.myText);
        
// Construct our AsyncTask and start it executing a count from 4, 6, 8, ... 40
        MyAsync ma = new MyAsync();
        ma.execute("4", "40","2");
        
    }
    
    
    // Inner class ... NOTE how customizable the calling sequence is for 
    // this class.   Discuss possibilities
    class MyAsync extends Cps251AsyncTask<String, Integer, Boolean>{
        
        ProgressDialog pleaseWaitDialog=null;
        protected void onCancelled() {
            
            pleaseWaitDialog.dismiss();
        }
        
        
           // This code is to provide an animated dialog showing progress
           // Think of this code executing on the Gui thread

         protected void onPreExecute() {
            pleaseWaitDialog = ProgressDialog.show(MainActivity.this, "Sesame Street Count(back arrow to cancel)", 
                    countMsg, true, true);
            pleaseWaitDialog.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    Log.d("Mine", "onCancel");
                    cancel(true);
                }
            
            });
        }

        
        
       
        // This code executes in the created Thread
         protected Boolean doInBackground(String... parm) {
             int start = Integer.parseInt(parm[0]);
             int end = Integer.parseInt(parm[1]);
             int stepsize = Integer.parseInt(parm[2]);
             for(int count=start; count <= end; count+= stepsize)
             {
                 if (isCancelled())
                 {
                     Log.d("Mine","isCancelled=true");
                     break;
                 }
                 Log.d("Mine"," progress:"+count);
                 publishProgress(count);
                 try
                 {                 
                     Thread.sleep(1000);
                 }catch (InterruptedException e){}
             }
             Log.d("Mine"," Done");
             return true;
         }

       //This code executes in the GUI Thread
       protected void onProgressUpdate(Integer... progress) {
             Log.d("Mine","onProgressUpdate "+ progress[0]);
             Date d = new Date();
             text.setText(progress[0] + " : " + d.toString());
             if (pleaseWaitDialog != null)
                 pleaseWaitDialog.setMessage(countMsg+progress[0]);
         }

       //This code executes in theGUI Thread
       protected void onPostExecute(Boolean result) {
             Log.d("Mine","onPostExecute");
             text.setText("PostExecute: "+ result);
             if (pleaseWaitDialog != null)
                 pleaseWaitDialog.dismiss();
         }
     }// end of MyAsync

    
}
