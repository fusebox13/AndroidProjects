package com.example.myoneliner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.net.Uri;


public class MainActivity extends Activity {

    public static final String MY_ONE_LINER_ACTION="edu.wccnet.oneliner.ACTION";
    public static final String MY_RANDOM_ONE_LINER_ACTION="edu.wccnet.random_oneliner.ACTION";
    
    
    ArrayAdapter<String> oneliner_array_adapter;
    ListView myListView;
    String[] oneliner_array;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate your view
        setContentView(R.layout.activity_main);

        // Get references to UI widgets
        myListView = (ListView)findViewById(R.id.oneLinerListView);

        oneliner_array = (String[])getResources().getStringArray(R.array.oneliner_array);
        
        Intent intent = getIntent();
        String action = intent.getAction();
        if (MY_RANDOM_ONE_LINER_ACTION.equals(action))
        {
            int index = (int)(oneliner_array.length * Math.random());
            Intent outData = new Intent();
            outData.putExtra("oneLiner", oneliner_array[index]);
            setResult(Activity.RESULT_OK, outData);
            finish();
        }
        else if (MY_ONE_LINER_ACTION.equals(action))
        {
            Intent outData = new Intent();
            String cmd=intent.getStringExtra("cmd");
            boolean cmd_good=true;
            if ("req_size".equals(cmd))
                outData.putExtra("size", oneliner_array.length);
            else if ("req_indexed".equals(cmd))
            {
                int index = intent.getIntExtra("index", 0);
                outData.putExtra("oneLiner", oneliner_array[index]);
            }
            else
            {
                cmd_good=false;
            }
            if (cmd_good)
                setResult(Activity.RESULT_OK, outData);
            finish();            
        }
        else if (Intent.ACTION_PICK.equals(action))
            action_pick(intent);
        else if (Intent.ACTION_MAIN.equals(action))
            action_pick(intent); // If called as a main activity
        else
        {
            Log.d("Mine", "Exitting ... don't support ACTION="+action);
            finish();
        }
    }
    
    private void action_pick(Intent intent)
    {
        String dataPath = null;
        Uri uri= intent.getData();
        if (uri != null)
            dataPath = uri.toString();

        Log.d("Mine", "ACTION_PICK with dataPath="+dataPath);
        
        oneliner_array_adapter = new ArrayAdapter<String>(this, 
                R.layout.list_item, oneliner_array);

        // Bind the array adapter to the listview.
        myListView.setAdapter(oneliner_array_adapter);



        myListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                // Move the cursor to the selected item
                Log.d("Mine", "Item picked="+pos);
                
                Intent outData = new Intent();
                outData.putExtra("oneLiner", oneliner_array[pos]);
                setResult(Activity.RESULT_OK, outData);
                finish();
            }
        });
    }
}