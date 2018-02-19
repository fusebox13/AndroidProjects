package com.example.webdownload;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
    TextView myOutput, progress;
    Handler guiThread;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myUrl = (EditText)findViewById(R.id.myurl);
        myOutput = (TextView)findViewById(R.id.myoutput);
        progress = (TextView)findViewById(R.id.progress);
        guiThread = new Handler();

        myUrl.setOnKeyListener(new MyEditTextHandler()); 
        
    }

    // ************* Start of Inner class to handle the EditText box  *********
    class MyEditTextHandler implements OnKeyListener
    {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
                if (keyCode == KeyEvent.KEYCODE_ENTER) 
                {
                    final String url = myUrl.getText().toString();
                    Log.d("Mine", "loadFile "+url);
            
                    Thread t = new Thread(new Runnable()
                    {
                        @Override
                        public void run() {
                            final String str = loadFile(url);
                            guiSetText(myOutput, str);
                        }
                    });
                    t.start();               

                    return true; 
                }
            return false;
        }


    }
    // ************* End of Inner class


    private void guiSetText(final TextView view, final String text) {

        Runnable work = new Runnable(){
            public void run() {
                view.setText(text);
            }
        };
        guiThread.post(work);
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
                    if(count % 50 == 0)
                    {
                    	String s = "Downloaded" + count + " lines";
                    	guiSetText(progress, s);
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

}