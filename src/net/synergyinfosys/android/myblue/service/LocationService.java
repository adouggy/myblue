package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Location;
import net.synergyinfosys.android.myblue.dao.LocationDao;

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
	
}
 