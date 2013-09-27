package net.synergyinfosys.android.myblue.receiver;

import net.synergyinfosys.android.myblue.helper.BluetoothHelper;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BluetoothReceiver extends BroadcastReceiver {
	
	public static final String TAG = "BluetoothReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i( TAG, "onReceive");
		String action = intent.getAction();
		if (BluetoothDevice.ACTION_FOUND.equals(action)) {
			// Get the BluetoothDevice object from the Intent
			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//			short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
			BluetoothHelper.addToNearList( device );
//			Toast.makeText(context, "找到:" + (device.getName()==null?"无名设备":device.getName()), Toast.LENGTH_SHORT).show();
		} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
        {
			Toast.makeText(context, "蓝牙搜索完毕", Toast.LENGTH_SHORT).show();
        }
	}
}
