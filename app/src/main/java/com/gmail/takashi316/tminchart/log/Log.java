package com.gmail.takashi316.tminchart.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by sasaki on 2015/11/25.
 */
public class Log {
    private static SQLiteDatabase sqliteDatabase;

    public Log(Context context) {
        if (sqliteDatabase == null) {
            LogSqliteOpenHelper log_sqlite_open_helper = new LogSqliteOpenHelper(context);
            this.sqliteDatabase = log_sqlite_open_helper.getWritableDatabase();
        }
    }

    private static String toJson(Object object) {
        ObjectMapper object_mapper = new ObjectMapper();
        try {
            String json_string = object_mapper.writeValueAsString(object);
            return json_string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void log(Object object) {
        ContentValues content_values = new ContentValues();
        final String json_string = toJson(object);
        content_values.put("json", json_string);
        content_values.put("currentTimeMills", System.currentTimeMillis());
        content_values.put("nanoTime", System.nanoTime());
        sqliteDatabase.insert("Log", null, content_values);
    }
}
