package com.example.alarms;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION_MY_RECEIVER_ALARM = "edu.wccnet.clem.alarmservice.ACTION_MY_RECEIVER_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        String s = "MyReceiver.onReceive "+ new Date();
        Log.d("Mine", s);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

    }
}