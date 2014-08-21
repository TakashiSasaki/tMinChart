package com.gmail.takashi316.tminchart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sasaki on 2014/08/21.
 */
public class UploadSqliteOpenHelper extends SQLiteOpenHelper{
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_FILE_NAME = "Upload.sqlite";

    public UploadSqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE UploadTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id INTEGER, recordedDateTime INTEGER, finishedDateTime INTEGER, posted TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
