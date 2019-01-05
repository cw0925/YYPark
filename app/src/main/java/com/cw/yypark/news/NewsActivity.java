package com.cw.yypark.news;

import android.os.Bundle;
import android.view.View;

import com.cw.yypark.BaseActivity;
import com.cw.yypark.R;
import com.cw.yypark.my.MyActivity;
import com.cw.yypark.navigation.CustomToolBar;

public class NewsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //导航栏
        CustomToolBar mSimpleToolbar = findViewById(R.id.simple_toolbar);
        mSimpleToolbar.setMainTitle("消息");
        mSimpleToolbar.mTxtRightTitle.setVisibility(View.GONE);
        mSimpleToolbar.setLeftTitleDrawable(R.drawable.leftback);
        mSimpleToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsActivity.this.finish();
            }
        });
    }
}
