package com.example.bwcomliweixin.model;

import android.os.Handler;
import android.text.TextUtils;

import com.example.bwcomliweixin.bean.UserBean;
import com.example.bwcomliweixin.utils.OkHttpUtils;
import com.example.bwcomliweixin.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/8/25.
 */
//m层，解析数据
public class CartModel {
    private Handler handler=new Handler();
    public void getCart(HashMap<String,String>prams, String url, final CartCallBack cartCallBack){
        //调用okhttputils
        OkHttpUtils.getInstace().postData(prams, url, new RequestCallback() {
            @Override
            public void error(Call call, IOException msg) {
                cartCallBack.error("有错");
            }

            @Override
            public void success(Call call, Response response) {
                try {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)){
                        jiexi(string,cartCallBack);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void jiexi(String string, final CartCallBack cartCallBack) {
        final UserBean userBean = new Gson().fromJson(string, UserBean.class);
        handler.post(new Runnable() {
            @Override
            public void run() {
                cartCallBack.success(userBean);
            }
        });
    }

    public interface CartCallBack {
        void success(UserBean userBean);
        void error(String msg);
    }
}
