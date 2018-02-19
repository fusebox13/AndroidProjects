package com.example.peoplelistview;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    
    private EditText name, age, height, primaryKey;
    
    private ListView listResults;
    Cursor cursor=null;
    ClientDB clientDB = new ClientDB(this);
    
    @Override
    public void onCreate(Bundle savedInstanceState) {        
        super.onCreate(savedInstanceState);
        
        int[] resIds = { R.id.insert, R.id.delete};
        setContentView(R.layout.activity_main);
        
        name = (EditText)findViewById(R.id.name);
        age = (EditText)findViewById(R.id.age);
        height = (EditText)findViewById(R.id.height);
        primaryKey = (EditText)findViewById(R.id.primary_key);

        listResults=(ListView)findViewById(R.id.list);
        for (int i=0; i < resIds.length; i++)
        {
            Button b = (Button)findViewById(resIds[i]);
            b.setOnClickListener(this);
        }
        showPeople();
    }
    
    void showPeople()
    {
        String[] FromColumns = {Constants._ID, Constants.NAME, Constants.AGE, Constants.HEIGHT};
        int[] ToFields = {R.id.rowid, R.id.name, R.id.age, R.id.height};

        // You will need to make a small change to ClientDB.getPeople to eliminate the compile error
        cursor = clientDB.getPeople(FromColumns);

        //startManagingCursor(cursor);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item, cursor, FromColumns ,ToFields,0);

        listResults.setAdapter(adapter);        
    }
    @Override
    public void onDestroy(){
        // Clean up any resources including ending threads, 
        // closing database connections etc.
        super.onDestroy();
        Log.d("Mine", "onDestroy");
        // Let's be a good citizen!!!!
        if (cursor != null)
            cursor.close();
    }
    
    public void onClick(View v)
    {
        String nameStr = name.getText().toString().trim();
        if (nameStr.length() == 0)
             nameStr = null;

        switch (v.getId())
        {
        case R.id.insert:
            try
            {
                
                clientDB.insert( nameStr, 
                    Integer.parseInt(age.getText().toString()), 
                    Double.parseDouble(height.getText().toString()));
            }
            catch(NumberFormatException e)
            {
                Log.d("Mine","OOPS bad user input");
            }
            break;
        case R.id.delete:
            String id = primaryKey.getText().toString().trim();
            if (id.length() == 0)
                clientDB.delete(nameStr); 
            else
            {
                try
                {
                    int id_num = Integer.parseInt(id);
                    clientDB.deleteById ( id_num);
                }
                catch(NumberFormatException e)
                {
                    Log.d("Mine","OOPS bad primary key input");
                }
            }
            break;
        }
        showPeople();
        primaryKey.setText("");
    }
}
