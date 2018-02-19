package com.example.implicitintents;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    
    static final int CONTACTS_INTENT = 1;
    static final int VOICE_RECOGNITION=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] resources = {R.id.browser, R.id.makeCall, R.id.map, R.id.pick_contact, 
                R.id.speech_recognition, R.id.text_message};
        for (int i=0; i < resources.length; i++)
        {
            Button b = (Button)findViewById(resources[i]);
            b.setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Uri uri;
        switch (v.getId())
        {
        case R.id.browser:
            uri = Uri.parse("http://amazon.com");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            break;
            
        case R.id.makeCall:
            uri = Uri.parse("tel:7349733471");
            //intent = new Intent(Intent.ACTION_DIAL, uri);
            //Needs: <uses-permission android:name="android.permission.CALL_PHONE"/> 
            intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
            break;
            
        case R.id.map:
            uri = Uri.parse("geo:42.2608, -83.6600");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            break;
            
        case R.id.pick_contact:
            intent = new Intent(Intent.ACTION_PICK);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            startActivityForResult(intent, CONTACTS_INTENT);
            break;
        
        case R.id.speech_recognition:
            intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            // Specify free form input
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, 
                            "or forever hold your peace");
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
            
            startActivityForResult(intent,VOICE_RECOGNITION);
            break;
            
        case R.id.text_message:
             intent = new Intent(Intent.ACTION_SENDTO,  Uri.parse("sms:"+"7349733470"));
             intent.putExtra("sms_body", "Sent from cps251 android app.");
             startActivity(intent);
            break;
        }
    
        
    }
    
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != RESULT_OK)
        {
            Toast.makeText(this, "onActivityResult NOT OK", Toast.LENGTH_SHORT).show();
            return;
        }
        switch(requestCode)
        {
        case CONTACTS_INTENT:
            Uri uri = data.getData();
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
            
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            break;

        case VOICE_RECOGNITION:
             ArrayList<String> results;
             results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
             StringBuilder sb = new StringBuilder();
             for (int i=0; i< results.size(); i++)
             {
                 Log.d("Mine",results.get(i));
                 sb.append(results.get(i));
             }
             Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
            break;
        }
    }

}
