package com.example.hangman;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import fileutils.FileUtils;

public class WordListDownloaderDialog extends Dialog implements OnClickListener
{
	EditText e;
	FileUtils f;
	Context context;
	WordListDownloaderInterface wordListDownloaderInterface;


public WordListDownloaderDialog(Context context, WordListDownloaderInterface wordListDownloaderInterface) {
  super(context);
  this.context = context;
  this.wordListDownloaderInterface = wordListDownloaderInterface;
}

@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);

  setTitle("Enter URL");
  setContentView(R.layout.wordlist_downloader_dialog);
  
  Button b = (Button)this.findViewById(R.id.urlButton);
  b.setOnClickListener(this);
  e = (EditText)this.findViewById(R.id.urlEntry);

 
}
public void onClick(View v) {
   int index = v.getId();
   switch(index)
   {
   case R.id.urlButton:
	   wordListDownloaderInterface.downloadWords(e.getText().toString().replaceAll("[\\s]", "_"));
	   break;
   }
   dismiss();

}


}