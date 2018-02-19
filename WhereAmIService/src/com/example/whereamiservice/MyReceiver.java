package com.example.whereamiservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;


public class MyReceiver extends BroadcastReceiver {
    public static final String MY_RECIEVER_ACTION = "edu.wccnet.myproximity.ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Mine", "MyReceiver.onReceive");

        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean entering = intent.getBooleanExtra(key, false);

        String results = "entering="+entering;
        Toast.makeText(context, results, Toast.LENGTH_SHORT).show();


    }
}