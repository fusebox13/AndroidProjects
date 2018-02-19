package com.example.rawsql;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    TextView results;
    EditText cmd;
    Button clear;
    MyHelper myDB;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new MyHelper(this);

        results = (TextView)findViewById(R.id.results);

        clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener( new OnClickListener(){
            public void onClick(View v)
            {
                results.setText("");
            }
        });


        cmd = (EditText)findViewById(R.id.cmd);
        cmd.setOnKeyListener(new OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String cmdStr = cmd.getText().toString().trim();
                    Log.d("Mine", "CMD="+cmdStr);
                    String cmpStr = cmdStr.toLowerCase();

                    if (cmpStr.startsWith("select"))
                        doQuery(cmdStr);
                    else
                        doSQLCmd(cmdStr);

                    return true;
                }
                return false;
            }

        });


    }

    void doSQLCmd(String cmdStr)
    {
        try
        {
            SQLiteDatabase db = myDB.getWritableDatabase();
            db.execSQL(cmdStr);
            results.setText("Good CMD: "+cmdStr +"\n*********\n"
                    + results.getText().toString());
        }
        catch (SQLException e)
        {
            results.setText(cmdStr + "\nERROR:"+ e +"\n*********\n"
                    + results.getText().toString());
        }
    }

    void doQuery(String cmdStr)
    {
        try
        {
            SQLiteDatabase db = myDB.getReadableDatabase();
            Cursor cursor = db.rawQuery(cmdStr, null);

            int rows = cursor.getCount();
            int cols = cursor.getColumnCount();
            String[] colNames = cursor.getColumnNames();

            StringBuffer sb = new StringBuffer();
            sb.append("rows="+rows + " cols=" +cols +"\n");
            sb.append("******  COLUMN NAMES *******\n");
            for (int i=0; i < colNames.length; i++)
            {
                sb.append(colNames[i]+", ");
            }
            sb.append("\n******   DATA ********\n");

            while (cursor.moveToNext()) { 
                for (int i=0; i < cols; i++)
                {
                    sb.append(cursor.getString(i) +", ");
                }
                sb.append("\n");            
            }
            sb.append("********** end of cursor *************\n");
            results.setText(sb + results.getText().toString());
        }
        catch (SQLException e)
        {
            results.setText(cmdStr + "\nERROR:"+ e +"\n*********\n"
                    + results.getText().toString());
        }
    }

}
