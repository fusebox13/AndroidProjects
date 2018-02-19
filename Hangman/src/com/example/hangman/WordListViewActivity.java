package com.example.hangman;

import java.util.ArrayList;

import fileutils.FileUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class WordListViewActivity extends Activity{
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.word_list_view_activity);
	      
	      Intent i = this.getIntent();
	      String fileName = i.getStringExtra("fileName");
	      
	      FileUtils fileUtils = new FileUtils(this);
	      
	      ArrayList<String> wordList = fileUtils.getWordListAsArrayList(fileName);
	      
	      String textViewString = "";
	      for(int j = 0; j < wordList.size(); j++)
	      {
	    	  textViewString+=wordList.get(j) + "\n";
	    	  
	      }
	      
	      setTitle(fileName.substring(0, fileName.length() - 4));
	      
	      TextView t = (TextView)this.findViewById(R.id.wordListViewTextView);
	      t.setText(textViewString);
	   }

}
