package com.example.myonelinertester;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener{

    static final int CMD_PICK = 1;
    static final int CMD_SIZE = 2;
    static final int CMD_RANDOM = 3;
    static final int CMD_INDEX = 4;
    
    TextView results_textview ;
    Button buttonRandom, buttonSize, buttonPick;
    EditText indexCmd;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        results_textview = (TextView)findViewById(R.id.results);
        buttonRandom = (Button)findViewById(R.id.random);
        buttonSize = (Button)findViewById(R.id.size);
        buttonPick = (Button)findViewById(R.id.pick);
        indexCmd = (EditText)findViewById(R.id.index);
        
        buttonRandom.setOnClickListener(this);
        buttonSize.setOnClickListener(this);
        buttonPick.setOnClickListener(this); 
        
        indexCmd.setOnKeyListener(new OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  
                    String str="";
                    try
                    {
                        str = indexCmd.getText().toString();
                        int value = Integer.parseInt(str);
                        Intent intent = new Intent("edu.wccnet.oneliner.ACTION");
                        intent.putExtra("cmd", "req_indexed");
                        intent.putExtra("index", value);
                        startActivityForResult(intent, CMD_INDEX);
                    }
                    catch (NumberFormatException e)
                    {
                        results_textview.setText("Bad index:"+str);
                    }

                    return true;
                }
                return false;
            }

        });

        
    }
    
    public void onClick(View v)
    {
        Intent intent;
        
        switch (v.getId())
        {
        case R.id.pick:
            intent = new Intent(Intent.ACTION_PICK,
                                        Uri.parse("content://edu.wccnet.oneliner/"));
            startActivityForResult(intent, CMD_PICK); 
            break;
        case R.id.random:
            intent = new Intent("edu.wccnet.random_oneliner.ACTION");
            startActivityForResult(intent, CMD_RANDOM); 
            break;
        case R.id.size:
            intent = new Intent("edu.wccnet.oneliner.ACTION");
            intent.putExtra("cmd", "req_size");
            startActivityForResult(intent, CMD_SIZE);
            break;
        }
    }

    @Override 
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);

        String results="";
        Log.d("Mine", "onActivityResult");
        if (resCode != Activity.RESULT_OK)
        {
            results ="Failed Intent Call";
        }
        else
        {
            switch (reqCode)
            {
            case CMD_PICK:
                results ="Picked:" + data.getStringExtra("oneLiner");
                break;
            case CMD_INDEX:
                results ="Indexed:" + data.getStringExtra("oneLiner");
                break;
            case CMD_RANDOM:
                results ="Random:" + data.getStringExtra("oneLiner");
                break;
            case CMD_SIZE:
                results ="Size:" + data.getIntExtra("size", -1);
                break;
            }
        }
        results_textview.setText(results);
                
    }
}