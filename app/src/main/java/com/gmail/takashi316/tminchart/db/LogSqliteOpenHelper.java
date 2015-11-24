package com.gmail.takashi316.tminchart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sasaki on 2015/11/25.
 */
public class LogSqliteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILE_NAME = "log.sqlite";

    public LogSqliteOpenHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Log (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nanoTime LONG, json TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
