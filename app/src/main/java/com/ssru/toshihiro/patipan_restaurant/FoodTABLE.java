package com.ssru.toshihiro.patipan_restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Admin on 19-Oct-16.
 */
public class FoodTABLE {
    //Explicit

    private MySQLiteOpenHelper objMySQLiteOpenHelper;

    private SQLiteDatabase writeSqLiteDatabase;
    private SQLiteDatabase readSqLiteDatabase;

    public static final String FOOD_TABLE = "foodTABLE";
    public static final String COLUMN_IN_FOOD = "_id";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_SOURCE = "Source";
    public static final String COLUMN_PRICE = "Price";

    public FoodTABLE(Context context) {
        objMySQLiteOpenHelper = new MySQLiteOpenHelper(context);
        writeSqLiteDatabase = objMySQLiteOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMySQLiteOpenHelper.getReadableDatabase();
    }

    public long addNewFood(String strFood, String strSource, String strPrice) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FOOD, strFood);
        contentValues.put(COLUMN_SOURCE, strSource);
        contentValues.put(COLUMN_PRICE, strPrice);


        return readSqLiteDatabase.insert(FOOD_TABLE, null, contentValues);
    }
}
