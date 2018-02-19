package com.example.whereamiservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    // button names:
    public static final String startLocationUpdates="start location updates";
    public static final String stopLocationUpdates="stop location updates";
    public static final String startProximityChecking = "start proximity checking";
    public static final String stopProximityChecking = "stop proximity checking";
    public static final String stopService = "stop service";


    Button start_stop_button;
    Button proximity_start_stop_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button)findViewById(R.id.start_stop);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.proximity_start_stop);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.stop_service);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Button buttonClicked = (Button)view;
        String buttonLabel = buttonClicked.getText().toString();

        Intent myIntent = new Intent(this, WhereAmIService.class);
        myIntent.putExtra("cmd", buttonLabel);

        switch (view.getId())
        {
        case R.id.start_stop:
            if (buttonLabel.startsWith("start"))
            {
                buttonClicked.setText(stopLocationUpdates);
            }
            else
            {
                buttonClicked.setText(startLocationUpdates);
            }
            break;


        case R.id.proximity_start_stop:
            if (buttonLabel.startsWith("start"))
            {
                buttonClicked.setText(stopProximityChecking);
            }
            else
            {
                buttonClicked.setText(startProximityChecking);
            }
            break;
        case R.id.stop_service:
            // Because "stop service" automatically stops the Location updates and the Proximity Checking
            //  we need to set those buttons back to the "start" situation in case they are running
            Button b = (Button)findViewById(R.id.start_stop);
            b.setText(startLocationUpdates);
            b = (Button)findViewById(R.id.proximity_start_stop);
            b.setText(startProximityChecking);
            break;
        }
        startService(myIntent);

    }

}