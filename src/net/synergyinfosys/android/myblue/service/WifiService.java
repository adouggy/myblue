package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;
import java.util.List;

import net.synergyinfosys.android.myblue.bean.Wifi;
import net.synergyinfosys.android.myblue.dao.WifiDao;
import net.synergyinfosys.android.myblue.util.WifiUtil;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;

public enum WifiService {
	INSTANCE;
	
	WifiService(){}
	
	public ArrayList<Wifi> getBlackList(){
		return WifiDao.getInstance().getAll();
	}
	
	public ArrayList<Wifi> getNearWifiWithoutCheck(){
		ArrayList<Wifi> nearListWithoutCheck = new ArrayList<Wifi>();
		
		WifiUtil.INSTANCE.startScan();
		List<ScanResult> nearList = WifiUtil.INSTANCE.getWifiList();
		for( ScanResult res : nearList ){
			Wifi wifi = new Wifi();
			wifi.setSsid( res.SSID );
			wifi.setBssid( res.BSSID );
			nearListWithoutCheck.add( wifi );
		}
		
		return nearListWithoutCheck;
	}
	
	public List<Wifi> getNearWifiWithCheck( ){
		ArrayList<Wifi> nearList = getNearWifiWithoutCheck();
		ArrayList<Wifi> blackList = WifiDao.getInstance().getAll();
		for( Wifi n : nearList ){
			boolean flag = false;
			for( Wifi b : blackList ){
				if( b.equals( n ) ){
					flag = true;
					break;
				}
			}
			
			if( flag ){
				n.setChecked(true);
			}
		}
		
		return nearList;
	}
	
	public long addBlackList( Wifi wifi ){
		Wifi search = WifiDao.getInstance().get( wifi.getBssid() );
		if( search == null ){
			 return WifiDao.getInstance().insert( wifi );
		}else{
			return -1;
		}
	}
	
	public int removeBlackList( long id ){
		return WifiDao.getInstance().remove(id);
	}
	
	public int removeBlackList( String bssid ){
		return WifiDao.getInstance().remove( bssid );
	}
	
	public boolean isLock(){
		ArrayList<Wifi> blackList = WifiDao.getInstance().getAll();
		WifiInfo wifi = WifiUtil.INSTANCE.getConnectedWifi();
		if( wifi == null ){
			return false;
		}
		
		for( Wifi blackWifi : blackList ){
			if( wifi.getBSSID() != null )
			if( wifi.getBSSID().compareTo( blackWifi.getBssid() ) == 0 ){
				return true;
			}
		}
		
		return false;
	}
}
 