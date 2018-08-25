package com.example.bwcomliweixin.presenter;

import com.example.bwcomliweixin.bean.UserBean;
import com.example.bwcomliweixin.model.CartModel;
import com.example.bwcomliweixin.view.IcartView;

import java.util.HashMap;

/**
 * Created by lenovo on 2018/8/25.
 */
//p层，交互，解绑
public class CartPresenter {
    private IcartView icartView;
    private CartModel cartModel;

    public CartPresenter(IcartView icartView) {

        cartModel=new CartModel();
        bangding(icartView);
    }
//绑定
    private void bangding(IcartView icartView) {
        this.icartView = icartView;
    }

    public void grtCart(HashMap<String,String>prams,String url){
        cartModel.getCart(prams, url, new CartModel.CartCallBack() {
            @Override
            public void success(UserBean userBean) {
                icartView.success(userBean);
            }

            @Override
            public void error(String msg) {
                icartView.error(msg);
            }
        });
    }




    public void jiebang(){
        icartView=null;
    }
}
