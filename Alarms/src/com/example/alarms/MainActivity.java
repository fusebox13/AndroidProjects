package com.example.alarms;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {


    // The following 2 routines will be used to keep track of a boolean stored in our Preferences
    // under the name "BOOT_RESTART".  We will use this on the next stage, when we want to restore
    // our Alarm after a reboot (normally Alarms are lost in a reboot)


    static final String ALARM_IS_SET="ALARM_IS_SET";
    public static boolean getAlarmIsSet(Context ctx)
    {        
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sp.getBoolean(ALARM_IS_SET, false);
    }    
    public static void setAlarmIsSet(Context ctx, boolean value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(ALARM_IS_SET, value);
        editor.commit();
    }

    Button startAlarm, stopAlarm;    
    AlarmStartStop alarmStartStop = new AlarmStartStop(this);
    boolean alarmSet=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAlarm=(Button)findViewById(R.id.startalarm);
        startAlarm.setOnClickListener(this);

        stopAlarm=(Button)findViewById(R.id.stopalarm);
        stopAlarm.setOnClickListener(this);
        alarmSet = getAlarmIsSet(this);
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.startalarm:                                                                                                                            
            alarmStartStop.startRepeatingAlarm();
            alarmSet=true;
            setAlarmIsSet(this, true);
            break;

        case R.id.stopalarm:
            alarmSet=false;
            alarmStartStop.stopRepeatingAlarm();
            setAlarmIsSet(this, false);           
            break;
        }
    }
}
