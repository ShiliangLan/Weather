package com.example.lanshiliang.myweather.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.lanshiliang.myweather.R;
import com.example.lanshiliang.myweather.db.WeatherOpenHelper;
import com.example.lanshiliang.myweather.util.MyApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lanshiliang on 2016/2/15.
 */
public class WeatherDB {

    public static final String DB_NAME = "MyWeather";

    public static final int VERSION = 1;

    private static WeatherDB weatherDB;

    private SQLiteDatabase db;

    private  WeatherDB (Context context){
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static WeatherDB getInstance (Context context){
        if (weatherDB == null){
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }
    public String queryCityName(String key){
        Cursor cursor = db.rawQuery("select * from County where key = ?", new String[]{key});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("county_name"));
    }
    public void saveExistCity(String key){
        db.execSQL("insert into ExistCity (key) values(?)",new String[]{key});
    }
    public void delExistCity(String key){
        db.execSQL("delete from ExistCity where key = ?",new String[]{key});
    }
    /**
     * 加载已保存城市的列表
     * @return
     */
    public List<String> loadExistCity(){
        List <String> list = new ArrayList<String>();
        Cursor cursor = db.query("ExistCity",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(cursor.getColumnIndex("key")));
            }while(cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }

    /**
     * 从数据库中读取全国省份信息
     */
    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<>();

        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                list.add(province);
            }while(cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
    /**
     * 从数据库中读取省份中市级信息
     */
    public List<City> loadCity(int provinceId){
        List<City> list = new ArrayList<>();

        Cursor cursor = db.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(provinceId);
                list.add(city);
            }while(cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }

    /**
     * 从数据库中读取市级中县级信息
     */
    public List<County> loadCounty(int cityId){
        List<County> list = new ArrayList<>();

        Cursor cursor = db.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCityKey(cursor.getString(cursor.getColumnIndex("key")));
                county.setPinyin(cursor.getString(cursor.getColumnIndex("ping_yin")));
                county.setCityId(cityId);
                list.add(county);
            }while(cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
    private Map<String,Map<String,Map<String,String []>>> map = new HashMap<String, Map<String, Map<String, String[]>>>();
    public void saveDB(){
        //读取raw文件目录下的CityKey文件
        InputStream inputStream = MyApplication.getContext().getResources().openRawResource(R.raw.citykey);
        BufferedReader bf =new BufferedReader(new InputStreamReader(inputStream));
        String line =null;
        try {
            while ((line = bf.readLine()) != null){
                String [] ss = line.split(",");
                if(!map.containsKey(ss[0])){
                    map.put(ss[0],new HashMap<String, Map<String, String[]>>());
                }
                if (!map.get(ss[0]).containsKey(ss[1])){
                    map.get(ss[0]).put(ss[1],new HashMap<String, String[]>());
                }
                map.get(ss[0]).get(ss[1]).put(ss[2],new String[]{ss[3],ss[4]});
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            String pro = (String) iterator.next(); //省名
            db.execSQL("insert into Province (province_name) values (?)", new String[]{pro}); //插入
            Cursor cursor = db.rawQuery("select * from Province where province_name = ?", new String[]{pro});
            cursor.moveToFirst();
            int proId = cursor.getInt(cursor.getColumnIndex("id"));  //获取刚插入省名id
            Iterator cityIt = map.get(pro).keySet().iterator();
            while (cityIt.hasNext()){
                String city = (String) cityIt.next();
                db.execSQL("insert into City (city_name,province_id) values (?,?)",new String []{city,String.valueOf(proId)});
                Cursor cityCs = db.rawQuery("select * from City where city_name = ?",new String[]{city});
                cityCs.moveToFirst();
                int cityId = cityCs.getInt(cityCs.getColumnIndex("id"));
                Iterator countyIt = map.get(pro).get(city).keySet().iterator();
                while (countyIt.hasNext()){
                    String county = (String) countyIt.next();
                    String []ss = map.get(pro).get(city).get(county);
                    db.execSQL("insert into County (county_name,city_id,ping_yin,key) values (?,?,?,?)",new String []{county,String.valueOf(cityId),ss[0],ss[1]});

                }
            }
        }

    }
}
