package com.example.mytouchscreendrawingprogram;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

enum DIALOG_TYPE {Open, Delete};

public class MyAlertDialogHandler implements DialogInterface.OnClickListener
{
    Context context;
    String[] fileList;
    DIALOG_TYPE dialog_type;
    WordFileManager wordFileManager;
    FileOperations fileOperations;

    MyAlertDialogHandler(Context context, WordFileManager wordFileManager, String title, 
            FileOperations fileOperations, DIALOG_TYPE dialog_type)
            {
                this.context = context;
                this.dialog_type = dialog_type;
                this.wordFileManager = wordFileManager;
                this.fileOperations = fileOperations;
        
                fileList = wordFileManager.getFileList();
        
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(title);      
                alertDialog.setItems(fileList, this);
                alertDialog.show();
            }
    public void onClick(DialogInterface dialoginterface, int index) 
    {
        String fileName = fileList[index];
        switch(dialog_type)
        {
        case Delete:
            fileOperations.delete(fileName);
            break;
        case Open:
            fileOperations.open(fileName);
            break;
        }
    }

}