package com.example.hangman;

import fileutils.FileUtils;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class CategoryManagerDialog extends Dialog implements OnClickListener
{
	EditText e;
	FileUtils fileUtils;
	Context context;
	CategoryManagerInterface cmi;


	public CategoryManagerDialog(Context context, CategoryManagerInterface cmi) {
	  super(context);
	  this.context = context;
	  this.cmi = cmi;
	  fileUtils = new FileUtils(context);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	
	  setTitle("Create New Category");
	  setContentView(R.layout.category_manager_dialog);
	  
	  Button b = (Button)this.findViewById(R.id.Add);
	  b.setOnClickListener(this);
	  e = (EditText)this.findViewById(R.id.editText1);
	  e.setOnKeyListener(new View.OnKeyListener() {
		
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
		
			if (event.getAction() == KeyEvent.ACTION_DOWN)
	            if (keyCode == KeyEvent.KEYCODE_ENTER && e.getText().toString() != "" && e.getText().toString() != null) {
	            	addCategory();
	            	dismiss();
	            	return true;	
	            }
			
			return false;
		}
	});
	  
	}
	
	
	public void onClick(View v) {
	   int index = v.getId();
	   switch(index) {
	   case R.id.Add:
		   addCategory();
		   break;
	   }
	   dismiss();
	}
	
	
	@SuppressLint("DefaultLocale")
	public void addCategory() {
		String s = e.getText().toString();
		s = s.substring(0,1).toUpperCase() + s.substring(1);
		
		if (s != null && s != "") {
			fileUtils.createFile(s + ".txt");
			cmi.addCategory(s);
			
		}
	}


}
