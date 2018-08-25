package com.example.bwcomliweixin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lenovo on 2018/8/25.
 */

public class jiajianqi extends LinearLayout {

    private TextView jia, jian;
    private EditText numEt;
    private int numl = 1;

    public jiajianqi(Context context) {
        this(context, null);
    }

    public jiajianqi(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public jiajianqi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.jia_jian_layout, this, false);
        addView(view);
        jia = findViewById(R.id.jia);
        numEt = findViewById(R.id.numET);
        jian = findViewById(R.id.jian);
        numEt.setText(numl + "");
        jia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                numl++;
                numEt.setText(numl + "");
                if (jiajianListener != null) {
                    jiajianListener.getNum(numl);
                }
            }
        });
        jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                numl--;
                if (numl<=0){
                    numl=1;
                }

                numEt.setText(numl + "");
                if (jiajianListener!=null){
                    jiajianListener.getNum(numl);
                }
            }
        });
    }


    public void setNumEt(int n){
        numEt.setText(n+ "");
        numl=Integer.parseInt(numEt.getText().toString());
    }

    private jiajianListener jiajianListener;

    public void setJiajianListener(jiajianqi.jiajianListener jiajianListener) {
        this.jiajianListener = jiajianListener;
    }

    public interface jiajianListener {
        void getNum(int numl);
    }
}
