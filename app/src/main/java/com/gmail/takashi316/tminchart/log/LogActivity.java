package com.gmail.takashi316.tminchart.log;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gmail.takashi316.tminchart.R;

public class LogActivity extends Activity {
    Button buttonCreateDatabase;
    Button buttonWriteDatabase;
    LogSqliteOpenHelper logSqliteOpenHelper;
    SQLiteDatabase readableDatabase;
    SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_log);
        this.buttonCreateDatabase = (Button) this.findViewById(R.id.buttonCreateDatabase);
        this.buttonCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogActivity.this.logSqliteOpenHelper = new LogSqliteOpenHelper(LogActivity.this.getApplicationContext());
                LogActivity.this.readableDatabase = LogActivity.this.logSqliteOpenHelper.getReadableDatabase();
                LogActivity.this.writableDatabase = LogActivity.this.logSqliteOpenHelper.getWritableDatabase();
            }
        });

        this.buttonCreateDatabase = (Button) this.findViewById(R.id.buttonWriteDatabase);
        this.buttonWriteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues content_values = new ContentValues();
                content_values.put("json", "{'test':'{}'");
                content_values.put("nanoTime", System.nanoTime());
                LogActivity.this.writableDatabase.insert("Log", null, content_values);
            }
        });
    }

}
