package com.example.bwcomliweixin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fynn.fluidlayout.FluidLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText ed;
    private FluidLayout liu;
    private Button but;
    private Button but1;
    private List<String>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        ed = findViewById(R.id.Ed);
        liu = findViewById(R.id.liu);
        but = findViewById(R.id.but);
        but1 = findViewById(R.id.but1);
        list=new ArrayList<>();
    }

    private void initData() {
    but.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String s = ed.getText().toString();

            String liua[]={s};

            for (int i = 0; i < liua.length; i++) {
                FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(12,12,12,12);
                TextView textView = new TextView(MainActivity.this);
                textView.setText(liua[i]);
                liu.addView(textView, params);
            }
        }
    });
//    but1.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
////            liua=null;
//        }
//    });
    }

    public void tiao(View view) {
        startActivity(new Intent(MainActivity.this,CartActivity.class));
    }
}
