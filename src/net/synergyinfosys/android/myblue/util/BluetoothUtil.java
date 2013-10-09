package net.synergyinfosys.android.myblue.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import net.synergyinfosys.android.myblue.asynjob.BluetoothSearchJob;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

public enum BluetoothUtil {
	INSTANCE;

	private Context mContext = null;

	BluetoothUtil() {
	}

	public void initial(
			Context ctx) {
		this.mContext = ctx;
	}
	

	public ArrayList<BluetoothDevice> getBondedBluetooth() {
		ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

		if (BluetoothAdapter.getDefaultAdapter() != null) {
			// 调用isEnabled()方法判断当前蓝牙设备是否可用
			if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
				// 如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
				// Intent intent = new
				// Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// mContext.startActivity(intent);
			}
			// 得到所有已经配对的蓝牙适配器对象
			Set<?> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
			if (devices.size() > 0) {
				for (Iterator<?> iterator = devices.iterator(); iterator.hasNext();) {
					// 得到BluetoothDevice对象,也就是说得到配对的蓝牙适配器
					BluetoothDevice device = (BluetoothDevice) iterator.next();
					list.add(device);
				}
			}
		}
		return list;
	}

	public void startSearch() {
		if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}
		
		if( BluetoothUtil.isBLE() ){
			BluetoothSearchJob j = new BluetoothSearchJob();
			j.execute(BluetoothSearchJob.TYPE_BLE);
		}else{
			BluetoothSearchJob j2 = new BluetoothSearchJob();
			j2.execute(BluetoothSearchJob.TYPE_3_0);
		}
		
	}

	public void stopSearch(){
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if ((bluetoothAdapter != null) && (bluetoothAdapter.isDiscovering()))
			bluetoothAdapter.cancelDiscovery();
	}
	
	/**
	 * seems only supported by SAMSUNG S3/Note2 under android version 4.3
	 * @param bluetoothAdapter
	 * @return
	 */
	public static boolean isBLE(){
		Method startLeDiscoveryMethod = null;
		if(startLeDiscoveryMethod == null) // find the method if necessary
		{
			@SuppressWarnings("rawtypes")
			Class classToInvestigate = null;
			try {
				classToInvestigate = Class.forName("android.bluetooth.BluetoothAdapter");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if( classToInvestigate == null )
				return false;
			
			Method[] aClassMethods = classToInvestigate.getDeclaredMethods();
			for(Method m : aClassMethods)
			{
				if(m.getName().equals("startLeDiscovery"))
				{
					startLeDiscoveryMethod = m;
					break;
				}
			}
		}
		return (startLeDiscoveryMethod==null?false:true);
	}
}
