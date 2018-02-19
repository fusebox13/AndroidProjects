package com.example.enhancedtextview;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    TextView textView;
    EditText myEditText1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView = (TextView) findViewById(R.id.normalTextView);
// uncomment the following to turn on the Enhanced TextView
        //textView = (MyDrawTextView) findViewById(R.id.myDrawTextView);
        
        myEditText1 = (EditText)findViewById(R.id.myEditText1);
        MyEnterKeyStuff myEnterKeyStuff = new MyEnterKeyStuff();       
    }
    // *******************  Inner Class *************************
    class MyEnterKeyStuff implements OnKeyListener
    {
        EditText myEditText2;
        MyEnterKeyStuff()
        {
            myEditText2 = (EditText)findViewById(R.id.myEditText2);

// Why the MyEnterKeyStuff.this 
            myEditText2.setOnKeyListener(MyEnterKeyStuff.this);
        }
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
                if (keyCode == KeyEvent.KEYCODE_ENTER ) {
                    
                    String str;
                    str = myEditText1.getText().toString();
                    if (str.length() > 0)
                        str +="\n" ;
                    str += myEditText2.getText().toString();
                    
                    String currValue = textView.getText().toString();
                    if (currValue.length() == 0)
                    {
                        textView.setText(str);
                    }
                    else
                        textView.append("\n" + str);
                    
                    myEditText2.setText("");
                    myEditText1.setText("");
                    return true; 
                }
              return false;
        }
    }
    
    // ******************* End of Inner Class *******************
}