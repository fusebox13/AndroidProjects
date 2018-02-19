package com.example.wordfilemanager;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
    TextView textView;
    WordFileManager wordFileManager = new WordFileManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.output);

        int[] resources ={R.id.again, R.id.delete_all};
        for (int i=0; i < resources.length; i++)
        {
            Button b = (Button)findViewById(resources[i]);
            b.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
        case R.id.again:
            again();
            break;
        case R.id.delete_all:
            delete_all();
            break;
        }

    }

    private void delete_all()
    {
        String[] files = wordFileManager.getFileList();
        for (int i=0; i < files.length; i++)
        {
            wordFileManager.deleteFile(files[i]);
        }
        textView.setText("All Files Deleted");

    }

    private void again()
    {
        // Fetch from preference file and print out the last number stored
        String lastCurrent = wordFileManager.getMyLastFileName();
        textView.setText("lastCurrent="+lastCurrent + "\n");

        // Save the current number to a preference file
        int current = (int)System.currentTimeMillis() % 1000;  // generate a semi-unique number
        wordFileManager.storeMyFileName(""+current);

        Scanner scanner = wordFileManager.getScanner(R.raw.mysongs);
        PrintStream out = wordFileManager.getPrintStream(current+"_copyFromResource.txt");
        ArrayList<String> arrList = new ArrayList<String>();

        // Copying from a Raw Resource file to another file
        while (scanner.hasNextLine())
        {
            String s = scanner.nextLine();
            out.println(s);
            arrList.add(s);
        }
        scanner.close();
        out.close();

        // Dump the ArrayList to a binary file
        String fileName = current+"_savedArrayList.binary";
        wordFileManager.saveMyWords(arrList, fileName);

        // Dump out a listing of all files we have now
        String[] files = wordFileManager.getFileList();
        for (int i=0; i < files.length; i++)
        {
            textView.append(files[i]+"\n");
        }

        // Go read in the ArrayList from a binary file and print it out to the screen
        ArrayList<String> newArrList = wordFileManager.getMyWords(fileName);
        for (int i=0; i < newArrList.size(); i++)
            textView.append(newArrList.get(i) + "\n");


    }
}
