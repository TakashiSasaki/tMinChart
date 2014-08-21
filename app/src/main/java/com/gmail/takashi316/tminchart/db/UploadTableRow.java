package com.gmail.takashi316.tminchart.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sasaki on 2014/08/21.
 */
public class UploadTableRow {
    final static String tableName = "UploadTable";
    public Integer id;
    public Integer recordedDateTime;
    public Integer finishedDateTime;

    public static UploadTableRow getLastUploaded(SQLiteDatabase db){
        final Cursor cursor = db.query(tableName, new String[]{"id", "recordedDateTime", "finishedDateTime"}, "finishedDateTime IS NOT NULL", null, null, null, "id DESC");
        if(cursor.getCount() == 0) {
            cursor.close();
            return null;
        }
        final UploadTableRow upload_table_row = new UploadTableRow();
        final ContentValues content_values = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(cursor, content_values);
        upload_table_row.id = content_values.getAsInteger("id");
        upload_table_row.recordedDateTime = content_values.getAsInteger("recordedDateTime");
        upload_table_row.finishedDateTime = content_values.getAsInteger("finishedDateTime");
        cursor.close();
        return upload_table_row;
    }

    public void write(SQLiteDatabase db){

    }

    public void read(Cursor cursor){

    }

}
