package com.gmail.takashi316.tminchart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sasaki on 2014/08/04.
 */
public class UserInfoSqliteOpenHelper extends SQLiteOpenHelper{
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_FILE_NAME = "UserInfo.sqlite";

    public UserInfoSqliteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE UserInfo (_id INTEGER PRIMARY KEY autoincrement, " +
                " name TEXT NOT NULL, age INTEGER, sex TEXT, affiliation TEXT, " +
                " correction TEXT, fatigue TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE userinfo");
    }
}
