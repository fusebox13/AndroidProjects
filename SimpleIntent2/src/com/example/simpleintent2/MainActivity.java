package com.example.simpleintent2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    Spinner myJokeSpinner, myPhraseSpinner;
    String theJoke="No Joke Selected";
    String thePhrase="No Phrase Selected";
    Button callJokeRater, callPhraseCompleter;
    TextView myJokeRating, myPhraseCompletion;

    static final int MY_JOKE_RATER_INTENT_CALL=1;
    static final int MY_PHRASE_COMPLETION_INTENT_CALL=2;
    
    static final String JOKE_RATER_ACTION = "edu.wccnet.clem.joke_rater.action";
    static final String PHRASE_COMPLETER_ACTION = "edu.wccnet.clem.phrase_completer_action";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callJokeRater = (Button)findViewById(R.id.callJokeRater);
        myJokeRating = (TextView)findViewById(R.id.jokeRating);
        myJokeSpinner = (Spinner)findViewById(R.id.myJokeSpinner);
        initJokeSpinner();

        callPhraseCompleter = (Button)findViewById(R.id.callPhraseCompletion);
        myPhraseCompletion = (TextView)findViewById(R.id.phraseCompletion);
        myPhraseSpinner = (Spinner)findViewById(R.id.myPhraseSpinner);
        initPhraseSpinner();


        callJokeRater.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Note that  a simple "this" will refer to the inner anonymous class

                Intent intent = new Intent(JOKE_RATER_ACTION);
                intent.putExtra("joke", theJoke);

                startActivityForResult(intent, MY_JOKE_RATER_INTENT_CALL /* Request */);

            }
        });
        callPhraseCompleter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Note that  a simple "this" will refer to the inner anonymous class
                Intent intent = new Intent(PHRASE_COMPLETER_ACTION);

                intent.putExtra("phrase", thePhrase);
                intent.putExtra("anyOtherJunk", "Some Junk we won't use");

                startActivityForResult(intent, MY_PHRASE_COMPLETION_INTENT_CALL /* Request */);

            }
        });


    }
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);

        String lineSeparator = System.getProperty("line.separator"); // ""/n" on Linux
        if (resCode != Activity.RESULT_OK)
        {
            Log.d("Mine", "Returning without proper Result code");
            return;
        }
        switch(reqCode)
        {
        case MY_JOKE_RATER_INTENT_CALL:
            ArrayList<String> ratings = data.getStringArrayListExtra("rating");

            StringBuilder sb = new StringBuilder();
            for (int i=0; i< ratings.size(); i++)
                sb.append(ratings.get(i)+ lineSeparator);
            myJokeRating.setText(sb.toString());
            break;

        case MY_PHRASE_COMPLETION_INTENT_CALL:
            String completion = data.getStringExtra("phraseCompletion");
            myPhraseCompletion.setText(completion);
            break;
        default:
            Log.d("Mine","Shouldn't happen");
            break;

        }

    }

    // ****************************************************
    // A little messy ... if you want a simpler example, just use and EditText box and type in the Joke manually
    private String[] jokeArray;
    private void initJokeSpinner()
    {
        jokeArray = getResources().getStringArray(R.array.jokes_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jokes_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        myJokeSpinner.setAdapter(adapter);
        myJokeSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
            {
    
                @Override
                public void onItemSelected(AdapterView<?> av, View v,
                        int pos, long id) {
                    theJoke = jokeArray[pos];
    
                }
    
                @Override
                public void onNothingSelected(AdapterView<?> av) {
                    Log.d("Mine", "onNothingSelected .... when is this called?");
    
                }
    
            }
        );
    }

    // ****************************************************
    // A little messy ... if you want a simpler example, just use and EditText box and type in the Joke manually
    private String[] phraseArray;
    private void initPhraseSpinner()
    {
        phraseArray = getResources().getStringArray(R.array.phrase_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phrase_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        myPhraseSpinner.setAdapter(adapter);
        myPhraseSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
            {
    
                @Override
                public void onItemSelected(AdapterView<?> av, View v,
                        int pos, long id) {
                    thePhrase = phraseArray[pos];
    
                }
    
                @Override
                public void onNothingSelected(AdapterView<?> av) {
                    Log.d("Mine", "onNothingSelected .... when is this called?");
    
                }
    
            }
        );
    }
}
