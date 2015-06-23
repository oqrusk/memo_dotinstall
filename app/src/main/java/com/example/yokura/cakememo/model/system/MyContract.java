package com.example.yokura.cakememo.model.system;

import android.provider.BaseColumns;

/**
 * Created by YOKURA on 4/12/15.
 */
public class MyContract {

    public MyContract(){}

    public static abstract class Memos implements BaseColumns {
        public static final String TABLE_NAME = "memos";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_UPDATED = "updated";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_TITLE + " text, " +
                COLUMN_BODY + " text, " +
                COLUMN_CREATED + " datetime default current_timestamp, " +
                COLUMN_UPDATED + " datetime default current_timestamp)";

        public static final String INIT_TABLE = "" +
                "INSERT INTO " + TABLE_NAME + "(" +
                COLUMN_TITLE + ", " +
                COLUMN_BODY + ") values ('title1', 'body1')";

        public static final String DROP_TABLE = "" +
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class GNaviApis {
        public static final String URL_RESTAURANT = "http://api.gnavi.co.jp/ver2/RestSearchAPI/?keyid=f62676c2315a010802111ea842117cb1&format=json";
        public static final String URL_PREF_S = "http://api.gnavi.co.jp/ver1/PrefSearchAPI/?keyid=f62676c2315a010802111ea842117cb1&format=json";
        public static final String URL_CATE_S = "http://api.gnavi.co.jp/ver1/CategorySmallSearchAPI/?keyid=f62676c2315a010802111ea842117cb1&format=json";


        public static final String API_KEY = "f62676c2315a010802111ea842117cb1";

    }
}
