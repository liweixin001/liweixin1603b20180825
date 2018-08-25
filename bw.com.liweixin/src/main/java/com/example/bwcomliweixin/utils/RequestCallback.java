package com.example.bwcomliweixin.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/8/25.
 */

public interface RequestCallback {
void error(Call call, IOException msg);
void success(Call call, Response response);
}
