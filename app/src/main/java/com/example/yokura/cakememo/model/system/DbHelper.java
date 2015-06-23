package com.example.yokura.cakememo.model.system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yokura.cakememo.model.system.MyContract;

/**
 * Created by YOKURA on 4/12/15.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "memos.db";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MyContract.Memos.CREATE_TABLE);
        sqLiteDatabase.execSQL(MyContract.Memos.INIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(MyContract.Memos.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
