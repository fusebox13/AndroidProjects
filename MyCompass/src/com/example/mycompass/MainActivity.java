package com.example.mycompass;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity  implements OnClickListener{
    
    CompassView compassView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button b = (Button)findViewById(R.id.increment);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.reset);
        b.setOnClickListener(this);
        
        compassView = (CompassView)findViewById(R.id.myDraw1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) 
    {
        switch (v.getId())
        {
        case R.id.increment:
            compassView.increment();
            break;
        case R.id.reset:
            compassView.reset();
            break;
        }
    }

}