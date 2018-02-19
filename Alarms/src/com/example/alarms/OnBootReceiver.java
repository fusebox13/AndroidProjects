package com.example.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Mine", "OnBootReceiver.onReceive");
        Toast.makeText(context, "OnBootReceiver.onReceive", 
                Toast.LENGTH_SHORT).show();


        if (MainActivity.getAlarmIsSet(context)) 
        {
            AlarmStartStop alarmStartStop = new AlarmStartStop(context);
            alarmStartStop.startRepeatingAlarm();
            String str = "onBootReceiver: Alarm was re-enabled";
            Log.d("Mine", str);
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }


    }
}
