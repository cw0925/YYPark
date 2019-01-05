package com.cw.yypark.my;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cw.yypark.BaseActivity;
import com.cw.yypark.R;
import com.cw.yypark.home.HomeActivity;
import com.cw.yypark.navigation.CustomToolBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.arch.lifecycle.Transformations.map;

public class MyActivity extends BaseActivity {

    ArrayList<HashMap<String, Object>> listItem;
    HashMap<String, Object> map ;
    SimpleAdapter listItemAdapter;
    ListView list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //导航栏
        CustomToolBar mSimpleToolbar = findViewById(R.id.simple_toolbar);
        mSimpleToolbar.setMainTitle("个人中心");
        mSimpleToolbar.mTxtRightTitle.setVisibility(View.GONE);
        mSimpleToolbar.setLeftTitleDrawable(R.drawable.leftback);
        mSimpleToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.this.finish();
            }
        });
        //列表
        list = (ListView) findViewById(R.id.listView_my);
        // 生成动态数组，加入数据
        listItem = new ArrayList<HashMap<String, Object>>();
        String[] a={"","我的资料","支付中心","我的订单"};
        String[] b={"","我的车辆","我的车位"};

        // 小标题前面的图标
        int[] Image_a  = {R.drawable.my_info,R.drawable.my_pay,R.drawable.my_order};
        int[] Image_b  = {R.drawable.my_car,R.drawable.my_park};

        map(a,Image_a);
        map(b,Image_b);

        listItemAdapter = new SimpleAdapter(this, listItem,// 数据源
                R.layout.my_list_item,// ListItem的XML实现
                // 动态数组与ImageItem对应的子项
                new String[] {"ItemTitle", "ItemImage", "xiaoItemTitle" },
                // ImageItem的XML文件里面的一个ImageView,TextView ID
                new int[] { R.id.ItemTitle,R.id.ItemImage, R.id.xiaoItemTitle });
        //表头
        LayoutInflater headerInflater = LayoutInflater.from(MyActivity.this);
        View headerView = headerInflater.inflate(R.layout.mylist_topview, null);
        list.addHeaderView(headerView);
        //表尾
        LayoutInflater footerInflater = LayoutInflater.from(MyActivity.this);
        View footerView = footerInflater.inflate(R.layout.mylist_bottomview, null);
        list.addFooterView(footerView);
        // 添加并且显示
        list.setAdapter(listItemAdapter);
        // 添加菜单点击
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                for (int i = 0; i < listItem.size(); i++) {

                    if (arg2 == i) {

                        Toast.makeText(getApplicationContext(),
                                listItem.get(arg2).get("xiaoItemTitle") + "    " + arg2, Toast.LENGTH_SHORT)
                                .show();
                    }

                }
            }
        });
    }

    public void map(String[] a,int[] Image){

        for(int i=0;i<a.length;i++){

            map = new HashMap<String, Object>();
            for(int j=0;j<Image.length;j++){


                if(i==0){

                    map.put("ItemTitle", a[0]);

                }else if(i==(j+1)){
                    map.put("ItemImage", Image[j]);// 图像资源的ID
                    map.put("xiaoItemTitle", a[i]);
                }
            }
            listItem.add(map);
        }



    }

}
