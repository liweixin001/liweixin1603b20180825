package com.example.bwcomliweixin.view;

import com.example.bwcomliweixin.bean.UserBean;

/**
 * Created by lenovo on 2018/8/25.
 */

public interface IcartView {
    void success(UserBean userBean);
    void error(String msg);
}
