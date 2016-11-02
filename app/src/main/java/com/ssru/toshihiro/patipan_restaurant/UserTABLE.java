package com.ssru.toshihiro.patipan_restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Admin on 19-Oct-16.
 */
public class UserTABLE {

    private MySQLiteOpenHelper objMySQLiteOpenHelper;

    private SQLiteDatabase writeSqLiteDatabase;
    private SQLiteDatabase readSqLiteDatabase;

    public static final String USER_TABLE = "userTABLE";
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_NAME = "Name";


    public UserTABLE(Context context) {
        objMySQLiteOpenHelper = new MySQLiteOpenHelper(context);
        writeSqLiteDatabase = objMySQLiteOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMySQLiteOpenHelper.getReadableDatabase();
    }


    public long addNewUser(String strUser, String strPass, String strName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER, strUser);
        contentValues.put(COLUMN_PASSWORD, strPass);
        contentValues.put(COLUMN_NAME, strName);


        return readSqLiteDatabase.insert(USER_TABLE, null, contentValues);
    }
}
