package com.gmail.takashi316.tminchart.log;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.takashi316.tminchart.R;

public class LogActivity extends Activity {
    Button buttonCreateDatabase;
    Button buttonWriteDatabase;
    Button buttonReadDatabase;
    TextView textView;
    TableLayout tableLayout;
    LogSqliteOpenHelper logSqliteOpenHelper;
    SQLiteDatabase readableDatabase;
    SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_log);
        this.tableLayout = (TableLayout) this.findViewById(R.id.tableLayout);
        this.getActionBar().hide();
        this.buttonCreateDatabase = (Button) this.findViewById(R.id.buttonCreateDatabase);
        this.buttonWriteDatabase = (Button) this.findViewById(R.id.buttonWriteDatabase);
        this.buttonReadDatabase = (Button) this.findViewById(R.id.buttonReadDatabase);
        this.buttonCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogActivity.this.logSqliteOpenHelper = new LogSqliteOpenHelper(LogActivity.this.getApplicationContext());
                LogActivity.this.readableDatabase = LogActivity.this.logSqliteOpenHelper.getReadableDatabase();
                LogActivity.this.writableDatabase = LogActivity.this.logSqliteOpenHelper.getWritableDatabase();
            }//onClick
        });

        this.buttonWriteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogActivity.this.logSqliteOpenHelper = new LogSqliteOpenHelper(LogActivity.this.getApplicationContext());
                LogActivity.this.writableDatabase = LogActivity.this.logSqliteOpenHelper.getWritableDatabase();
                ContentValues content_values = new ContentValues();
                content_values.put("json", "{'test':'{}'}");
                content_values.put("currentTimeMills", System.currentTimeMillis());
                content_values.put("nanoTime", System.nanoTime());
                LogActivity.this.writableDatabase.insert("Log", null, content_values);
            }//onClick
        });

        this.buttonReadDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogActivity.this.tableLayout.removeAllViews();
                LogActivity.this.logSqliteOpenHelper = new LogSqliteOpenHelper(LogActivity.this.getApplicationContext());
                LogActivity.this.readableDatabase = LogActivity.this.logSqliteOpenHelper.getReadableDatabase();
                Cursor cursor = LogActivity.this.readableDatabase.query("Log", null, null, null, null, null, null);
                {
                    final TableRow table_row = new TableRow(LogActivity.this.getApplicationContext());
                    for (int i = 0; i < cursor.getColumnCount(); ++i) {
                        final TextView text_view = new TextView(LogActivity.this.getApplicationContext());
                        text_view.setText(cursor.getColumnName(i));
                        text_view.setBackgroundColor(Color.GREEN);
                        text_view.setTextColor(Color.BLACK);
                        table_row.addView(text_view);
                        final ViewGroup.MarginLayoutParams margin_layout_params = (ViewGroup.MarginLayoutParams) text_view.getLayoutParams();
                        margin_layout_params.setMargins(1, 1, 1, 1);
                        text_view.setLayoutParams(margin_layout_params);
                    }//for
                    LogActivity.this.tableLayout.addView(table_row);
                }
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    final TableRow table_row = new TableRow(LogActivity.this.getApplicationContext());
                    final TextView text_view_id = new TextView(LogActivity.this.getApplicationContext());
                    text_view_id.setTextColor(Color.BLACK);
                    text_view_id.setText(Integer.toString(cursor.getInt(0)));
                    table_row.addView(text_view_id);
                    final TextView text_view_current_time_mills = new TextView(LogActivity.this.getApplicationContext());
                    text_view_current_time_mills.setTextColor(Color.BLACK);
                    text_view_current_time_mills.setText(Integer.toString(cursor.getInt(1)));
                    table_row.addView(text_view_current_time_mills);
                    final TextView text_view_nano_time = new TextView(LogActivity.this.getApplicationContext());
                    text_view_nano_time.setText(Long.toString(cursor.getLong(2)));
                    text_view_nano_time.setTextColor(Color.BLACK);
                    table_row.addView(text_view_nano_time);
                    final TextView text_view_json = new TextView(LogActivity.this.getApplicationContext());
                    text_view_json.setText(cursor.getString(3));
                    text_view_json.setTextColor(Color.BLACK);
                    table_row.addView(text_view_json);
                    LogActivity.this.tableLayout.addView(table_row);
                    cursor.moveToNext();
                }//while
            }//onClick
        });
    }

}
