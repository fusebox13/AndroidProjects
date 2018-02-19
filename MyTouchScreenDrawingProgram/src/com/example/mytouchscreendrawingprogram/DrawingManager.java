package com.example.mytouchscreendrawingprogram;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class DrawingManager extends Activity  implements OnClickListener, FileOperations{

    WordFileManager wordFileManager = new WordFileManager(this);
    private String currentFileName ;
    TextView fileNameDisplay;

    MyDrawingView myDrawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
                
        setContentView(R.layout.drawing_manager);
        int[] resources= {R.id.red, R.id.green, R.id.blue, R.id.rectangle, R.id.oval, R.id.line};

        for (int i=0; i < resources.length; i++)
        {
            RadioButton rb = (RadioButton)findViewById(resources[i]);
            rb.setOnClickListener(this);
        }
        Button b = (Button)findViewById(R.id.clear);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.undo);
        b.setOnClickListener(this);

        myDrawingView = (MyDrawingView)findViewById(R.id.myDraw1);
        myDrawingView.setOnTouchListener(myDrawingView);

        myDrawingView.update(R.id.red); // Initialize to red pen
        myDrawingView.update(R.id.rectangle); // Start out drawing rectangles

        RadioButton rb = (RadioButton)findViewById(R.id.red);
        rb.setChecked(true);  // make the red radio button checked
        rb = (RadioButton)findViewById(R.id.rectangle);
        rb.setChecked(true);  // make the rectangle radio button checked

        fileNameDisplay = (TextView)findViewById(R.id.file_name);
        String lastFileName = wordFileManager.getMyLastFileName();

        
        Intent receivedIntent = getIntent();
        String getLastDrawing = receivedIntent.getStringExtra("getLastDrawing");
        // getLastDrawing will be null if we are called from the Android System
        
        // Otherwise, Drawing Manager was called from MainActivity.
        // MainActivity will set getLastDrawing = "returnImmediately" if this call is 
        // made from inside of onCreate. In this case we get the current drawing and return it. 
        
        //It will have some other value if it was called
        // from the OptionsMenu in MainActivity.  In this case we let the user edit and 
        // change things.  We don't return until the User has clicked the appropriate button
                 
        if (getLastDrawing != null  )
        {
            if ("returnImmediately".equals(getLastDrawing))
            {
                Intent outData = new Intent();    
                ArrayList<Shape> arr = getShapeArr(lastFileName);
                
                // Note that for ArrayList<String> you could use:
                // putStringArrayListExtra(String name, ArrayList<String> value)
                
                outData.putExtra("shapes", arr);
                outData.putExtra("fileName", lastFileName);
                setResult(Activity.RESULT_OK, outData);
          
                finish();  
            }
            // We want the "Return Drawing" button visible and enabled
            Button return_drawing = (Button)findViewById(R.id.return_drawing);
            return_drawing.setVisibility(View.VISIBLE);
            return_drawing.setOnClickListener(this);
        }
        open(lastFileName);

    }
    
    private ArrayList<Shape> getShapeArr(String fileName)
    {
        ArrayList<Shape> arr;
        Serializable obj = wordFileManager.getSerializable(fileName);
        if (obj == null)
            arr = new ArrayList<Shape>(); // Empty list
        else
            arr = (ArrayList<Shape>)obj;
        
        return arr;
    }
    
    @Override
    public void open(String fileName) {
        changeFileName(fileName);        
        ArrayList<Shape> arr = getShapeArr(fileName);
        myDrawingView.setDrawing( arr);
        wordFileManager.storeMyFileName(currentFileName);
    }

    @Override
    public void onClick(View v) 
    {
        if (v.getId() != R.id.return_drawing)
        {
            myDrawingView.update(v.getId());
        }
        else
        {
            // Our user has made any necessary changes and we are ready to return
            // to the MainActivity caller
            
            Intent outData = new Intent();    
            ArrayList<Shape> arr = getShapeArr(currentFileName);
            outData.putExtra("shapes", arr);
            outData.putExtra("fileName", currentFileName);
            setResult(Activity.RESULT_OK, outData);
      
            finish();  
        }
            
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawing_main, menu);
        return true;
    }



    // User has selected an item from the Options Menu
    public boolean onOptionsItemSelected(MenuItem item)
    {            
        switch (item.getItemId())
        {
        case R.id.save:
            Log.d("Mine", "save option menu");
            saveDrawing();
            return true;
        case R.id.open_file:
            Log.d("Mine", "open option menu");
            MyAlertDialogHandler dho = new MyAlertDialogHandler(this, wordFileManager, 
                    "Select Category to Open, or hit Back button", 
                    this, DIALOG_TYPE.Open);
            return true;
        case R.id.new_file:
            Log.d("Mine", "new option menu");
            NewFileDialogHandler nfdh = new NewFileDialogHandler(this, this);
            nfdh.show();
            return true;
        case R.id.delete_file:
            Log.d("Mine", "delete option menu");
            MyAlertDialogHandler dhd = new MyAlertDialogHandler(this, wordFileManager, 
                    "Select Category to Delete, or hit Back button", 
                    this, DIALOG_TYPE.Delete);
            return true;



        }

        return super.onOptionsItemSelected(item);
    }




    public void onPause()
    {
        super.onPause();
        saveDrawing();
    }
    private void saveDrawing()
    {
        ArrayList<Shape> arr = myDrawingView.getDrawing();
        wordFileManager.saveSerializable(arr, currentFileName);
        wordFileManager.storeMyFileName(currentFileName);
    }

    private void changeFileName(String fileName)
    {
        currentFileName = fileName;    
        if (currentFileName == null)
            currentFileName = "default_drawing.drw";
        fileNameDisplay.setText(currentFileName);
        wordFileManager.storeMyFileName(currentFileName);       
    }

    @Override
    public void newFile(String fileName) {    
        myDrawingView.update(R.id.clear);
        changeFileName(fileName);
    }


    @Override
    public void delete(String fileName) 
    {
        wordFileManager.deleteFile(fileName);    
    }

}