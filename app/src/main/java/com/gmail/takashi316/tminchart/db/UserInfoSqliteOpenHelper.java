package com.gmail.takashi316.tminchart.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gmail.takashi316.tminchart.UsersTable;

import java.sql.SQLException;

/**
 * Created by sasaki on 2014/08/04.
 */
public class UserInfoSqliteOpenHelper extends SQLiteOpenHelper{
    static final int DATABASE_VERSION = 5;
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
        Log.i(UserInfoSqliteOpenHelper.class.getSimpleName(),"oldVersion="+oldVersion + ", newVersion="+newVersion);
        if(oldVersion<=2){
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
                try {
                    users_table.writeUsersTable(db);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cursor_user_info.moveToNext();
            }//while
        }
        oldVersion = 3; //now oldVersion is 3

        if(oldVersion <= 3){
            db.execSQL(UsersTable.ADD_COLUMNS_FOR_VERSION_3);
        }

        if(oldVersion == 4){
            db.execSQL(UsersTable.DROP_UsersTable);
            db.execSQL(UsersTable.CREATE_UsersTable);
        }//if

        //db.execSQL("DROP TABLE userinfo");
    }

}
