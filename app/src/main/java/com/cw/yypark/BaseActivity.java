package com.cw.yypark;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cw.yypark.util.StatusBarUtil;

public class BaseActivity extends AppCompatActivity {

    private ConstraintLayout parentConstraintLayout;//把父类activity和子类activity的view都add到这里

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_base);
        //根据状态栏颜色来决定状态栏文字用黑色还是白色
        StatusBarUtil.setStatusBarMode(this, true, R.color.colorWhite);
    }

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentConstraintLayout = new ConstraintLayout(this);
//        parentConstraintLayout.setOrientation(ConstraintLayout.VERTICAL);
        viewGroup.addView(parentConstraintLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentConstraintLayout, true);
    }

    @Override
    public void setContentView(int layoutResID) {

        LayoutInflater.from(this).inflate(layoutResID, parentConstraintLayout, true);

    }

    @Override
    public void setContentView(View view) {

        parentConstraintLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

        parentConstraintLayout.addView(view, params);

    }

}
