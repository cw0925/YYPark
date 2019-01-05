package com.cw.yypark.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cw.yypark.fragment.PageFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private static final String[] mTitles = {"临时停车", "代客泊车", "共享停车"};

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
