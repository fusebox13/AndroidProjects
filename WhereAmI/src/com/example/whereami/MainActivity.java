package com.example.whereami;

import java.util.StringTokenizer;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener{

    LocationManager locationManager;
    MyLocationListener myLocationListener = null;
    Location previousLocation = null;

    String myProvider = LocationManager.GPS_PROVIDER;  //"gps"
    //String myProvider = LocationManager.NETWORK_PROVIDER; //"network"

    Button start_stop_button;
    TextView location_results;
    EditText parameters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location_results = (TextView)findViewById(R.id.results);
        parameters = (EditText)findViewById(R.id.parameters);

        start_stop_button = (Button)findViewById(R.id.start_stop);
        start_stop_button.setOnClickListener(this);
        Button b = (Button)findViewById(R.id.clear);
        b.setOnClickListener(this);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(myProvider);
        receivedLocationUpdate(location);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
        case R.id.start_stop:
            if (myLocationListener == null)
            {
                myLocationListener = new MyLocationListener();
                startupLocationListener();
                start_stop_button.setText("Stop Location Updates");
            }
            else
            {
                locationManager.removeUpdates(myLocationListener);
                myLocationListener = null;
                start_stop_button.setText("Start Location Updates");
            }
            break;
        case R.id.clear:
            location_results.setText("");
            break;
        }
    }
    private void displayResults(String s)
    {
        location_results.setText(s + "\n**" + location_results.getText().toString());
        Log.d("Mine",s);
    }

    private void startupLocationListener()
    {
        long time=2;// 2 seconds default
        float distance=1; // 1 meter default

        String params = parameters.getText().toString().trim();
        StringTokenizer parse = new StringTokenizer(params," ,;:");
        int count = parse.countTokens();
        try
        {
            if (count > 0) 
                distance = Integer.parseInt(parse.nextToken()); // Bad data will cause exceptions
            if (count > 1)
                time = Integer.parseInt(parse.nextToken());
            if (count > 2)
                myProvider = parse.nextToken();
        }
        catch (NumberFormatException e)
        {}
        String s = String.format("requestLocationUpdates: distance=%.0f time=%d provider=%s ", 
                distance, time, myProvider);
        displayResults(s);        
        locationManager.requestLocationUpdates(myProvider, time*1000, distance, myLocationListener);
    }

    private void receivedLocationUpdate(Location location) {
        String latLongString;
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            float delta = 0;
            if (previousLocation != null)
                delta= location.distanceTo(previousLocation);

            previousLocation = location;
            String accuracy="";
            if (location.hasAccuracy())
            {
                accuracy = String.format("accuracy=%.1f", location.getAccuracy());
            }
            latLongString = String.format("Lat=%.5f Long=%.5f Delta=%.6f %s",lat, lng, delta, accuracy);
        } else {
            latLongString = "No location found"; 
        }
        displayResults(myProvider+ " : " +latLongString );
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start updates if necessary
        if (myLocationListener != null)
            startupLocationListener();
    }

    @Override
    protected void onPause() { 
        super.onPause();
        // Stop updates to save power while app paused
        if (myLocationListener != null)
            locationManager.removeUpdates(myLocationListener);
    }

    //*******   Inner Class ****************************************
    class MyLocationListener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location location) {
            receivedLocationUpdate(location);        
        }

        @Override
        public void onProviderDisabled(String provider) {    
            displayResults(provider + " Disabled");        
        }

        @Override
        public void onProviderEnabled(String provider) {
            displayResults(provider + " Enabled");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            String[] status_msgs = { "out of service",
                    "temporarily unavailable", "available" };

            displayResults(provider + " status changed: "+ status_msgs[status]);
        }

    }//*****   End of Inner Class
    
}

