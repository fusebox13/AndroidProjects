package com.example.mytouchscreendrawingprogram;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity  {

     
    TextView fileNameDisplay;

    MyDrawingView myDrawingView;
    
    static final int GET_DRAWING=1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
                
        setContentView(R.layout.activity_main);
        

        myDrawingView = (MyDrawingView)findViewById(R.id.myDraw1);
        
        // Because of the following our user can't change anything  
        // ... onTouch will not be called
        //myDrawingView.setOnTouchListener(myDrawingView);


        fileNameDisplay = (TextView)findViewById(R.id.file_name);
        
        // Call DrawingManager to get the latest/greatest picture and return it immediately
        
        Intent intent = new Intent(this, DrawingManager.class);
        intent.putExtra("getLastDrawing", "returnImmediately");
        startActivityForResult(intent, GET_DRAWING /* Request */);
        
        

    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);

        if (resCode != Activity.RESULT_OK)
        {
            Log.d("Mine", "Returning without proper Result code");
            return;
        }
        switch(reqCode)
        {
        case GET_DRAWING:
            
            // Note that for ArrayList<String> you can use:
            //ArrayList<String>     getStringArrayListExtra(String name)
            
            Serializable ob = data.getSerializableExtra("shapes");
            if (ob != null)
            {
                ArrayList<Shape> arr = (ArrayList<Shape>)ob;
                myDrawingView.setDrawing( arr);
            }
            String fileName = data.getStringExtra("fileName");
            fileNameDisplay.setText(fileName);

            break;

        default:
            Log.d("Mine","Shouldn't happen");
            break;

        }

    }
    
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    // User has selected an item from the Options Menu
    public boolean onOptionsItemSelected(MenuItem item)
    {            
        switch (item.getItemId())
        {
        case R.id.get_drawing:
            Log.d("Mine", "Get Drawing");
            
            Intent intent = new Intent(this, DrawingManager.class);
            // Note that any value other than "returnImmediately" would work.
            intent.putExtra("getLastDrawing", "return_after_pick"); 
            startActivityForResult(intent, GET_DRAWING /* Request */);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}