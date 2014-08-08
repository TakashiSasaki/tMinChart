package com.gmail.takashi316.tminchart;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.http.client.UserTokenHandler;

/**
 * Created by sasaki on 2014/08/04.
 */
public class UserInfoSqliteOpenHelper extends SQLiteOpenHelper{
    static final int DATABASE_VERSION = 3;
    static final String DATABASE_FILE_NAME = "UserInfo.sqlite";

    public UserInfoSqliteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersTable.CREATE_UsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if((oldVersion==1 || oldVersion==2) && newVersion==3){
            try {
                db.execSQL(UsersTable.DROP_UsersTable);
            } catch (SQLiteAbortException e){
            }
            db.execSQL(UsersTable.CREATE_UsersTable);
            Cursor cursor_user_info = UsersTable.getCursorUserInfo(db);
            cursor_user_info.moveToFirst();
            UsersTable users_table = new UsersTable();
            while(!cursor_user_info.isAfterLast()){
                users_table.readUserInfo(cursor_user_info);
                users_table.writeUsersTable(db);
                cursor_user_info.moveToNext();
            }//while
        }
        //db.execSQL("DROP TABLE userinfo");
    }
}
