package com.example.calci;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_STATUS = "ACTIVE_STATUS";
    public static final String COLUMN_ID = "ID";

    public database(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "CREATE TABLE " + CUSTOMER_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT," + COLUMN_CUSTOMER_AGE + " INT," + COLUMN_ACTIVE_STATUS + " BOOLEAN)";
        sqLiteDatabase.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean add_one(customermodel customer_model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, customer_model.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customer_model.getAge());
        cv.put(COLUMN_ACTIVE_STATUS, customer_model.isActive());
        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1)
            return false;
        else
            return true;
    }
public boolean delete_one(customermodel customer_model){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+CUSTOMER_TABLE+" WHERE "+COLUMN_ID+"="+customer_model.getId();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            return true;
        }
        else
            return false;
}

    public List<customermodel> getEveryone() {
        List<customermodel> returnList = new ArrayList<>();
        String query_string = "SELECT * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery(query_string, null)) {
            if (cursor.moveToFirst()) {
                do {
                    int customerID = cursor.getInt(0);
                    String customerName = cursor.getString(1);
                    int customerAge = cursor.getInt(2);
                    boolean customerActive = cursor.getInt(3) == 1 ? true : false;

                    customermodel new_customer = new customermodel(customerID, customerName, customerAge, customerActive);

                    returnList.add(new_customer);

                } while(cursor.moveToNext());
            } else {

            }
            return returnList;
        }
    }
}