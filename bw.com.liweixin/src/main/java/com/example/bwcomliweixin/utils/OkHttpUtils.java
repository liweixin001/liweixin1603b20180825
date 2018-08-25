package com.example.bwcomliweixin.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/8/25.
 */

public class OkHttpUtils {
    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;
    public OkHttpUtils() {
        okHttpClient = new OkHttpClient.Builder().build();
    }
    public static OkHttpUtils getInstace(){
        if (okHttpUtils==null){
            synchronized (OkHttpUtils.class){
                if(okHttpUtils==null){
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }
    //get请求
    public void getData(String url, HashMap<String,String>params, final RequestCallback requestCallback){
        StringBuilder stringBuilder = new StringBuilder();
        String allUrl="";
        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
stringBuilder.append("?").append(stringStringEntry.getValue()).append("=").append(stringStringEntry.getKey()).append("&");
        }
        allUrl=url+stringBuilder.toString().substring(0,stringBuilder.length()-1);
        Request request = new Request.Builder()
                .get()
                .url(allUrl)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requestCallback.error(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                requestCallback.success(call, response);
            }
        });
    }
//post请求
    public void postData(HashMap<String, String> params, String url, final RequestCallback requestCallback){
        FormBody.Builder builder = new FormBody.Builder();
        if (params!=null&&params.size()>0){
            for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                builder.add(stringStringEntry.getKey(),stringStringEntry.getValue());
            }
        }


        Request request = new Request.Builder()
                .post(builder.build())
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (requestCallback!=null){
                    requestCallback.error(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (requestCallback!=null){
                    requestCallback.success(call, response);
                }
            }
        });
    }

}
