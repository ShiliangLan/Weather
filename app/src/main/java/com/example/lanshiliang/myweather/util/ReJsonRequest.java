package com.example.lanshiliang.myweather.util;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by lanshiliang on 2016/2/12.
 * 改变解码方式
 */
public class ReJsonRequest extends JsonObjectRequest {


    public ReJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    /*
        对返回的json数据以utf-8解码，默认的解码方式不支持
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String str = null;
        JSONObject json = null ;
        try {
            str = new String(response.data,"utf-8");
            json = new JSONObject(str);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
    }
}
