package com.example.yokura.cakememo.controller.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.yokura.cakememo.model.system.DbHelper;
import com.example.yokura.cakememo.model.system.MyContract;

/**
 * Created by YOKURA on 4/12/15.
 */
public class MyContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.yokura.cakememo.mycontentprovider";
    private DbHelper dbHelper;
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + MyContract.Memos.TABLE_NAME);

    public static final int MEMOS = 1;
    public static final int MEMO_ITEM = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, MyContract.Memos.TABLE_NAME, MEMOS);
        uriMatcher.addURI(AUTHORITY, MyContract.Memos.TABLE_NAME + "/#", MEMO_ITEM);
    }

    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)){
            case MEMO_ITEM:
            case MEMOS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                MyContract.Memos.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        if (uriMatcher.match(uri) != MEMOS) {
            throw new IllegalArgumentException("Unknown URI:" + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newId = db.insert(MyContract.Memos.TABLE_NAME,
                null,
                contentValues);

        Uri newUri = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, newId);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        if (uriMatcher.match(uri) != MEMO_ITEM) {
            throw new IllegalArgumentException("Unknown URI:" + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete(MyContract.Memos.TABLE_NAME,
                selection,
                selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return  count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) != MEMO_ITEM) {
            throw new IllegalArgumentException("Unknown URI:" + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.update(MyContract.Memos.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return  count;
    }
}
