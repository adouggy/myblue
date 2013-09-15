package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Bluetooth;
import net.synergyinfosys.android.myblue.dao.BluetoothDao;

public enum BluetoothService {
	INSTANCE;
	
	BluetoothService(){}
	
	public long addWhiteist( Bluetooth b ){
		Bluetooth search = BluetoothDao.getInstance().get( b.getMac() );
		if( search == null ){
			 return BluetoothDao.getInstance().insert( b );
		}else{
			return -1;
		}
	}
	
	public int removeWhiteList( long id ){
		return BluetoothDao.getInstance().remove(id);
	}
	
	public ArrayList<Bluetooth> getWhiteList(){
		return BluetoothDao.getInstance().getAll();
	}
}
 