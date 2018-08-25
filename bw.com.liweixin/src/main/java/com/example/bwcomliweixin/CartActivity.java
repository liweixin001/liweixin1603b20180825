package com.example.bwcomliweixin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bwcomliweixin.adapter.CartAdapter;
import com.example.bwcomliweixin.adapter.CartAllCheckboxlistener;
import com.example.bwcomliweixin.bean.UserBean;
import com.example.bwcomliweixin.presenter.CartPresenter;
import com.example.bwcomliweixin.view.IcartView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends AppCompatActivity implements IcartView, CartAllCheckboxlistener {
    private CartPresenter cartPresenter;
    private List<UserBean.DataBean> list;
    private CheckBox allCheckbox;
    private TextView allPrice;
    private XRecyclerView xRecyclerView;

    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initData();
    }

    private void initView() {
        list = new ArrayList<>();
        allCheckbox = findViewById(R.id.quanxuan);
        allPrice = findViewById(R.id.zongjia);
        xRecyclerView = findViewById(R.id.cartRv);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //点击全选，反选
        allCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allCheckbox.isChecked()) {
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setSelecten(true);
                            for (int i1 = 0; i1 < list.get(i).getList().size(); i1++) {
                                list.get(i).getList().get(i1).setSelecten(true);
                            }
                        }
                    }
                } else {
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setSelecten(false);
                            for (int i1 = 0; i1 < list.get(i).getList().size(); i1++) {
                                list.get(i).getList().get(i1).setSelecten(false);
                            }
                        }
                    }
                }
                cartAdapter.notifyDataSetChanged();
                //总价
                totalPricr();
            }
        });


    }

    private void initData() {
        HashMap<String, String> prams = new HashMap<>();
        prams.put("uil", "71");
        cartPresenter = new CartPresenter(this);
        cartPresenter.grtCart(prams, Api.GERCART);
    }

    @Override
    public void success(UserBean userBean) {
        if (userBean != null && userBean.getData() != null) {
            list = userBean.getData();
            cartAdapter = new CartAdapter(this, list);
            xRecyclerView.setAdapter(cartAdapter);
            cartAdapter.setAllCheckboxlistener(this);

        }

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void notifyAllCheckboxStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        if (cartAdapter != null) {
            for (int i = 0; i < cartAdapter.getCartlist().size(); i++) {
                stringBuilder.append(cartAdapter.getCartlist().get(i).isSelecten());
                for (int i1 = 0; i1 < cartAdapter.getCartlist().get(i).getList().size(); i1++) {
                    stringBuilder.append(cartAdapter.getCartlist().get(i).getList().get(i1).isSelecten());

                }
            }
        }
        if (stringBuilder.toString().contains("false")) {
            allCheckbox.setChecked(false);

        } else {
            allCheckbox.setChecked(true);
        }
        totalPricr();
    }

    private void totalPricr() {
        double totalPricr = 0;
        for (int i = 0; i < cartAdapter.getCartlist().size(); i++) {
            for (int i1 = 0; i1 < cartAdapter.getCartlist().get(i).getList().size(); i1++) {

                if (cartAdapter.getCartlist().get(i).getList().get(i1).isSelecten()) {
                    UserBean.DataBean.ListBean listBean = cartAdapter.getCartlist().get(i).getList().get(i1);
                    totalPricr+=listBean.getBargainPrice()*listBean.getTotalnum();
                }
            }
        }
        allPrice.setText("总价："+totalPricr);
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartPresenter.jiebang();
    }
}
