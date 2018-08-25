package com.example.bwcomliweixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bwcomliweixin.R;
import com.example.bwcomliweixin.bean.UserBean;
import com.example.bwcomliweixin.jiajianqi;

import java.util.List;

/**
 * Created by lenovo on 2018/8/25.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    private CheckListener checkListener;
    private CartAllCheckboxlistener cartAllCheckboxlistener;
    private Context context;
    private List<UserBean.DataBean.ListBean> listBeanList;

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }

    public void setCartAllCheckboxlistener(CartAllCheckboxlistener cartAllCheckboxlistener) {
        this.cartAllCheckboxlistener = cartAllCheckboxlistener;
    }


    public ProductAdapter(Context context, List<UserBean.DataBean.ListBean> listBeanList) {
        this.context = context;
        this.listBeanList = listBeanList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view=LayoutInflater.from(context).inflate(R.layout.cart_item_layout)
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final UserBean.DataBean.ListBean bean = listBeanList.get(position);

        holder.checkBox.setChecked(bean.isSelecten());
        holder.pricrTv.setText("优惠价：￥"+bean.getBargainPrice());
        holder.titTv.setText(bean.getTitle());
        String[]imgs=bean.getImages().split("\\|");
        if (imgs!=null&&imgs.length>0){
            Glide.with(context).load(imgs[0]).into(holder.imageView);
        }
        holder.myjiajian.setNumEt(bean.getTotalnum());
        holder.myjiajian.setJiajianListener(new jiajianqi.jiajianListener() {
            @Override
            public void getNum(int numl) {
                bean.setTotalnum(numl);
                if (checkListener!=null){
                    checkListener.notifyPrient();
                }
            }
        });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.checkBox.isChecked()){
                        bean.setSelecten(true);
                    }else {
                        bean.setSelecten(false);
                    }
                    if (checkListener!=null){
                        checkListener.notifyPrient();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return listBeanList.size()==0?0:listBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private ImageView imageView;
        private  TextView pricrTv,titTv;
        private jiajianqi myjiajian;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.item_CB);
            imageView = itemView.findViewById(R.id.item_img);
            pricrTv = itemView.findViewById(R.id.item_price);
            myjiajian = itemView.findViewById(R.id.item_jiajianqi);
            titTv = itemView.findViewById(R.id.tit_name);


        }
    }
}
