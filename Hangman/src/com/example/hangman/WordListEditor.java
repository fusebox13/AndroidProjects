package com.example.hangman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.StringTokenizer;

import enumerations.InsertionMode;
import enumerations.Sort;
import fileutils.FileUtils;
import fileutils.WordListDownloader;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;



@SuppressLint("DefaultLocale")
public class WordListEditor extends Activity implements AdapterView.OnItemClickListener, OnKeyListener, OnTouchListener,
WordListInterface, WordListDownloaderInterface{
	
	ArrayList<String> wordList;
	ListView wordListView;
	ArrayAdapter<String> wordListAdapter;
	FileUtils fileUtils;
	EditText wordListEditText;
	String fileName;
	int wordListPosition;
	Sort sortMethod = Sort.REVERSE_ALPHA;
	String sortMenuText = "Sort Z-A";
	InsertionMode insertionMode = InsertionMode.ADD;
	public static final int VOICE_RECOGNITION = 1;
	
	InputMethodManager keyboard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_list_editor);
		init();
	}
	
	
	public void init() {
		fileUtils = new FileUtils(this);
		Intent i = getIntent();
		fileName = i.getStringExtra("fileName");
		wordList = fileUtils.getWordListAsArrayList(fileName);
		
		wordListView = (ListView)findViewById(R.id.wordListView);
		wordListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordList);
		wordListView.setAdapter(wordListAdapter);
		wordListView.setOnItemClickListener(this);
		wordListAdapter.notifyDataSetChanged();
		
		setTitle("Category: " + fileName.substring(0, fileName.length()-4));
		
		registerForContextMenu(wordListView);
		
		wordListEditText = (EditText)this.findViewById(R.id.wordListEditText);
		wordListEditText.setOnKeyListener(this);
		
		wordListEditText.setOnTouchListener(this);
		keyboard = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
		wordListEditText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/* When there is an onClickListener, for some reason when you hit enter the softkeyboard won't go away
				 I'm pretty sure this is a bug, but I'm exploiting it because I don't want the keybaord to go away unless
				 explicitly told to do so. */
				
			}
		});
		toastMessageHere("Tip: You can downswipe to add a word.", Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL);
		
		
		
		
	}
	
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.wordlist_editor_menu, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}

	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId())
	    {
		case R.id.save:
			fileUtils.writeArrayListToFile(fileName, wordList);
			toastMessage("Saved!");
			return true;
		case R.id.sort:
			toggleSort();
			item.setTitle(sortMenuText);
			return true;
		case R.id.insert_add:
			toggleInsertionMode(item);
			return true;
		case R.id.download_list:
			Dialog d = new WordListDownloaderDialog(this, this);
			d.show();
            return true;
		case R.id.clear_list:
			wordList.clear();
			wordListAdapter.notifyDataSetChanged();
			return true;
		case R.id.add_voice_recognition:
			startVoiceRecognition();
			return true;
		default:
	        return super.onOptionsItemSelected(item);
			
	    }
	}

	
	public void onCreateContextMenu(ContextMenu menu,  View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		//Gets the position of the TextView from the overall Listview, where the context menu was created
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		wordListPosition = info.position;
		
	    MenuInflater inflater;
	    inflater = getMenuInflater();
	    inflater.inflate(R.menu.wordlist_editor_context_menu, menu);
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {  
	    super.onContextItemSelected(item);
	    
	    switch (item.getItemId()) {
	    case R.id.editWord:
	    	Dialog d = new WordListDialog(this, this);
	    	d.show();
	    	break;
	    case R.id.deleteWord:
	    	wordList.remove(wordListPosition);
	    	wordListAdapter.notifyDataSetChanged();
	        break;
	    case R.id.wiki:
	    	Uri uri = Uri.parse("http://en.wikipedia.org/wiki/" + wordList.get(wordListPosition));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
	    default:
	        break;
	    }

	    return true;
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		
		if (event.getAction() == KeyEvent.ACTION_DOWN)
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
            	String enteredText = wordListEditText.getText().toString();
            	enteredText = enteredText.replaceAll("^\\s+", "").trim().toLowerCase();
            	wordListEditText.setText("");
            	
            	
            	if(isAcceptableInput(enteredText)) {
            		switch(insertionMode){
            			case ADD:
            				wordList.add(enteredText);
            				break;
            			case INSERT:
            				wordList.add(0, enteredText);
            				break;
            		}
            		wordListAdapter.notifyDataSetChanged();
            	}
            	else
            		toastMessageHere("Invalid Entry", Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
            	
            	return true;
            	
            }
		return false;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != RESULT_OK)
        {
            toastMessage("Voice Recognition returned no results.");
            return;
        }
        switch(requestCode)
        {

        case VOICE_RECOGNITION:
             ArrayList<String> results;
             results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
             StringTokenizer st = new StringTokenizer(results.get(0), " ");
             
             while (st.hasMoreTokens())
            	 wordList.add(st.nextToken());
             
             wordListAdapter.notifyDataSetChanged();
            break;
        }
    }
	
	public void startVoiceRecognition(){
		Intent intent;
		intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Specify free form input
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        
        startActivityForResult(intent,VOICE_RECOGNITION);
	}
	
	
	@Override
	public void updateWord(String s) {
		wordList.set(wordListPosition, s);
		wordListAdapter.notifyDataSetChanged();	
	}
	
	public void toggleSort() {
		switch(sortMethod) {
		case ALPHA:
			Collections.sort(wordList);
			sortMethod = Sort.REVERSE_ALPHA;
			sortMenuText = "Sort Z-A";
			break;
		case REVERSE_ALPHA:
			Collections.sort(wordList);
			Collections.reverse(wordList);
			sortMethod = Sort.ALPHA;
			sortMenuText = "Sort A-Z";
			break;
		default:
			break;
		}
		wordListAdapter.notifyDataSetChanged();
	}
	
	public void sort()
	{
		switch(sortMethod) {
		case ALPHA:
			Collections.sort(wordList);
		case REVERSE_ALPHA:
			Collections.sort(wordList);
			Collections.reverse(wordList);
		default:
			Collections.sort(wordList);
			
		}
	}
	
	public boolean isAcceptableInput(String s) {
		
		if (s == null || s.equals(""))
			return false;
		
		for(int i = 0; i < s.length(); i++)
		{
			if (!Character.isLetter(s.charAt(i)) && s.charAt(i) != ' ')
				return false;
		}
		return true;
	}
	
	public void toggleInsertionMode(MenuItem item)
	{
		switch(insertionMode){
		case ADD:
			insertionMode = InsertionMode.INSERT;
			item.setTitle("Insert");
			break;
		case INSERT:
			insertionMode = InsertionMode.ADD;
			item.setTitle("ADD");
			break;
		}
	}
	
	public void toastMessage(String s) {
		Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        toast.show();	
	}
	
	public void toastMessageHere(String s, int GravityLocation1, int GravityLocation2) {
		Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
		toast.setGravity(GravityLocation1|GravityLocation2, 0,0);
		toast.show();
	}


	@Override
	public void downloadWords(String url) {
		WordListDownloader wordListDownloader = new WordListDownloader(this, wordList, wordListAdapter);
        wordListDownloader.execute(url);
		//http://russet.wccnet.edu/~chasselb/girls_names.txt
		
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float y1 = 0, y2 = 0;
		
		switch (event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			y1 = event.getY();
			keyboard.showSoftInput(wordListEditText,0);
			break;
		case MotionEvent.ACTION_UP:
			Log.d("Action up activted", "up");
			y2 = event.getY();
			float dy = y2 - y1;
			if (dy > 100) {
				String enteredText = wordListEditText.getText().toString().replaceAll("^\\s+", "").trim().toLowerCase();
				wordListEditText.setText("");
				if(isAcceptableInput(enteredText)) {
	            	wordList.add(0, enteredText);
	            	wordListAdapter.notifyDataSetChanged();
	            	return true; 
					
				}
				else
					toastMessageHere("Invalid Entry", Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
			}
				
			break;
			
		}
		return true;
	}
	
	
	

}
