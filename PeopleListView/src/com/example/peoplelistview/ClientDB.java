package com.example.peoplelistview;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class ClientDB {

    Context context;

    ClientDB(Context context)
    {
        this.context = context;
    }
    
    Cursor getPeople(String[] columns)
    {
        Cursor cursor = null;
        try
        {
            ContentResolver contentResolver = context.getContentResolver();
            cursor = contentResolver.query(Constants.PEOPLE_CONTENT_URI, columns, null, null,  Constants.NAME);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Query error: "+ e);
        }
        
        return cursor;
    }
    
    int delete (String name)
    {
        int num = 0;

        try
        {
            ContentResolver contentResolver = context.getContentResolver();
            String where_clause = Constants.NAME + "='" + name + "'";
            num = contentResolver.delete(Constants.PEOPLE_CONTENT_URI, where_clause, null);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Delete error: "+ e);
        }
        return num;
    }
    int deleteById (int identifier)
    {
        int num = 0;
        try
        {
            ContentResolver contentResolver = context.getContentResolver();
            String where_clause = Constants._ID + "=" + identifier ;
            num = contentResolver.delete(Constants.PEOPLE_CONTENT_URI, where_clause, null);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Delete error: "+ e);
        }
        return num;
    }
    void insert(String name, int age, double height)
    {
        try
        {
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(Constants.NAME, name);
            values.put(Constants.AGE, age);
            values.put(Constants.HEIGHT, height);
    
            contentResolver.insert(Constants.PEOPLE_CONTENT_URI,  values);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Insert error: "+ e);
        }
        
    }

}