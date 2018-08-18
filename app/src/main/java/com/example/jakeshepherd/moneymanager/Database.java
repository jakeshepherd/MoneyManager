package com.example.jakeshepherd.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO need to get database working properly with date
 */
public class Database extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "Bills.db";
    private static final String TABLE_NAME = "Bill_Table";
    private static final String COL_1 = "BILL_NUMBER";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "AMOUNT";
    private static final String COL_4 = "DUE_DATE";
    private static final String COL_5 = "SPLIT_NUMBER";
    private static final String COL_6 = "DESCRIPTION";


    Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(BILL_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, AMOUNT INTEGER, " +
                "DUE_DATE TEXT, SPLIT_NUMBER INTEGER, DESCRIPTION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    /**
     * Getters for getting billName, billAmount, billDate.
     * note: billID = 0 means the first bill (bill number 1) just like arrays.
     */
    public String getBillName(int billID){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();
        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String name = res.getString(res.getColumnIndex("NAME"));
                list.add(name);
                res.moveToNext();
            }
        }
        return list.get(billID);
    }

    public String getBillDate(int billID){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();
        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String date = res.getString(res.getColumnIndex("DUE_DATE"));
                list.add(date);
                res.moveToNext();
            }
        }
        return list.get(billID);
    }

    public String getBillAmount(int billID){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();
        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String amount = res.getString(res.getColumnIndex("AMOUNT"));
                list.add(amount);
                res.moveToNext();
            }
        }
        return list.get(billID);
    }

    public String getSplitNum(int billID){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();
        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String amount = res.getString(res.getColumnIndex("SPLIT_NUMBER"));
                list.add(amount);
                res.moveToNext();
            }
        }
        return list.get(billID);
    }

    public String getDescription(int billID){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();
        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String amount = res.getString(res.getColumnIndex("DESCRIPTION"));
                list.add(amount);
                res.moveToNext();
            }
        }
        return list.get(billID);
    }



    /**
     * Insert data into created database
     * @param name
     *      Name of person to be paid
     * @param amount
     *      Amount to be paid
     * @param date
     *      Date the payment is due
     * @return
     *      Return true if data inserted correctly
     */
    public boolean insertData(String name, float amount, String date, int splitNum, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, amount);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, splitNum);
        contentValues.put(COL_6, description);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    /**
     * Collects all data from database
     * @return
     *      Return Cursor containing data from database
     */
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }


    /**
     * Update database, depending on bill ID
     * @param billNum
     *      Bill to be updated
     * @param name
     *      New name to update
     * @param amount
     *      New amount to update
     * @param date
     *      New date to update
     * @return
     *      Return true if data updated
     */
    public boolean updateData(String billNum, String name, float amount, String date, int splitNum, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, billNum);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, amount);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, splitNum);
        contentValues.put(COL_6, description);
        db.update(TABLE_NAME, contentValues, "BILL_NUMBER = ?", new String[] {billNum});

        return true;
    }

    /**
     * TODO on delete, bill number for previous bills do not decrease, they stay the same
     * @param id
     *      Bill ID number to show which bill is to be deleted
     * @return 1 or 0 depending on result of deletion
     */
    public Integer deleteRowData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "BILL_NUMBER = ?", new String[] {id});
    }


}
