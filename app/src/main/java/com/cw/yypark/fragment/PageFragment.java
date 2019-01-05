package com.cw.yypark.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.cw.yypark.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PageFragment extends SupportMapFragment implements AMapLocationListener,LocationSource {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private MapView mMapView;
    private AMap mAmap;
    // 声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    // 声明定位回调监听器
    public AMapLocationListener mLocationListener;
    private AMapLocation mMyLocationPoint;
    // 我的位置监听器
    private LocationSource.OnLocationChangedListener mLocationChangeListener = null;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        mMapView = (MapView ) view;
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        initMap();
        return view;
    }
    private void initMap(){
        if (mAmap == null){
            mAmap = mMapView.getMap();
            initMyLocation();
        }
    }
    private void initMyLocation(){
        mAmap.setLocationSource((LocationSource) this);
        mAmap.getUiSettings().setMyLocationButtonEnabled(true);
        mAmap.setMyLocationEnabled(true);
        mAmap.getUiSettings().setLogoPosition(
                AMapOptions.LOGO_POSITION_BOTTOM_LEFT);// logo位置
        mAmap.getUiSettings().setScaleControlsEnabled(true);// 标尺开关
        mAmap.getUiSettings().setCompassEnabled(true);// 指南针开关
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        if(mLocationClient!=null){
            mLocationClient.stopLocation();//ֹͣ��λ
        }
        deactivate();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if(mLocationClient!=null){
            mLocationClient.startLocation();
        }
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mLocationClient!=null){
            mLocationClient.onDestroy();
        }
        mMapView.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();// 获取经度
                aMapLocation.getLongitude();// 获取纬度
                aMapLocation.getAccuracy();// 获取精度信息
                SimpleDateFormat df = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);// 定位时间
                aMapLocation.getAddress();// 地址，如果option中设置isNeedAddress为false，则没有此结果
                aMapLocation.getCountry();// 国家信息
                aMapLocation.getProvince();// 省信息
                aMapLocation.getCity();// 城市信息
                aMapLocation.getDistrict();// 城区信息
                aMapLocation.getCityCode();// 城市编码
                aMapLocation.getAdCode();// 地区编码
                mMyLocationPoint = aMapLocation;
                mLocationChangeListener.onLocationChanged(mMyLocationPoint);
            } else {
                // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError",
                        "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
            }
        }

    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mLocationChangeListener = onLocationChangedListener;
        if (mLocationClient == null) {
            initLocation();
        }
    }

    @Override
    public void deactivate() {
        mLocationChangeListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }
    /**
     * 初始化定位服务
     */
    private void initLocation() {
        mLocationClient = new AMapLocationClient(getActivity());
        mLocationClient.setLocationListener(this);
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        // 设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mLocationClient.startLocation();
    }
}
