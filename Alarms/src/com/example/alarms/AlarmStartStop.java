package com.example.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmStartStop {
    Context context;
    
    AlarmStartStop(Context context)
    {
        this.context = context;
    }
    
    private PendingIntent getAlarmPendingIntent()
    {
         Intent intentToFire = new Intent(MyReceiver.ACTION_MY_RECEIVER_ALARM);
         PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intentToFire, 0);
         return alarmIntent;
    }
    
    public void startRepeatingAlarm()
    {
        //  You can look at android.app.AlarmManager
        //               Alarm types:
        //               use setRepeating for the following:
        //               ELAPSED_REALTIME_WAKEUP - Wakeup device at some starting elapsed time and at interval times
        //               ELAPSED_REALTIME - like the above except that a sleeping device is not woken up
        
        //               use set for the following:
        //               RTC_WAKEUP - Wake the device up at a specified Clock Time
        //               RTC - like the above except that a sleeping device is not woken up
        
        //               A big battery savings happens if you don't have to wake up a sleeping device
        
        //               By calling the setRepeating method below, we are requiring accurate alarm times
        //               Another battery savings happens if your time intervals don't need to be exact.
        //               Use setInexactRepeating instead of setRepeating.  The interval times can then be:
        //                    AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_HOUR, etc.   See documentation

        //


        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent= getAlarmPendingIntent();
       

        int alarmType = AlarmManager.RTC_WAKEUP; 
        long timeToRefresh = System.currentTimeMillis() + 2*1000 /* 2 seconds after start*/;
    
        alarmManager.setRepeating(alarmType, timeToRefresh, 
                /*every minute*/  10*1000, alarmIntent); // repeat every 10 seconds
    }
    public void stopRepeatingAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent= getAlarmPendingIntent();
        alarmManager.cancel(alarmIntent);
    }

}