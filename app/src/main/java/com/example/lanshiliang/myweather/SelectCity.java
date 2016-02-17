package com.example.lanshiliang.myweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lanshiliang.myweather.model.City;
import com.example.lanshiliang.myweather.model.County;
import com.example.lanshiliang.myweather.model.Province;
import com.example.lanshiliang.myweather.model.WeatherDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanshiliang on 2016/2/17.
 */
public class SelectCity extends Activity {
    private ListView listView;
    private int key;
    private String select;
    private WeatherDB weatherDB;
    private List<String> listAdapter = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);
        System.out.println(3);
        listView = (ListView) findViewById(R.id.cityList);
        Intent intent = getIntent();
        select = intent.getStringExtra("select");
        key = intent.getIntExtra("cityKey", 0);
        System.out.println(select+" "+key);
        weatherDB = WeatherDB.getInstance(getApplicationContext());
        if (select.equals("province")){
            final List<Province> list = weatherDB.loadProvince();
            for (Province s :list){
                System.out.println(s.getProvinceName());
                listAdapter.add(s.getProvinceName());
            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(SelectCity.this,android.R.layout.simple_list_item_1,listAdapter);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i =new Intent(SelectCity.this,SelectCity.class);
                    i.putExtra("select","city");
                    i.putExtra("cityKey",list.get(position).getId());
                    startActivity(i);
                }
            });
        }else if (select.equals("city")){
            final List<City> list = weatherDB.loadCity(key);
            for (City s:list){
                listAdapter.add(s.getCityName());
            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(SelectCity.this,android.R.layout.simple_list_item_1,listAdapter);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i =new Intent(SelectCity.this,SelectCity.class);
                    i.putExtra("select","county");
                    i.putExtra("cityKey",list.get(position).getId());
                    startActivity(i);
                }
            });
        }else if (select.equals("county")){
            final List<County> list = weatherDB.loadCounty(key);
            for (County s:list){
                listAdapter.add(s.getCountyName());
            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(SelectCity.this,android.R.layout.simple_list_item_1,listAdapter);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i =new Intent(SelectCity.this,MainActivity.class);
                    i.putExtra("cityKey",list.get(position).getCityKey());
                    startActivity(i);
                }
            });
        }
    }
}
