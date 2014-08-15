package com.gmail.takashi316.tminchart;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;

import java.sql.SQLException;

/**
 * Created by sasaki on 2014/08/08.
 */
public class UsersTable {
    public String textName;
    public Integer integerAge;
    public String textSex;
    public String textAffiliation;
    public String textCorrection;
    public String textFatigue;
    public String textFatigueEx;
    public String textCare;
    public String textCareEx;
    public Long integerLastUsed;
    public Double realLongitude;
    public Double realLatitude;
    public String textAddress;
    public final static String[] ALL_COLUMN_NAMES_UserInfo
            = new String[]{"name", "age", "sex", "affiliation", "correction", "fatigue"};

    @Deprecated
    public final static String CREATE_TABLE_UserInfo = "CREATE TABLE UserInfo (_id INTEGER PRIMARY KEY autoincrement, " +
            " name TEXT NOT NULL, age INTEGER, sex TEXT, affiliation TEXT, " +
            " correction TEXT, fatigue TEXT )";

    public final static String[] ALL_COLUMN_NAMES_UsersTable
            = new String[]{"name", "age", "sex", "affiliation", "correction", "fatigue", "fatigueEx", "care", "careEx",
            "longitude", "latitude", "address", "lastUsed"};

    public final static String CREATE_UsersTable =
            "CREATE TABLE UsersTable (_id INTEGER PRIMARY KEY autoincrement, " +
                    " name TEXT NOT NULL, age INTEGER, sex TEXT, affiliation TEXT, " +
                    " correction TEXT, fatigue TEXT, fatigueEx TEXT, care TEXT, careEx TEXT, " +
                    "longitude REAL, latitude REAL, address TEXT, lastUsed INTEGER)";

    public final static String ADD_COLUMNS_FOR_VERSION_3 =
            "ALTER TABLE UsersTable ADD COLUMN fatigueEx TEXT;" +
            "ALTER TABLE UsersTable ADD COLUMN care TEXT;" +
            "ALTER TABLE UsersTable ADD COLUMN careEx TEXT;";

    public final static String DROP_UsersTable =
            "DROP TABLE UsersTable";

    @Deprecated
    public final static Cursor getCursorUserInfo(SQLiteDatabase database) {
        return database.query("UserInfo", UsersTable.ALL_COLUMN_NAMES_UserInfo, null, null, null, null, null);
    }

    public final static Cursor getCursorUsersTable(SQLiteDatabase database) {
        return database.query("UsersTable", UsersTable.ALL_COLUMN_NAMES_UsersTable, null, null, null, null, null);
    }

    public final static Cursor getCursor(SQLiteDatabase database, String name){
        return getCursorUsersTable(database, "name = ? ", new String[]{name}, null, null, null, null);
    }

    public final static Cursor getCursorUsersTable(SQLiteDatabase database, String selection,
                                                   String[] selectionArgs, String groupBy, String having,
                                                   String orderBy, String limit) {
        return database.query("UsersTable", UsersTable.ALL_COLUMN_NAMES_UsersTable, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public UsersTable() {}

    @Deprecated
    public void readUserInfo(Cursor cursor) {
        this.textName = cursor.isNull(0) ? null : cursor.getString(0);
        this.integerAge = cursor.isNull(1) ? null : cursor.getInt(1);
        this.textSex = cursor.isNull(2) ? null : cursor.getString(2);
        this.textAffiliation = cursor.isNull(3) ? null : cursor.getString(3);
        this.textCorrection = cursor.isNull(4) ? null : cursor.getString(4);
        this.textFatigue = cursor.isNull(5) ? null : cursor.getString(5);
    }//readUserInfo

    public void readUsersTable(Cursor cursor){
        ContentValues content_values = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(cursor, content_values);
        this.textName = content_values.getAsString("name");
        this.integerAge = content_values.getAsInteger("age");
        this.textSex = content_values.getAsString("sex");
        this.textAffiliation = content_values.getAsString("affiliation");
        this.textCorrection = content_values.getAsString("correction");
        this.textFatigue = content_values.getAsString("fatigue");
        this.textFatigueEx = content_values.getAsString("fatigueEx");
        this.textCare = content_values.getAsString("care");
        this.textCareEx = content_values.getAsString("careEx");
        this.integerLastUsed = content_values.getAsLong("lastUsed");
        this.realLatitude = content_values.getAsDouble("latitude");
        this.realLongitude = content_values.getAsDouble("longitude");
        this.textAddress = content_values.getAsString("address");
    }//readUsersTable

    public void writeUsersTable(SQLiteDatabase database) throws SQLException {
        ContentValues  content_values = new ContentValues();
        content_values.put("name", this.textName);
        content_values.put("age", this.integerAge);
        content_values.put("sex", this.textSex);
        content_values.put("affiliation", this.textAffiliation);
        content_values.put("correction", this.textCorrection);
        content_values.put("fatigue", this.textFatigue);
        content_values.put("fatigueEx", this.textFatigueEx);
        content_values.put("care", this.textCare);
        content_values.put("careEx", this.textCare);
        content_values.put("longitude", this.realLongitude);
        content_values.put("latitude", this.realLatitude);
        content_values.put("address", this.textAddress);
        content_values.put("lastUsed", this.integerLastUsed);
        final long row_id = database.insert("UsersTable", null, content_values);
        if(row_id < 0){
            throw new SQLException("failed to write row on UsersTable");
        }//if
    }//writeUsersTable

    public static boolean hasUniqueName(SQLiteDatabase database, String name) {
        final Cursor cursor = UsersTable.getCursor(database, name);
        cursor.moveToFirst();
        final UsersTable users_table = new UsersTable();
        users_table.readUsersTable(cursor);
        if(!name.equals(users_table.textName)) return false;
        if(cursor.getCount() != 1) return false;
        return true;
    }//hasUniqueName
}//UsersTable
