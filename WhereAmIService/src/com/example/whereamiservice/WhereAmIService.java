package com.example.whereamiservice;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class WhereAmIService extends Service implements LocationListener{
    Location lastLocation = null;
    boolean locationUpdates = false;
    boolean proximityUpdates = false;

    public void onCreate() {
        logToast( "onCreate of WhereAmIService");
    }

    @Override
    public void onDestroy() {
        logToast( "WhereAmIService onDestroy");
    }

    private void logToast(String s)
    {
        Log.d("Mine",s);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null)
        {
            Log.d("Mine", "Why are we getting a null Intent in onStartCommand????");
            return Service.START_NOT_STICKY;
        }
        String cmd = intent.getStringExtra("cmd");
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        if (cmd.equals(MainActivity.startLocationUpdates))
        {
            // 2 seconds, 1 meter
            locationManager.requestLocationUpdates("gps", 2000, 1, this);
            locationUpdates=true;
        }
        else if (cmd.equals(MainActivity.stopLocationUpdates))
        {
            locationManager.removeUpdates(this);
            locationUpdates=false;
        }
        else if (cmd.equals(MainActivity.startProximityChecking))
        {
            // I realize this is hard-wired, but it keeps this code simpler.

            float radius = 10f; //meters
            double latitude = 42;
            double longitude = -83;

            if (lastLocation != null)
            {
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
            }
            long expiration =-1; // do not expire
            locationManager.addProximityAlert(latitude, longitude, radius, expiration, getPendingIntent());
            proximityUpdates=true;
        }
        else if (cmd.equals(MainActivity.stopProximityChecking))
        {
            locationManager.removeProximityAlert(   getPendingIntent()   );
            proximityUpdates = false;
        }
        else if (cmd.equals(MainActivity.stopService))
        {
			// Shut everything down before shutting down the Service

            if (locationUpdates)
            {
                locationManager.removeUpdates(this);
                locationUpdates=false;
            }
            if (proximityUpdates)
            {
                locationManager.removeProximityAlert(   getPendingIntent()   );
                proximityUpdates = false;
            }
            stopSelf(); // Finally I shut my service down
        }

        return Service.START_NOT_STICKY;
    }

    PendingIntent getPendingIntent()
    {
        Intent intent = new Intent(MyReceiver.MY_RECIEVER_ACTION);
        return PendingIntent.getBroadcast(this, -1, intent, 0);
    }

    // Methods for LocationListener Interface:

    @Override
    public void onLocationChanged(Location location) {
        String msg = String.format("lat=%.4f lng=%.4f", location.getLatitude(), location.getLongitude());
        logToast( msg);    
        lastLocation = location;
    }

    @Override
    public void onProviderDisabled(String provider) {        
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extra) {

    }

}
