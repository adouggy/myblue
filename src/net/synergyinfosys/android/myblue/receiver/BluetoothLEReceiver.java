package net.synergyinfosys.android.myblue.receiver;

import net.synergyinfosys.android.myblue.helper.BluetoothHelper;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ble.bluetoothle.BluetoothDeviceHidden;

public class BluetoothLEReceiver extends BroadcastReceiver {
	
	public static final String TAG = "BluetoothLEReceiver";

	public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
	{
		String str = paramAnonymousIntent.getAction();
		if ("android.bluetooth.device.action.FOUND".equals(str))
		{
			BluetoothDevice localBluetoothDevice = (BluetoothDevice)paramAnonymousIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
			short s = paramAnonymousIntent.getShortExtra("android.bluetooth.device.extra.RSSI", (short)0);
			
			/*
			 * for BLE
			 */
			if(BluetoothDeviceHidden.getDeviceType(localBluetoothDevice) == 1)
			{
//				addDevice(localBluetoothDevice, s);
				BluetoothHelper.addToNearList( localBluetoothDevice, s + "" );
				Log.i( TAG, "found..rssi=" + s );
			}
			/*
			 * for 3.0
			 */
			else{
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = paramAnonymousIntent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//				short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
				BluetoothHelper.addToNearList( device, s + ""  );
				
				Log.i(TAG, "找到:" + (device.getName()==null?"无名设备":device.getName() ));
			}
		}
		if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str))
		{
			Log.i( TAG , "discovery finished");
			BluetoothHelper.makeCorrectStatus();
		}
	}
}
