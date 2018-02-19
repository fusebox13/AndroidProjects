package com.example.userinputs;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity 
    implements OnClickListener {


    TextView display=null;
    //CheckBox cb = null;
    //RadioButton red, blue;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set up click listeners for all the buttons
        int[] resources = {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b0, R.id.clear};
       
        for (int i = 0; i < resources.length; i++)
        {
        	Button b = (Button)findViewById(resources[i]);
        	b.setOnClickListener(this);
        }
        
        
        display = (TextView) findViewById(R.id.display);
        
        //cb = (CheckBox) findViewById(R.id.checkbox);
        //red = (RadioButton) findViewById(R.id.radio_red);
        //blue = (RadioButton) findViewById(R.id.radio_blue);
        
        //cb.setOnClickListener(this);
        //red.setOnClickListener(this);
        //blue.setOnClickListener(this);
        
        Log.d("Mine", "onCreate() finished");
     }

     // ...
     public void onClick(View v) {
    	 
    	 if (v.getId() == R.id.clear)
    		 display.setText("");
    	 else
    	 {
    		 Button bu = (Button)v;
    		 display.append(bu.getText());
    		 int R = (int)(Math.random()*256);
    		 int G = (int)(Math.random()*256);
    		 int B= (int)(Math.random()*256);
    		 
    		 bu.setBackgroundColor(Color.rgb(R, G, B));
    	 }
        /*switch (v.getId()) {
        case R.id.b1:
            display.append(b1.getText()+" ");
           break;
        case R.id.b2:
            display.append(b2.getText()+" ");
           break;
        case R.id.b3:
            display.append(b3.getText()+" ");
           break;
        
        case R.id.checkbox:
        	display.append(cb.getText() + " ");
        	break;
        case R.id.radio_red:
        	display.append(red.getText() + " ");
        	break;
        case R.id.radio_blue:
        	display.append(blue.getText() + " ");
        	break;
        // More buttons go here (if any) ...
       
        default:
           break;
        } */
        //Log.d("Mine", "Status (red, blue, checkbox) =" + red.isChecked() + "," + blue.isChecked() 
    	// 		+ "," + cb.isChecked());
        Log.d("Mine", "onClick() finished");
     }
}
