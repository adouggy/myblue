package net.synergyinfosys.android.myblue;

import java.util.Iterator;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class HardwareActivity extends Activity {

	public static final String TAG = "HardwareActivity";

	private static TextView mTextView = null;

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
				// Add the name and address to an array adapter to show in a
				// ListView

				String msg = device.getName() + " " + device.getAddress() + " " + rssi + " db";
				mTextView.setText(mTextView.getText() + "\n" + msg);
				Log.i(TAG, msg);
				
	            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hardware);

		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

		mTextView = (TextView) findViewById(R.id.txt_hardware_info);

		StringBuilder sb = new StringBuilder();

		if (adapter != null) {
			Log.i(TAG, "本机拥有蓝牙设备");
			// 调用isEnabled()方法判断当前蓝牙设备是否可用
			if (!adapter.isEnabled()) {
				// 如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(intent);
			}
			// 得到所有已经配对的蓝牙适配器对象
			Set<?> devices = adapter.getBondedDevices();
			if (devices.size() > 0) {
				for (Iterator<?> iterator = devices.iterator(); iterator.hasNext();) {
					// 得到BluetoothDevice对象,也就是说得到配对的蓝牙适配器
					BluetoothDevice device = (BluetoothDevice) iterator.next();
					// 得到远程蓝牙设备的地址
					Log.d(TAG, device.getAddress());
					sb.append(device.getName());
					sb.append(device.getAddress());
				}
			}

			mTextView.setText(sb.toString());
		} else {
			Log.i(TAG, "没有蓝牙设备");
		}

		// 注册开始发现广播。
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hardware, menu);
		return true;
	}

}
