package com.example.peoplelistview;

import android.net.Uri;

public interface Constants
{
    public static final String PEOPLE_TABLE="people";
    
    public static final String NAME="name";
    public static final String AGE="age";
    public static final String HEIGHT="height";

    public static final String _ID = "_id";
    
    public static final String AUTHORITY="edu.wccnet.clem.people";
    public static final Uri PEOPLE_CONTENT_URI = Uri.parse("content://"+ 
                AUTHORITY + "/" + PEOPLE_TABLE);

}