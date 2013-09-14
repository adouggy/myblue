package net.synergyinfosys.android.myblue.listener;

import net.synergyinfosys.android.myblue.LocateActivity;
import net.synergyinfosys.android.myblue.bean.Location;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener {

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;
		StringBuffer sb = new StringBuffer(256);
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\n地址 : ");
			sb.append(location.getAddrStr());
		}
		sb.append("\n");
//		sb.append("time : ");
//		sb.append(location.getTime());
//		sb.append("\nerror code : ");
//		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlongtitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		System.out.println(sb.toString());
		
		LocateActivity.refreshCurrentLocation(sb.toString());
		
		Location loc = new Location();
		loc.setLatitude( location.getLatitude() );
		loc.setLongitude( location.getLongitude() );
		loc.setDescription( sb.toString() );
		
		LocateActivity.setCurrentLocation(loc);
	}

	@Override
	public void onReceivePoi(BDLocation poiLocation) {
		if (poiLocation == null) {
			return;
		}

		StringBuffer sb = new StringBuffer(256);
		sb.append("Poi time : ");
		sb.append(poiLocation.getTime());
		sb.append("\nerror code : ");
		sb.append(poiLocation.getLocType());
		sb.append("\nlatitude : ");
		sb.append(poiLocation.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(poiLocation.getLongitude());
		sb.append("\nradius : ");
		sb.append(poiLocation.getRadius());
		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(poiLocation.getAddrStr());
		}
		if (poiLocation.hasPoi()) {
			sb.append("\nPoi:");
			sb.append(poiLocation.getPoi());
		} else {
			sb.append("noPoi information");
		}
		System.out.println(sb.toString());
	}

}
