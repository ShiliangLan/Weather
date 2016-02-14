package com.example.lanshiliang.myweather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;


public class MainActivity extends Activity {
    private LinearLayout linearLayout;
    private TextView tvCity, tvTmp, tvPm25, tvQua, tvSug, tvWd, tvWp;
    private WeatherInfo weatherInfoJson;
    private String url;
    private String cityKey = "101240706";
    private String time;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawlayout);
        setView();
        getParams();
        url = "http://zhwnlapi.etouch.cn/Ecalender/api/v2/weather?date=" + time + "&citykey=" + cityKey;
        updateUI();
    }

    public void getParams() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        time = format.format(date);
        Intent intent = getIntent();
        if (null != intent.getStringExtra("cityKey")) {
            cityKey = intent.getStringExtra("cityKey");
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "No location pricoder to use", Toast.LENGTH_SHORT).show();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null){
            Log.d("北纬", String.valueOf(location.getLatitude()));
            Log.d("东经", String.valueOf(location.getLongitude()));
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
                        ImageRequest imageRequest = new ImageRequest(weatherInfoJson.getForecast().get(1).getNight().getBgPic(), new Response.Listener<Bitmap>() {
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
