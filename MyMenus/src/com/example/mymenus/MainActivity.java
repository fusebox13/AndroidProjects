package com.example.mymenus;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    boolean sometimes = true;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // NOTE WE DON'T NEED A LAYOUT ... NO Screen
        //getOverflowMenu();
    }
    /*
    private void getOverflowMenu() {

        try {
           ViewConfiguration config = ViewConfiguration.get(this);
           Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
           if(menuKeyField != null) {
               menuKeyField.setAccessible(true);
               menuKeyField.setBoolean(config, false);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   */
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        
        Log.d("Mine", "onCreateOptionsMenu");

        /*menu.add(Menu.NONE, SETTINGS, Menu.NONE, "settings...");
        menu.add(Menu.NONE, ADD,      Menu.NONE, "Add");
        menu.add(Menu.NONE, REFRESH,  Menu.NONE, "Refresh");
        menu.add(Menu.NONE, TOGGLE,   Menu.NONE, "Turn OFF Sometimes");
        menu.add(Menu.NONE, ALWAYS,   Menu.NONE, "Always");
        menu.add(Menu.NONE, SOMETIMES,Menu.NONE, "Sometimes");
        */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d("Mine", "onOptionsItemSelected: "+ item.getItemId());
        
        switch (item.getItemId())
        {
        case R.id.settings:
            Toast toast = Toast.makeText(this /*Needs a Context*/, "Settings", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        case R.id.add:
            // Shorter version with chained call
            Toast.makeText(this /*Needs a Context*/, "Add", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.refresh:
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.toggle:
            Toast.makeText(this, "TOGGLE", Toast.LENGTH_SHORT).show();
            sometimes = !sometimes;
            if (sometimes)
            {
                String s = getResources().getString(R.string.turn_off_sometimes);
                item.setTitle(s);
            }
            else
            {
                String s = getResources().getString(R.string.turn_on_sometimes);
                item.setTitle(s);
            }
            return true;
        case R.id.always:
            Toast.makeText(this, "ALWAYS", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.sometimes:
            Toast.makeText(this, "SOMETIMES", Toast.LENGTH_SHORT).show();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
 // Note from the Toast that the onPrepareOptionsMenu call is  made before EVERY press of the Options key
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
      super.onPrepareOptionsMenu(menu);

      Log.d("Mine", "onPrepareOptionsMenu: ");
      Toast.makeText(this, "onPrepareOptionsMenu", Toast.LENGTH_SHORT).show();
      
      MenuItem sometimesItem = menu.findItem(R.id.sometimes);
      MenuItem toggleItem = menu.findItem(R.id.toggle);
      
      if (sometimes)
      {
          String s = getResources().getString(R.string.turn_off_sometimes);
          toggleItem.setTitle(s);
          sometimesItem.setVisible(true);
      }
      else
      {
          String s = getResources().getString(R.string.turn_on_sometimes);
          toggleItem.setTitle(s);
          sometimesItem.setVisible(false);         
      }
      return true;
    }

}
