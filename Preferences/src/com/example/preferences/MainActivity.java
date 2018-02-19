package com.example.preferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity {

    MyRadioButtons myRadioButtons;
    MyCheckBox myCheckBox;
    MyEditText myEditText;

    // Called whenever your Activity gives up the Screen
    public void onPause()
    {
        super.onPause();

        // default shared for whole application
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // preference file for a specific Activity
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        SharedPreferences.Editor editObj = prefs.edit();
        myRadioButtons.save(editObj);
        myCheckBox.save(editObj);
        myEditText.save(editObj);
        editObj.commit();
        Log.d("Mine", "onPause completed");
    }

    // Called whenever your Activity regains the Screen
    public void onResume()
    {
        super.onResume();

        // default shared for whole application
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // preference file for a specific Activity
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        myRadioButtons.restore(prefs);
        myCheckBox.restore(prefs);
        myEditText.restore(prefs);

        Log.d("Mine", "onResume completed");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCheckBox = new MyCheckBox(R.id.student, "student");

        int[] radioButtonResources={R.id.unknown, R.id.female, R.id.male};
        myRadioButtons = new MyRadioButtons(radioButtonResources, "gender");

        myEditText = new MyEditText(R.id.name, "name");

    }


    //*****************   Inner class for Checkbox   **************************
    class MyCheckBox implements OnClickListener
    {
        CheckBox myCb;
        String prefName;

        public void save(SharedPreferences.Editor editObj)
        {
            editObj.putBoolean(prefName, myCb.isChecked());
        }
        public void restore(SharedPreferences prefs )
        {
            boolean value = prefs.getBoolean(prefName, false);
            myCb.setChecked(value);        
        }

        MyCheckBox(int resource, String prefName)
        {
            this.prefName = prefName;
            myCb = (CheckBox)findViewById(resource);
            myCb.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            Log.d("Mine", "CheckBox clicked: "+ myCb.getText());
        }
    }
    //*****************  Inner class for EditText   **************************
    class MyEditText implements OnKeyListener
    {
        EditText myEdit;
        String prefName;

        public void save(SharedPreferences.Editor editObj)
        {
            editObj.putString(prefName, myEdit.getText().toString());
        }
        public void restore(SharedPreferences prefs )
        {
            String value = prefs.getString(prefName, "");
            myEdit.setText(value);            
        }

        MyEditText(int resource, String prefName)
        {
            this.prefName = prefName;
            myEdit = (EditText)findViewById(resource);
            myEdit.setOnKeyListener(this);
        }
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    Log.d("Mine", "Enter key with value=" + myEdit.getText());
                    return true;
                }
            return false;
        }

    }
    // *****************  End of Inner class for EditText   *******************

    //*****************   Inner class for RadioButtons ************************
    class MyRadioButtons implements OnClickListener
    {
        int[] myResources;
        RadioButton[] myRbs;
        String prefName;

        public void save(SharedPreferences.Editor editObj)
        {
            int value = -1;
            for (int i=0; i < myResources.length; i++)
            {
                if (myRbs[i].isChecked())
                    value = i;
            }
            editObj.putInt(prefName, value);
        }
        public void restore(SharedPreferences prefs )
        {
            int value = prefs.getInt(prefName, -1);
            for(int i=0; i < myRbs.length; i++)
            {
                boolean checked = false;
                if (value == i)
                    checked = true;

                myRbs[i].setChecked(checked);
            }
        }

        MyRadioButtons(int[] resources, String prefName)
        {
            myResources = resources;
            this.prefName = prefName;
            myRbs = new RadioButton[resources.length];

            for(int i=0; i < resources.length; i++)
            {
                myRbs[i] = (RadioButton)findViewById(resources[i]);
                myRbs[i].setOnClickListener(this);
            }
        }
        @Override
        public void onClick(View v) {
            RadioButton rb= (RadioButton)v;
            Log.d("Mine", "CheckBox clicked: "+ rb.getText());            
        }

    }
  // *************************  End of the Inner Class for Radio Buttons *******
}