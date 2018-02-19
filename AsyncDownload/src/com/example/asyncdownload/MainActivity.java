package com.example.asyncdownload;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;


// Files that you can download:
// http://russet.wccnet.edu/~chasselb/boys_names.txt
// http://russet.wccnet.edu/~chasselb/girls_names.txt
// http://earthquake.usgs.gov/eqcenter/catalogs/1day-M2.5.xml
// http://wccnet.edu
// http://yahoo.com


public class MainActivity extends Activity {
    EditText myUrl;
    TextView myOutput;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myUrl = (EditText)findViewById(R.id.myurl);
        myOutput = (TextView)findViewById(R.id.myoutput);

        myUrl.setOnKeyListener(new MyEditTextHandler()); 

    }

    // ************* Start of Inner class to handle the EditText box  *********
    class MyEditTextHandler implements OnKeyListener
    {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
                if (keyCode == KeyEvent.KEYCODE_ENTER) 
                {
                    String url = myUrl.getText().toString();
                    Log.d("Mine", "loadFile "+url);

                    MyAsync myAsync = new MyAsync();
                    myAsync.execute(url);

                    return true; 
                }
            return false;
        }


    }
    // ************* End of Inner class



    


    // ******* INNER CLASS
    class MyAsync extends AsyncTask<String, Integer, String>{

        ProgressDialog pleaseWaitDialog=null;
        String downloadMsg="Lines=";

        // This code is to provide an animated dialog showing progress
        // Think of this code executing on the Gui thread

        protected void onPreExecute() {
            pleaseWaitDialog = ProgressDialog.show(MainActivity.this, 
                    "Downloading file(back arrow to cancel)", 
                    downloadMsg, true, true);
            pleaseWaitDialog.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    Log.d("Mine", "onCancel");
                    cancel(true);
                }

            });
        }
        



        // This code executes in the created Thread
        protected String doInBackground(String... parm) {
            String url=parm[0];
            String contents =loadFile(url);     

            Log.d("Mine"," Done");
            return contents;
        }

        //This code executes in the GUI Thread
        protected void onProgressUpdate(Integer... progress) {
            Log.d("Mine","onProgressUpdate "+ progress[0]);
            if (pleaseWaitDialog != null)
                pleaseWaitDialog.setMessage(downloadMsg+progress[0]);
        }

        //This code executes in theGUI Thread
        protected void onPostExecute(String contents) {
            Log.d("Mine","onPostExecute");
            myOutput.setText(contents);
            if (pleaseWaitDialog != null)
                pleaseWaitDialog.dismiss();
        }
        
        private String loadFile(String urlStr)
        {
            StringBuilder sb= new StringBuilder();
            URL url;
            URLConnection connection;
            HttpURLConnection httpConnection=null;
            Scanner scan=null;

            try {
                url = new URL(urlStr);
                connection = url.openConnection();

                httpConnection = (HttpURLConnection)connection; 
                int responseCode = httpConnection.getResponseCode(); 

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("Mine", "Good HttpURLConnection");
                    InputStream in = httpConnection.getInputStream(); 
                    scan = new Scanner(in);
                    int count = 0;
                    while(scan.hasNextLine())
                    {
                        String str = scan.nextLine();
                        count += 1;
                        if (count % 50 == 0)
                        {
                        	publishProgress(count);
                        }
                        sb.append(str +"\n");                  
                    }
                    scan.close();
                } 
                else

                    Log.d("Mine", "BAD HttpURLConnection");
            }
            catch (MalformedURLException e) {
                Log.d("Mine", "MalformedURLException "+e);
            } catch (IOException e) {
                Log.d("Mine", "IOException "+e);
            }
            finally {
                if (httpConnection != null)
                    httpConnection.disconnect();
            }
            return sb.toString();
        }

    }// end of MyAsync Inner class

}