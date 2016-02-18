package com.example.lanshiliang.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lanshiliang on 2016/2/15.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {
    /**
     *Province 建表语句
      */
    public static final String CREATE_PROVINCE = "create table Province ("
            + "id integer primary key autoincrement, "
            +"province_name text)";

    public static final String CREATE_CITY = "create table City ("
            + "id integer primary key autoincrement, "
            +"city_name text, "
            +"province_id integer)";

    public static final String CREATE_COUNTY = "create table County ("
            + "id integer primary key autoincrement, "
            +"county_name text, "
            +"ping_yin text, "
            +"key text, "
            +"city_id integer)";
    /**
     * 用来保存用户已经保存的城市列表
     */
    public static final String CREATE_EXIST_CITY = "create table ExistCity ("
            +"id integer primary key autoincrement, "
            +"key text)";

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);//建立Province表
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
        db.execSQL(CREATE_EXIST_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
