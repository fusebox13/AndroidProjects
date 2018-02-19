package com.example.peoplecontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PeopleHelper extends SQLiteOpenHelper{

    private static final String Database_filename="people.db";
    private static final int Database_version=2;

    public PeopleHelper(Context context) {
        super(context, Database_filename, null, Database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table people (name text, age integer, height real);
        // For the name, we could have also said UNIQUE instead of NOT NULL 
        String sql = "CREATE TABLE " + Constants.PEOPLE_TABLE + " (" + 
                        Constants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        Constants.NAME + " text NOT NULL, " +
                        Constants.AGE  + " integer, "+
                        Constants.HEIGHT + " real); " ;
        db.execSQL(sql);        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + Constants.PEOPLE_TABLE);
        onCreate(db);        
    }

}