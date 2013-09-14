package net.synergyinfosys.android.myblue.util;

import net.synergyinfosys.android.myblue.listener.MyLocationListener;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public enum LocatorUtil {
	INSTANCE;

	LocatorUtil() {
	}

	private Context mContext = null;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	public void initial(Context ctx) {
		this.mContext = ctx;
		this.onCreate();
	}

	public void onCreate() {
		mLocationClient = new LocationClient(this.mContext.getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		// option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
		
		mLocationClient.start();
	}

	public BDLocation getLocation() {
		return this.mLocationClient.getLastKnownLocation();
	}
}
