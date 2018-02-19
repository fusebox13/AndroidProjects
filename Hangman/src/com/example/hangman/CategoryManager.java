package com.example.hangman;


import java.util.ArrayList;
import java.util.Collections;

import enumerations.Sort;
import fileutils.FileUtils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CategoryManager extends Activity implements AdapterView.OnItemClickListener, CategoryManagerInterface {
	
	ArrayList<String> categories;
	ArrayAdapter<String> categoriesArrayAdapter;
	ListView categoryList;
	FileUtils fileUtils;
	private static final int WORD_FILE_MANAGER = 2;
	private static final String WORD_LIST_EDITOR_ACTION = "edu.dan.wordlisteditor";
	private static final String WORD_LIST_VIEWER_ACTION = "edu.dan.wordlistviewer";
	
	int listViewPosition;
	String ext = ".txt";
	Sort sortMethod = Sort.REVERSE_ALPHA;
	String sortMenuText = "Sort Z-A";
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.category_manager);
			init();
				
	}
	
	
	public void init(){
		fileUtils = new FileUtils(this);
		categories = fileUtils.getFileArrayList();
		
		categoryList = (ListView)findViewById(R.id.listView1);
		categoriesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
		
		categoryList.setAdapter(categoriesArrayAdapter);
		categoryList.setOnItemClickListener(this);
		
		categoriesArrayAdapter.notifyDataSetChanged();
		registerForContextMenu(categoryList);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category_menu, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.createCategory:
			createCategory();
			return true;
		case R.id.sortAZ:
			toggleSort();
			item.setTitle(sortMenuText);
			return true;
	    }
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TextView listItem = (TextView)view;
		
		if (!fileUtils.isEmpty(listItem.getText().toString() + ext)) {
			Intent outData = new Intent();
			outData.putExtra("category", listItem.getText().toString() + ext);
			setResult(Activity.RESULT_OK, outData); 
			finish();   	
		}
		else
			toastMessage("File is empty!");
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,  View v, ContextMenu.ContextMenuInfo menuInfo) {
	    
		super.onCreateContextMenu(menu, v, menuInfo);
	
		//Gets the position of the TextView from the overall Listview, where the context menu was created
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		listViewPosition = info.position;
		
	    MenuInflater inflater;
	    inflater = getMenuInflater();
	    inflater.inflate(R.menu.category_context_menu, menu);
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {  
	    super.onContextItemSelected(item);
	    
	    switch (item.getItemId()) {
	    case R.id.editWordList:
	    	if (!"StatesAnimalsCountriesElementsPresidentsScientistsState Capitals".contains(categories.get(listViewPosition)))
	    		openWordListEditor();
	    	else
	    		toastMessage("This category is protected");
	    	break;
	    case R.id.deleteCategory:
	    	deleteCategory();
	        break;
	    case R.id.viewCategory:
	    	viewCategory();
	    	break;
	    default:
	        break;
	    }
	    return true;
	}
	
	
	public void createCategory() {
		Dialog d = new CategoryManagerDialog(this, this);
		d.show();
	}
	
	public void toggleSort() {
		switch(sortMethod) {
		case ALPHA:
			Collections.sort(categories);
			sortMethod = Sort.REVERSE_ALPHA;
			sortMenuText = "Sort Z-A";
			break;
		case REVERSE_ALPHA:
			Collections.sort(categories);
			Collections.reverse(categories);
			sortMethod = Sort.ALPHA;
			sortMenuText = "Sort A-Z";
			break;
		default:
			break;
		}
		categoriesArrayAdapter.notifyDataSetChanged();
	}
	
	
	public void sort()
	{
		switch(sortMethod) {
		case ALPHA:
			Collections.sort(categories);
		case REVERSE_ALPHA:
			Collections.sort(categories);
			Collections.reverse(categories);
		default:
			Collections.sort(categories);
			
		}
	}
	
	
	public void openWordListEditor() {
		Intent intent = new Intent(WORD_LIST_EDITOR_ACTION);
		intent.putExtra("fileName", categories.get(listViewPosition) + ext);
	    startActivityForResult(intent, WORD_FILE_MANAGER);
	}
	
	public void viewCategory() {
		Intent intent = new Intent(WORD_LIST_VIEWER_ACTION);
		intent.putExtra("fileName", categories.get(listViewPosition) + ext);
		this.startActivity(intent);
	}
	
	
	public void addCategory(String category) {
		categories.add(category);
		categoriesArrayAdapter.notifyDataSetChanged();
		
	}
	
	
	public void deleteCategory() {
		
		//Protected Categories
		if(!"StatesAnimalsCountriesElementsPresidentsScientistsState Capitals".contains(categories.get(listViewPosition)))
		{
			fileUtils.deleteFile(categories.get(listViewPosition) + ext);
			categories.remove(listViewPosition);
			categoriesArrayAdapter.notifyDataSetChanged();
		}
		else
			toastMessage("This category is protected");
		
	}
	
	public void toastMessage(String s) {
		Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        toast.show();	
	}

}
