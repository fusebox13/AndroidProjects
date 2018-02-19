package com.example.simpleintent2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class PhraseCompleter extends Activity{


EditText myCompletion;
TextView thePhrase;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrase_completer);
        myCompletion = (EditText)findViewById(R.id.myCompletion);
        thePhrase = (TextView)findViewById(R.id.thePhrase);
        
        Intent receivedIntent = getIntent();
        String sPhrase = receivedIntent.getStringExtra("phrase");
        thePhrase.setText(sPhrase);

        
        myCompletion.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ( keyCode == KeyEvent.KEYCODE_ENTER) 
                    {
                        String str = myCompletion.getText().toString();
                        Intent outData = new Intent();
                        outData.putExtra("phraseCompletion", str);
                        setResult(Activity.RESULT_OK, outData);
                        // The default result is Activity.RESULT_CANCELED ... 
                        //        SetResult to this value if you want 
                        //        tell the caller to forget it. 
                        finish();                    
                       
                        return true; 
                    }
              return false;
            }
          });
    }

}