package com.example.bwcomliweixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bwcomliweixin.R;
import com.example.bwcomliweixin.bean.UserBean;

import java.util.List;

/**
 * Created by lenovo on 2018/8/25.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements CheckListener {
    private CheckListener checkListener;
    private Context context;
    private List<UserBean.DataBean> cartlist;

    private CartAllCheckboxlistener allCheckboxlistener;

    public CartAdapter(Context context, List<UserBean.DataBean> cartlist) {
        this.context = context;
        this.cartlist = cartlist;
    }

    public void addPage(List<UserBean.DataBean> list) {
        if (cartlist != null) ;
        cartlist.addAll(list);
        notifyPrient();
    }

    public void setAllCheckboxlistener(CartAllCheckboxlistener allCheckboxlistener) {
        this.allCheckboxlistener = allCheckboxlistener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final UserBean.DataBean bean = cartlist.get(position);
        holder.checkBox.setChecked(bean.isSelecten());
        holder.nameTV.setText(bean.getSellerName());
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.proudXRV.setLayoutManager(manager);
         ProductAdapter productAdapter = new ProductAdapter(context, bean.getList());
        holder.proudXRV.setAdapter(productAdapter);
        productAdapter.setCheckListener(this);

        for (int i = 0; i < bean.getList().size(); i++) {
            if (!bean.getList().get(i).isSelecten()){
                holder.checkBox.setChecked(false);
                break;
            }else {
                holder.checkBox.setChecked(true);
            }

        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()){
                    bean.setSelecten(true);
                    for (int i = 0; i < bean.getList().size(); i++) {
                        bean.getList().get(i).setSelecten(true);
                    }
                }else
                    {
                    bean.setSelecten(false);
                    for (int i = 0; i < bean.getList().size(); i++) {
                        bean.getList().get(i).setSelecten(false);
                    }
                }
                notifyDataSetChanged();
                if (allCheckboxlistener!=null){
                    allCheckboxlistener.notifyAllCheckboxStatus();
                }
            }
        });

    }

    public List<UserBean.DataBean> getCartlist() {
        return cartlist;
    }

    @Override
    public int getItemCount() {
        return cartlist.size()== 0?0:cartlist.size();
    }

    @Override
    public void notifyPrient() {
        notifyDataSetChanged();
        if (allCheckboxlistener!=null){
            allCheckboxlistener.notifyAllCheckboxStatus();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView nameTV;
        private RecyclerView proudXRV;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.sellerCheckBox);
            nameTV = itemView.findViewById(R.id.sellerNamet);
            proudXRV = itemView.findViewById(R.id.presentRv);

        }
    }
}
