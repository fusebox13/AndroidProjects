package com.example.notifications;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends Activity {
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    
        
        Button b = (Button)findViewById(R.id.add_notification);  
        
        b.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                switch (v.getId())
                {
                case R.id.add_notification:
                    String tickerText = getStringFromEditText(R.id.ticker_text);
                    String contentTitle = getStringFromEditText(R.id.content_title);
                    String contentText = getStringFromEditText(R.id.content_text);
                    String contentSubText = getStringFromEditText(R.id.content_subtext);
                    String contentInfo = getStringFromEditText(R.id.content_info);
                    
                    
                    int icon = R.drawable.ic_launcher; // Too lazy to make my own ICON
                    // Could also use: android.R.drawable.stat_sys_warning
                    int count = getIntFromEditText(R.id.count);
                    int notificationId = getIntFromEditText(R.id.notification_id);
                    int alertOptions = getOptions();
                    String action=getStringFromEditText(R.id.action);
                    String msgForIntent =getStringFromEditText(R.id.msgForIntent);
                    
                    add_notification(tickerText, contentTitle, contentText,
                            contentSubText, contentInfo,
                            icon, count, notificationId, action,
                            alertOptions,  msgForIntent);
                    break;
                }
            }
        });
    }


// **********************************************************
// The following method was designed to run in an Activity, Service or anything else(i.e. BroadcaseReceiver) in an Application


// There are many properties that can be set when a Notification is submitted.
// For Example:

// 1.) tickerText is a String that flashes on the status line when the event occurs
// 2.) iconResId is an ICON that is displayed on the status line
// 3.) The current system time is recorded in the "when" local variable 
// 4.) The alertOptions contains flags to optionally alert the user through sound, lights and vibration
// 5.) A notification can contain a count(i.e. number voice msgs).  If the number is > 0 then it shows up on the ICON
// 6.) A PendingIntent is created to be fired when the user clicks on the notify msg.  In this case we are asking 
//       to have the NotifyActivity launched. 
// 7.) The notification_id is a unique identifier that can be used to cancel the notification later
// 8.) contentTitle, contentText, contentSubText and contentInfo shows up when the user pulls the notification line down to see more information
// 9.) To make this routine more flexible, I made a few things configurable:
//     action - An intent will be constructed from this action string
//     msgForPendingIntent - The Intent will be stuffed with a parameter containing this String
//                I also added another parameter in the Intent to pass the Notification ID.


    private void add_notification( 
            String tickerText, String contentTitle, String contentText,
            String contentSubText, String contentInfo,
            int iconResId, int count, int notificationId, String action,
            int alertOptions, String msgForPendingIntent)
    {
        Context context = getApplicationContext(); // Makes this code usable in places 
        // where Context isn't as easy as "this"
        Notification.Builder builder = new Notification.Builder(context);

               
        builder.setSmallIcon(iconResId);
        long when = System.currentTimeMillis();
        builder.setWhen(when);     // Time stamp printed with the message
        builder.setDefaults(alertOptions); // vibrate, sound, lights options
        builder.setTicker(tickerText);
        builder.setContentTitle(contentTitle); // first line
        builder.setContentText(contentText); // second line
        builder.setSubText(contentSubText); // third line
        builder.setContentInfo(contentInfo); // A small piece of additional information pertaining to this notification
        builder.setNumber(count);  
        
        Intent myIntent =new Intent(action);
        // Note I can put any parameters I want into this Intent.  
        // I chose to send the notify message along with the id.
        // The id is useful for the Intent to clear the Notification 
        myIntent.putExtra("notify", msgForPendingIntent);
        myIntent.putExtra("id", notificationId);
        
        PendingIntent launchIntent = PendingIntent.getActivity(context,0, myIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT);  //another choice: FLAG_ONE_SHOT  
        
        builder.setContentIntent(launchIntent);
        
        Notification notification = builder.build();

        NotificationManager notificationManager =
                          (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notification);
    }

    /*
     * 
     * The following version will work with old releases of Android.
     * It also works with newer releases, but is marked a deprecated.
     */   

    private void add_notification_deprecated( 
            String tickerText, String expandedTitle, String expandedText,
            int iconResId, int count, int notificationId, String action,
            int alertOptions, String msgForPendingIntent)
    {
        Notification newCountNotification;

        NotificationManager notificationManager;
        Context context = getApplicationContext(); // Makes this code usable in places 
                                                   // where Context isn't as easy as "this"
        
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
       
        
        long when = System.currentTimeMillis();

        newCountNotification = new Notification(iconResId,tickerText, when);
        newCountNotification.number =count;         
        newCountNotification.defaults = alertOptions; 

        Intent myIntent =new Intent(action);
        // Note I can put any parameters I want into this Intent.  
        // I chose to send the notify message along with the id.
        // The id is useful for the Intent to clear the Notification 
        myIntent.putExtra("notify", msgForPendingIntent);
        myIntent.putExtra("id", notificationId);
        
        PendingIntent launchIntent = PendingIntent.getActivity(context,0, myIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT);  //another choice: FLAG_ONE_SHOT  

        newCountNotification.setLatestEventInfo(context, 
                expandedTitle, 
                expandedText,
                launchIntent);
        notificationManager.notify(notificationId, newCountNotification);
    }
    
    
// Utility routine to return a flag word containing the user's wishes stored in the 3 check boxes.

    int getOptions()
    {
        int retval=0;
        // Note that Notification.DEFAULT_ALL turns on everything possible.
        CheckBox sound = (CheckBox)findViewById(R.id.sound);
        CheckBox lights = (CheckBox)findViewById(R.id.lights);
        CheckBox vibrate = (CheckBox)findViewById(R.id.vibrate);
        
        if (sound.isChecked())
            retval |= Notification.DEFAULT_SOUND;

        if (lights.isChecked())
            retval |= Notification.DEFAULT_LIGHTS;

        if (vibrate.isChecked())
            retval |= Notification.DEFAULT_VIBRATE;
        return retval;
    }
    
// Utility to return a String from an EditText as specified by a Resource ID

    private String getStringFromEditText(int resid)
    {
        EditText et = (EditText)findViewById(resid);
        return et.getText().toString();
    }
    

// Utility to return an Integer from an EditText as specified by a Resource ID ... 0 returned
//      if the EditText does not contain a valid Integer.

    private int getIntFromEditText(int resid)
    {
        int retval=0;
        try
        {
            String str = getStringFromEditText( resid);
            retval = Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {}
        return retval;
    }
}