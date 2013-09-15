package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import com.baidu.location.BDLocation;

import net.synergyinfosys.android.myblue.bean.Location;
import net.synergyinfosys.android.myblue.dao.LocationDao;
import net.synergyinfosys.android.myblue.util.LocationUtil;

public enum LocationService {
	INSTANCE;
	
	LocationService(){}
	
	public ArrayList<Location> getAllLocation(){
		return LocationDao.getInstance().getAll();
	}
	
	public int delLocation(long id){
		return LocationDao.getInstance().remove(id);
	}
	
	public long addLocation(Location loc){
		return LocationDao.getInstance().insert(loc);
	}
	
	public int updateLocation(Location loc){
		return LocationDao.getInstance().update(loc);
	}
	
	public boolean isLock(){
		BDLocation location = LocationUtil.INSTANCE.getLocation();
		if( location == null ){
			return false;
		}
		ArrayList<Location> blackList = LocationDao.getInstance().getAll();
		for( Location blackLocation : blackList ){
			if( blackLocation.near(location.getLatitude(), location.getLongitude()) ){
				return true;
			}
		}
		return false;
	}
}
 