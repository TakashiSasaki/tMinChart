package com.gmail.takashi316.tminchart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sasaki on 2014/08/04.
 */
public class ResultsSqliteOpenHelper extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 6;
    static final String DATABASE_FILE_NAME = "Results.sqlite";

    public ResultsSqliteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ResultsTable (_id INTEGER PRIMARY KEY autoincrement, " +
                " date TEXT NOT NULL, name TEXT NOT NULL, age INTEGER, sex TEXT, affiliation TEXT, " +
                " correction TEXT, fatigue TEXT, fatigueEx TEXT, care TEXT, careEx TEXT, " +
                " address TEXT," +
                "light_sensor_value REAL, accelerometer TEXT, device TEXT, dpi TEXT, tcon_chart_results TEXT, tmin_chart_results TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE ResultsTable ADD COLUMN care;");
            db.execSQL("ALTER TABLE ResultsTable ADD COLUMN careEx;");
        }//if

        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE ResultsTable ADD COLUMN address;");
        }

        if (oldVersion < 6) {
            db.execSQL("ALTER TABLE ResultsTable ADD COLUMN fatigueEx;");
        }
    }//onUpgrade
}//ResultsSqliteOpenHelper
