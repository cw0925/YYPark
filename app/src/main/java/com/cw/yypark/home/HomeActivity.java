package com.cw.yypark.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.cw.yypark.BaseActivity;
import com.cw.yypark.R;
import com.cw.yypark.adapter.SimpleFragmentPagerAdapter;
import com.cw.yypark.my.MyActivity;
import com.cw.yypark.navigation.CustomToolBar;
import com.cw.yypark.news.NewsActivity;

public class HomeActivity extends BaseActivity {
    private CustomToolBar mSimpleToolbar;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        导航栏
        mSimpleToolbar = findViewById(R.id.simple_toolbar);

        mSimpleToolbar.setMainTitle("优优停车");

        mSimpleToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(HomeActivity.this, "点击了左侧Title", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(HomeActivity.this,MyActivity.class);
                //启动
                startActivity(intent);
            }
        });

        mSimpleToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(HomeActivity.this, "点击了右侧Title", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(HomeActivity.this,NewsActivity.class);
                //启动
                startActivity(intent);
            }
        });
//        标签栏
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

