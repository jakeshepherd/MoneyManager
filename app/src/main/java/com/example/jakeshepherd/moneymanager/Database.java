package com.example.jakeshepherd.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * TODO need to get database working properly with date
 */
public class Database extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Bills.db";
    public static final String TABLE_NAME = "Bill_Table";
    public static final String COL_1 = "BILL_NUMBER";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "DUE_DATE";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(BILL_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, AMOUNT INTEGER, DUE_DATE DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, float amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, amount);
        //contentValues.put(COL_4, String.valueOf(date));

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){return false;}
        else{return true;}
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME;
        Cursor res = db.rawQuery(query, null);
        return res;

    }

    public boolean updateData(String billNum, String name, float amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, billNum);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, amount);
        //contentValues.put(COL_4, String.valueOf(date));
        db.update(TABLE_NAME, contentValues, "BILL_NUMBER = ?", new String[] {billNum});

        return true;
    }

    /**
     * TODO on delete, bill number for previous bills do not decrease, they stay the same
     * @param id
     * @return
     */
    public Integer deleteRowData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "BILL_NUMBER = ?", new String[] {id});
    }
}
