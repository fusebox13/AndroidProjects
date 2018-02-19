package com.example.simpleintent2;

import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class JokeRater extends Activity{


EditText myRating;
TextView theJoke;
Button returnRating;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_rating);
        myRating = (EditText)findViewById(R.id.myRating);
        theJoke = (TextView)findViewById(R.id.theJoke);
        returnRating = (Button)findViewById(R.id.returnRating);
        
        Intent receivedIntent = getIntent();
        String joke = receivedIntent.getStringExtra("joke");
        theJoke.setText(joke);

        returnRating.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                 String str = myRating.getText().toString();

                 //Using the Scanner to break this into separate line
                 ArrayList<String>arr = new ArrayList<String>();
                 Scanner scan = new Scanner(str);
                 while(scan.hasNextLine())
                     arr.add(scan.nextLine());

                 Intent outData = new Intent();
                 outData.putStringArrayListExtra("rating", arr);
                 setResult(Activity.RESULT_OK, outData);
                 // The default result is Activity.RESULT_CANCELED ... SetResult to this value if you want
                 // tell the caller to forget it. 
                 finish();                                    
            }
            
        });
    }

}