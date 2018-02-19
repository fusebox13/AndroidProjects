package com.example.hangman;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import fileutils.FileUtils;

public class WordListDialog extends Dialog implements OnClickListener
{
	EditText e;
	FileUtils f;
	Context context;
	WordListInterface wordListInterface;


public WordListDialog(Context context, WordListInterface wordListInterface) {
  super(context);
  this.context = context;
  f = new FileUtils(context);
  this.wordListInterface = wordListInterface;
}

@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);

  setTitle("Edit Word");
  setContentView(R.layout.wordlist_editor_dialog);
  
  Button b = (Button)this.findViewById(R.id.wordListDialogButton);
  b.setOnClickListener(this);
  e = (EditText)this.findViewById(R.id.wordListDialogEditText);

 
}
public void onClick(View v) {
   int index = v.getId();
   switch(index)
   {
   case R.id.wordListDialogButton:
	   wordListInterface.updateWord(e.getText()
			   						.toString()
			   						.replaceAll("[^A-Za-z\\s]", "")
			   						.replaceAll("^\\s+", "")
			   						.toLowerCase()
			   						.trim());
	   break;
   }
   dismiss();

}


}
