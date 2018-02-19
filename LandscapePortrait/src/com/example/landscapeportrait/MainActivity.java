package com.example.landscapeportrait;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Just in case your really need to know 
        // whether you are in Landscape mode or Portrait mode:

       if (findViewById(R.id.landscape_widget) != null)  
       {
            Log.d("Mine", "Based on Widget:  Must be Landscape");
            Toast.makeText(this, "Based on Widget: Must be Landscape", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.d("Mine", "Based on Missing Widget Must be Portrait");
            Toast.makeText(this, "Based on Missing Widget: Must be Portrait", Toast.LENGTH_SHORT).show();
        }
        
        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();
        if (d.getWidth() > d.getHeight())
        {
            Log.d("Mine", " Must be Landscape");
            Toast.makeText(this, "Must be Landscape", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.d("Mine", " Must be Portrait");
            Toast.makeText(this, "Must be Portrait", Toast.LENGTH_SHORT).show();
        }
    }
}