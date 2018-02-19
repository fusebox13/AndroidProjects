package com.example.notifications;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



public class ClearNotifyActivity extends Activity {
    TextView text;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify);

        Intent myIntent = getIntent();

        text = (TextView)findViewById(R.id.mynotify);
        if (myIntent != null)
        {            
            String str = myIntent.getStringExtra("notify");
            text.setText("notify text="+ str);

            NotificationManager notificationManager;
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            int id = myIntent.getIntExtra("id", 0);
            notificationManager.cancel(id);
            Log.d("Mine","Notify Activity cancelling id="+id);
        }
    }

}