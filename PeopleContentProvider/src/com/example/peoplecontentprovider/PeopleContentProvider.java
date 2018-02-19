package com.example.peoplecontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PeopleContentProvider extends ContentProvider{

    PeopleHelper ph;
    private UriMatcher uriMatcher;
    static final int GOOD_PEOPLE=1;
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase mydb = ph.getWritableDatabase();
        int count =0;
        
        switch(uriMatcher.match(uri))
        {
        case GOOD_PEOPLE:
            count =mydb.delete(Constants.PEOPLE_TABLE, selection, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Delete has a bad URI");
                
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase mydb = ph.getWritableDatabase();
        long rowID;
        switch(uriMatcher.match(uri))
        {
        case GOOD_PEOPLE:
            rowID = mydb.insertOrThrow(Constants.PEOPLE_TABLE, null, values);
            break;
        default:
            throw new IllegalArgumentException("Insert has a bad URI");
                
        }
        getContext().getContentResolver().notifyChange(uri, null);
        
        if (rowID > 0)
        {
            Uri uri_return = ContentUris.withAppendedId(Constants.PEOPLE_CONTENT_URI, rowID);
            return uri_return;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        ph = new PeopleHelper(getContext());
        SQLiteDatabase mydb = ph.getWritableDatabase();
        if (mydb == null) return false;
        
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Constants.AUTHORITY, Constants.PEOPLE_TABLE, GOOD_PEOPLE);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = ph.getReadableDatabase();
        Cursor cursor=null;
        
        switch(uriMatcher.match(uri))
        {
        case GOOD_PEOPLE:
            cursor = db.query(Constants.PEOPLE_TABLE, projection, 
                    selection, selectionArgs, null, null, sortOrder);
            break;
        default:
            throw new IllegalArgumentException("Query has a bad URI");
                
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        SQLiteDatabase mydb = ph.getWritableDatabase();
        int count = 0;
        
        switch(uriMatcher.match(uri))
        {
        case GOOD_PEOPLE:
            count = mydb.update(Constants.PEOPLE_TABLE,  values, selection, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Update has a bad URI");
                
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
