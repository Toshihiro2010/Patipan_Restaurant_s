package com.ssru.toshihiro.patipan_restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Admin on 19-Oct-16.
 */
public class OrderTABLE {
    private MySQLiteOpenHelper objMySQLiteOpenHelper;

    private SQLiteDatabase writeSqLiteDatabase;
    private SQLiteDatabase readSqLiteDatabase;

    public static final String ORDER_TABLE = "orderTABLE";
    public static final String COLUMN_ID_ORDER = "_id";
    public static final String COLUMN_OFFICER = "Officer";
    public static final String COLUMN_DESK = "Desk";
    public static final String COLUMN_Food = "Food";
    public static final String COLUMN_ITEM = "Item";


    public OrderTABLE(Context context) {
        objMySQLiteOpenHelper = new MySQLiteOpenHelper(context);
        writeSqLiteDatabase = objMySQLiteOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMySQLiteOpenHelper.getReadableDatabase();
    }

    public long addOrder(String strOfficer, String strDesk, String strFood, String strItem) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OFFICER, strOfficer);
        contentValues.put(COLUMN_DESK, strDesk);
        contentValues.put(COLUMN_Food, strFood);
        contentValues.put(COLUMN_ITEM, strItem);


        return readSqLiteDatabase.insert(ORDER_TABLE, null, contentValues);

    }
}
