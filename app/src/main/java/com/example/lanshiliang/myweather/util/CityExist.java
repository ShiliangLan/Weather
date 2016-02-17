package com.example.lanshiliang.myweather.util;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanshiliang on 2016/2/17.
 */
public class CityExist  {
    private Context context = MyApplication.getContext();

    public CityExist(){
//        File file = new File("CityExist");
    }

    /**
     * 把选择的城市存入CityExist文件中
     * @param key
     */
    public void saveCityKey(String key){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput("CityExist",Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(key+"\r\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取已经保存好的城市列表
     */
    public List<String> loadCityKey(){
        List <String> list = new ArrayList<>();
        BufferedReader bf =null;
        try {
            bf = new BufferedReader(new InputStreamReader(context.openFileInput("CityExist")));
            String line = null;
            while((line = bf.readLine()) != null) {
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
