package com.example.rawsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// SQLiteOpenHelper is very valuable for creating and opening the file 
// associated with your database.  You must override the 2 methods seen 
// below(onCreate and onUpgrade) even if you don't really don't do anything in them.

// Normally you will create your database on the very first open by creating the 
// necessary tables for your application in the onCreate method.  
// The filename is specified in the constructor.

// If you ever have a need to change the definitions of your database, increase the 
// version number to force the onUpgrade method to execute.
// Our onUpgrade could just blow away the old tables and recreate as seen in the comments.
// Alternatively, you could use SQL commands to add a new column using commands like ALTER. 

    public class MyHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "mydb.db";
        private static final int DATABASE_VERSION = 1;

        /** Create a helper object for the Events database */
        public MyHelper(Context ctx) { 
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) { 
            //db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
            //      + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIME
            //      + " INTEGER," + TITLE + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
                int newVersion) {
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            //onCreate(db);
        }
    }
