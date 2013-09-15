package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import net.synergyinfosys.android.myblue.bean.Bluetooth;
import net.synergyinfosys.android.myblue.dao.BluetoothDao;
import net.synergyinfosys.android.myblue.util.BluetoothUtil;

public enum BluetoothService {
	INSTANCE;
	
	public static final String TAG = "BluetoothService";
	
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
	
	public boolean isWhiteListDeviceBonded(){
		ArrayList<Bluetooth> whiteList = BluetoothDao.getInstance().getAll();
		ArrayList<BluetoothDevice> bondedList = BluetoothUtil.INSTANCE.getBondedBluetooth();
		
		for( Bluetooth whiteDevice : whiteList ){
			for( BluetoothDevice bondedDevice: bondedList ){
				if( whiteDevice.getMac().compareTo( bondedDevice.getAddress() ) == 0 ){
					Log.d( TAG, "Bonded bluetooth in white list:" + bondedDevice.getAddress() );
					return true;
				}
			}
		}
		
		return false;
	}
}
 