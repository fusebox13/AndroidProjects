package com.example.hangman;

import customview.HangmanView;
import fileutils.FileUtils;
import hangman.HangmanGame;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener {
	
	TextView display = null;
	TextView categoryDisplay = null;
	HangmanGame h = null;
	HangmanView hv = null;
	private final int CATEGORY_MANAGER = 1;
	FileUtils fileUtils;
	private static final String CATEGORY_MANAGER_ACTION = "edu.dan.categorymanager";
	
	int[] resources = {R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, R.id.h,
			R.id.i, R.id.j, R.id.k, R.id.l, R.id.m, R.id.n, R.id.o, R.id.p, R.id.q, R.id.r,
			R.id.s, R.id.t, R.id.u, R.id.v, R.id.w, R.id.x, R.id.y, R.id.z};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		
	}
	
	public void init() {
		resetButtons();
		fileUtils = new FileUtils(this);
		fileUtils.restoreDefaultCategories();
		
		display = (TextView)findViewById(R.id.display);
		categoryDisplay = (TextView)findViewById(R.id.categoryView);
		
		h = new HangmanGame(this, display);
		hv = (HangmanView)findViewById(R.id.hangmanView);
		registerForContextMenu(hv);
		
		categoryDisplay.setText(h.getCategoryName());
		getPrefs();
	}
	
	public void getPrefs()
	{
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("theme", "blackOnWhite");
        
        if(theme.equals("blackOnWhite")) {
        	categoryDisplay.setBackgroundColor(Color.WHITE);
	    	categoryDisplay.setTextColor(Color.BLACK);
        	hv.themeBlackOnWhite();	
        }	
        
        if(theme.equals("whiteOnBlack")) {
        	categoryDisplay.setBackgroundColor(Color.BLACK);
	    	categoryDisplay.setTextColor(Color.WHITE);
        	hv.themeWhiteOnBlack();
        }
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);

        if (resCode != Activity.RESULT_OK) 
            return;
        
        switch(reqCode) {
        case CATEGORY_MANAGER:
        	h.setCategory(data.getStringExtra("category"));
        	h.newGame();
        	hv.resetDrawing();
			resetButtons();
			categoryDisplay.setText(h.getCategoryName());
			toastMessage("Category: " + h.getCategoryName());
            break;

       
        default:
            break;
        }
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh:
			h.newGame();
			hv.resetDrawing();
			resetButtons();
			return true;
		case R.id.categoryManager:
			openCategoryManager();
			return true;
		
        }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,  View v, ContextMenu.ContextMenuInfo menuInfo) {
	    
		super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater;
	    inflater = getMenuInflater();
	    inflater.inflate(R.menu.hangman_view_context_menu, menu);
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {  
	    super.onContextItemSelected(item);
	    
	    switch (item.getItemId()) {
	    case R.id.whiteOnBlack:
	    	hv.themeWhiteOnBlack();
	    	categoryDisplay.setBackgroundColor(Color.BLACK);
	    	categoryDisplay.setTextColor(Color.WHITE);
	    	saveThemeToPreferences("whiteOnBlack");
	    	break;
	    case R.id.blackOnWhite:
	    	hv.themeBlackOnWhite();
	    	categoryDisplay.setBackgroundColor(Color.WHITE);
	    	categoryDisplay.setTextColor(Color.BLACK);
	    	saveThemeToPreferences("blackOnWhite");
	    	break;
	   
	    }
	    return true;
	}
	
	
	public void saveThemeToPreferences(String theme)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putString("theme", theme);
        prefEditor.commit();
	}
	
	@Override
	public void onClick(View v) {
		
		Button b = (Button)v;
		
		if (h.makeGuess("" + b.getText()) == false)
			hv.incrementDrawing();
		
		b.setAlpha(0.5f);
		b.setOnClickListener(null);
		
	}
	
	public void openCategoryManager() {
		
		Intent intent = new Intent(CATEGORY_MANAGER_ACTION);
        startActivityForResult(intent, CATEGORY_MANAGER);
	}
	
	public void toastMessage(String s) {
		Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        toast.show();	
	}
	
	public void resetButtons() {
		
		for (int i = 0; i < resources.length; i++) {
			Button b = (Button)findViewById(resources[i]);
			b.setOnClickListener(this);
			b.setAlpha(1f);
		}
	}
	
	public void disableButtons() {
		
		for(int i = 0; i < resources.length; i++) {
			Button b = (Button)findViewById(resources[i]);
			b.setOnClickListener(null);
		}	
	}

}
