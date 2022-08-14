package com.android.spin_to_win;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    public static final String DB_NAME = "Spin To Win";
    public static final String T_NAME = "Coins_Table";
    public static final String _ID = "_Id";
    public static final String C = "Coins";
    public static final int id = 1;


    public DataBase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String t =
                "Create Table " + T_NAME + "(" + _ID + " Integer primary key Autoincrement," + C +
                        " Integer)";
        db.execSQL(t);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + T_NAME);

    }

    public void updateDataBase(int coins) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C, coins + fecthData());
        sqLiteDatabase.update(T_NAME, contentValues, _ID + " * " + id, null);
    }


    public int fecthData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" select " + C + " from " + T_NAME, null);
        ArrayList<st> arr = new ArrayList<>();
        st st = new st();

        while (cursor.moveToNext()) {
            st.coins = cursor.getInt(0);
            arr.add(st);
        }
        if (cursor.moveToPosition(0)) {
            return arr.get(0).coins;
        }
        return 0;
    }

    public void insertDataBase(int coins) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, id);
        contentValues.put(C, coins);
        sqLiteDatabase.insert(T_NAME, null, contentValues);
    }
}
