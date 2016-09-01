package com.example.raazn.momoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by raazn on 11-Jul-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";
    public static final String DATABASE_NAME = "item_database";
    private static final int DATABASE_VERSION = 1;
    //item detail
    public static final String TABLE_ITEM_DETAIL = "item_detail";
    public static final String COL1 = "item_name";
    public static final String COL2 = "sub_item1";
    public static final String COL3 = "sub_item2";
    public static final String COL4 = "sub_item3";
    public static final String COL5 = "sub_item4";
    //price detail
    public static final String TABLE_PRICE_DETAIL = "price_detail";
    public static final String NAME = "item_name";
    public static final String PRICE = "price";
    public static final String DISCOUNT = "discount";
    //temp detail
    public static final String TABLE_TEMP_DETAIL = "temp_detail";
    public static final String TID = "id";
    public static final String TEMP_NAME = "item_name";
    public static final String TEMP_QUANTITY = "quantity";
    public static final String TEMP_PRICE = "unit_price";
    public static final String TEMP_AMOUNT = "amount";
    //order detail
    public static final String TABLE_ORDER_DETAIL = "order_detail";
    public static final String OID = "id";
    public static final String ORDER_NAME = "item_name";
    public static final String ORDER_QUANTITY = "quantity";
    public static final String ORDER_PRICE = "total_price";
    public static final String ORDER_TIME = "time";

    //create item detail table
    private static final String SQL_CREATE_TABLE_ITEM_DETAIL = "CREATE TABLE " + TABLE_ITEM_DETAIL + "("
            + COL1 + " TEXT NOT NULL PRIMARY KEY, "
            + COL2 + " TEXT, "
            + COL3 + " TEXT, "
            + COL4 + " TEXT, "
            + COL5 + " TEXT "
            + ");";
    //create price detail table
    private static final String SQL_CREATE_TABLE_PRICE_DETAIL = "CREATE TABLE " + TABLE_PRICE_DETAIL + "("
            + NAME + " TEXT NOT NULL PRIMARY KEY, "
            + PRICE + " TEXT NOT NULL, "
            + DISCOUNT + " TEXT NOT NULL "

            + ");";
    //create temp detail table
    private static final String SQL_CREATE_TABLE_TEMP_DETAIL = "CREATE TABLE " + TABLE_TEMP_DETAIL + "("
            + TID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TEMP_NAME + " TEXT NOT NULL, "
            + TEMP_PRICE + " TEXT NOT NULL, "
            + TEMP_QUANTITY + " TEXT NOT NULL, "
            + TEMP_AMOUNT + " TEXT NOT NULL "
            + ");";
    //create order detail table
    private static final String SQL_CREATE_TABLE_ORDER_DETAIL = "CREATE TABLE " + TABLE_ORDER_DETAIL + "("
            + OID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ORDER_NAME + " TEXT NOT NULL, "
            + ORDER_QUANTITY + " TEXT NOT NULL, "
            + ORDER_PRICE + " TEXT NOT NULL,"
            + ORDER_TIME + " TEXT NOT NULL"
            + ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ITEM_DETAIL);
        db.execSQL(SQL_CREATE_TABLE_PRICE_DETAIL);
        db.execSQL(SQL_CREATE_TABLE_TEMP_DETAIL);
        db.execSQL(SQL_CREATE_TABLE_ORDER_DETAIL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICE_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAIL);
        // recreate the tables
        onCreate(db);

    }

    //item_detail_method
    public boolean insert_itemdetail(String name, String sub1, String sub2, String sub3, String sub4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1, name);
        cv.put(COL2, sub1);
        cv.put(COL3, sub2);
        cv.put(COL4, sub3);
        cv.put(COL5, sub4);
        long res = db.insert(TABLE_ITEM_DETAIL, null, cv);
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor get_item_detail() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_ITEM_DETAIL, null);
        return c;

    }

    public int delete_item(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEM_DETAIL, COL1 + "=?", new String[]{name});
    }

    //price_detail_method
    public boolean insert_price_detail(String name, String price, String discount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(PRICE, price);
        cv.put(DISCOUNT, discount);

        long res = db.insert(TABLE_PRICE_DETAIL, null, cv);
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor get_price_detail() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_PRICE_DETAIL, null);
        return c;

    }

    public int delete_price(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PRICE_DETAIL, NAME + "=?", new String[]{name});
    }

    public void update_price(String name, String price, String discount) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_PRICE_DETAIL + " SET " + PRICE + "=" + price + "," + DISCOUNT + "=" + discount + " WHERE " + NAME + "='" + name + "';");
    }

    public Cursor get_price_discount(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select " + PRICE + "," + DISCOUNT + " from " + TABLE_PRICE_DETAIL + " where " + NAME + "= '" + name + "'", null);
        return c;

    }

    //temp_detail_method
    public boolean insert_temp_detail(String name, String price, String quantity, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TEMP_NAME, name);
        cv.put(TEMP_PRICE, price);
        cv.put(TEMP_QUANTITY, quantity);
        cv.put(TEMP_AMOUNT, amount);
        long res = db.insert(TABLE_TEMP_DETAIL, null, cv);
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor get_temp_detail() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_TEMP_DETAIL, null);
        return c;

    }

    public int delete_temp() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TEMP_DETAIL, null, null);

    }

    public float get_total_price() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select " + TEMP_AMOUNT + " from " + TABLE_TEMP_DETAIL, null);
        float total = 0;
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            total = total + Float.parseFloat(c.getString(0));
        }
        if (c.getCount() == 0) {
            return 0;
        }
        return total;
    }

    public void update_autoinc() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + TABLE_TEMP_DETAIL + "';");

    }

    //order_detail_method
    public boolean insert_order_detail(String name, String quantity, String price, String dateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ORDER_NAME, name);
        cv.put(ORDER_QUANTITY, quantity);
        cv.put(ORDER_PRICE, price);
        cv.put(ORDER_TIME, dateTime);
        long res = db.insert(TABLE_ORDER_DETAIL, null, cv);
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor get_order_detail() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_ORDER_DETAIL, null);
        return c;

    }

    public int delete_order() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ORDER_DETAIL, null, null);

    }

    public void update_order_autoinc() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + TABLE_ORDER_DETAIL + "';");

    }

    //transaction
    public boolean insert_transaction() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select " + TEMP_NAME + "," + TEMP_QUANTITY + "," + TEMP_AMOUNT + " from " + TABLE_TEMP_DETAIL, null);
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            System.out.println(c.getString(0) + "$$$$" + c.getString(1) + "$$$$" + c.getString(2));
            result = insert_order_detail(c.getString(0), c.getString(1), c.getString(2),getDateTime() );
        }
        return result;
    }

    //delete row from temp table
    public void delete_temp_item(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TEMP_DETAIL + " WHERE " + TID + " = '" + id + "';");
    }

    //update quantity and total amount from temp table
    public void update_quantity(int id, String quantity, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TEMP_DETAIL + " SET " + TEMP_QUANTITY + "=" + quantity + "," + TEMP_AMOUNT + "=" + amount + " WHERE " + TID + "=" + id + ";");
    }

    public float order_total() {
        Cursor c = get_order_detail();
        float sum = 0;
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            sum = sum + Float.parseFloat(c.getString(3));
        }
        return sum;
    }

    public Cursor get_qty_amount(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT " + ORDER_QUANTITY + "," + ORDER_PRICE + " FROM " + TABLE_ORDER_DETAIL + " WHERE " + ORDER_NAME + "='" + name + "';", null);
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    //insert item list in price table
    public void insert_item_price(){
        Cursor o_cursor=get_item_detail();

            for(int i=0;i<o_cursor.getCount();i++){
                o_cursor.moveToPosition(i);
                for(int j=1;j<=4;j++){
                    if(!o_cursor.getString(j).equals("")){
                        try {
                            insert_price_detail(o_cursor.getString(j),"","");
                            System.out.println(o_cursor.getString(j));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


    }

}