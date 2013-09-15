package net.synergyinfosys.android.myblue.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

public enum BluetoothUtil {
	INSTANCE;

	private Context mContext = null;
	private BluetoothAdapter mAdapter = null;

	BluetoothUtil() {

	}

	public void initial(Context ctx) {
		this.mContext = ctx;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public ArrayList<BluetoothDevice> getBondedBluetooth() {
		ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
		
		if (mAdapter != null) {
			// 调用isEnabled()方法判断当前蓝牙设备是否可用
			if (!mAdapter.isEnabled()) {
				// 如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				mContext.startActivity(intent);
			}
			// 得到所有已经配对的蓝牙适配器对象
			Set<?> devices = mAdapter.getBondedDevices();
			if (devices.size() > 0) {
				for (Iterator<?> iterator = devices.iterator(); iterator.hasNext();) {
					// 得到BluetoothDevice对象,也就是说得到配对的蓝牙适配器
					BluetoothDevice device = (BluetoothDevice) iterator.next();
					list.add( device );
				}
			}
		}
		return list;
	}
	
	public void startSearch(){
		if (!mAdapter.isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			mContext.startActivity(intent);
		}
		mAdapter.startDiscovery(); // take 12 seconds.
	}
}
