package com.example.mygeocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements OnKeyListener{

    class LatLong
    {
        double lat=0, lng=0;
        LatLong(double plat, double plng)
        {
            lat = plat;
            lng = plng;
        }
    }

    TextView results;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results = (TextView)findViewById(R.id.results);

        EditText editText = (EditText)findViewById(R.id.input_name);
        editText.setOnKeyListener(this);

        editText = (EditText)findViewById(R.id.input_location);
        editText.setOnKeyListener(this);


    }
    public boolean onKey(View view, int keyCode, KeyEvent event)
    {         
        // If the event is a key-down event on the "enter" button
        EditText editText = (EditText)view;
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) 
        {
            results.setText(""); // Clear last output
            String cmdStr = editText.getText().toString().trim();
            switch (view.getId())
            {
            case R.id.input_name:
                processGetFromLocationName( cmdStr);
                break;
            case R.id.input_location:
                processGetFromLocation( cmdStr);
                break;
            }
            return true;
        }
        return false;

    }

    public void processGetFromLocationName(String names)
    {
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> geoInfo = gc.getFromLocationName(names, 10/*num results*/);
            if (geoInfo == null)
                displayInformation("null return from getFromLocationName");
            else
            {
                for (int i=0; i < geoInfo.size(); i++)
                {
                    Address address = geoInfo.get(i);
                    displayInformation( formatAddress(address));
                }
            }

        } catch (IOException e) {
            Log.d("Mine", "getFromLocationName error:"+e);
        }
    }

    public void processGetFromLocation(String cmdStr)
    {
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            LatLong latLong = getLatLong(cmdStr);
            if (latLong == null)
                return;

            List<Address> geoInfo = gc.getFromLocation(latLong.lat, latLong.lng, 10 /*num results*/);

            if (geoInfo == null)
                displayInformation("null return from getFromLocation");
            else
            {
                for (int i=0; i < geoInfo.size(); i++)
                {
                    Address address = geoInfo.get(i);
                    displayInformation( formatAddress(address));
                }
            }

        } catch (IOException e) {
            Log.d("Mine", "getFromLocation error:"+e);
        }

    }



    private String formatAddress(Address address)
    {
        StringBuilder buff= new StringBuilder();

        int numAddressLines = address.getMaxAddressLineIndex();
        for (int i = 0; i < numAddressLines; i++)
            buff.append(address.getAddressLine(i)).append("\n");

        String featureName = address.getFeatureName();
        if (featureName != null)
            buff.append(  String.format("featureName=%s %n", featureName));        
        String locality = address.getLocality();
        if (locality != null)
            buff.append(  String.format("locality=%s %n", locality));
        String postalCode = address.getPostalCode();
        if (postalCode != null)
            buff.append( String.format("postalCode=%s %n", postalCode));
        String countryName = address.getCountryName();
        if (countryName != null)
            buff.append(  String.format("countryName=%s %n", countryName));
        if (address.hasLatitude() && address.hasLongitude())
        {
            buff.append( String.format("Latitude:%.5f Longitude=%.5f %n", address.getLatitude(), address.getLongitude() ));
        }
        String phone = address.getPhone();
        if (phone != null)
            buff.append(  String.format("phone=%s %n", phone));
        String url = address.getUrl();
        if (url != null)
            buff.append(  String.format("url=%s %n", url));


        return buff.toString();
    }

    // utility to parse a string for 2 doubles
    LatLong getLatLong(String str)
    {
        StringTokenizer stnizer= new StringTokenizer(str," ,:");
        double lat=0, lng=0;
        if (stnizer.hasMoreTokens())
        {
            try
            {
                lat = Double.parseDouble(stnizer.nextToken());
                if (stnizer.hasMoreTokens())
                    lng = Double.parseDouble(stnizer.nextToken());
                else
                {
                    displayInformation( "Need 2 numbers:"+ str); 
                    return null;
                }
                return new LatLong(lat, lng);

            }
            catch(NumberFormatException e)
            {
                displayInformation( "Bad input for latitude longitude: "+ str); 
                return null;
            }            
        }
        else
        {
            displayInformation( "Need 2 numbers:"+ str); 
            return null;
        } 

    }

    // Utility to stick a new string on the top of the scrollable text field
    void displayInformation(String str)
    {
        results.setText( str +     "\n***" + results.getText().toString());
    }
}