package com.example.lanshiliang.myweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lanshiliang.myweather.model.WeatherInfo;
import com.example.lanshiliang.myweather.util.MyApplication;
import com.example.lanshiliang.myweather.util.ReJsonRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    private LinearLayout linearLayout ;
    private TextView tvCity,tvTmp,tvPm25,tvQua,tvSug,tvWd,tvWp;
    private WeatherInfo weatherInfoJson ;
    private String url;
    private String cityKey = "101240706";
    private String time ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        getParams();
        url = "http://zhwnlapi.etouch.cn/Ecalender/api/v2/weather?date="+time+"&citykey="+cityKey ;
        updateUI();
    }

    public void getParams(){
        Date date = new Date(System.currentTimeMillis());
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        time = format.format(date);
        Intent intent = getIntent();
        if ( null != intent.getStringExtra("cityKey")){
            cityKey = intent.getStringExtra("cityKey");
        }
    }

    public void setView(){
        linearLayout = (LinearLayout) findViewById(R.id.mainContent);
        tvCity = (TextView) findViewById(R.id.TvCity);
        tvTmp = (TextView) findViewById(R.id.TvTmp);
        tvPm25 = (TextView) findViewById(R.id.TvPm25);
        tvQua = (TextView) findViewById(R.id.TvQua);
        tvSug = (TextView) findViewById(R.id.TvSug);
        tvWd = (TextView) findViewById(R.id.TvWd);
        tvWp = (TextView) findViewById(R.id.TvWp);
    }

    public void updateUI(){
        RequestQueue mQueue = Volley.newRequestQueue(MyApplication.getContext());
        JsonObjectRequest jsonObjectRequest =
                new ReJsonRequest(url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        weatherInfoJson = gson.fromJson(jsonObject.toString(),WeatherInfo.class);
                        Log.d("response", jsonObject.toString());
                        tvCity.setText(weatherInfoJson.getMeta().getCity());
                        tvPm25.setText("Pm25:" + String.valueOf(weatherInfoJson.getEvn().getPm25()));
                        tvQua.setText("空气质量："+weatherInfoJson.getEvn().getQuality());
                        tvSug.setText("出门建议："+weatherInfoJson.getEvn().getSuggest());
                        tvTmp.setText(String.valueOf(weatherInfoJson.getObserve().getTemp()) + "°C");
                        tvWd.setText(weatherInfoJson.getObserve().getWd());
                        tvWp.setText(weatherInfoJson.getObserve().getWp());
                        RequestQueue nQueue = Volley.newRequestQueue(MyApplication.getContext());
                        ImageRequest imageRequest = new ImageRequest(weatherInfoJson.getForecast().get(1).getDay().getBgPic(), new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                linearLayout.setBackground(new BitmapDrawable(bitmap));
                            }
                        },0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("ingWhrError",volleyError.getMessage());
                            }
                        });
                        nQueue.add(imageRequest);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("response",volleyError.getMessage());
                    }
                });
        mQueue.add(jsonObjectRequest);

    }

}
