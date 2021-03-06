package net.synergyinfosys.android.myblue.helper;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adapter.BluetoothNearListAdapter;
import net.synergyinfosys.android.myblue.adapter.BluetoothWhiteListAdapter;
import net.synergyinfosys.android.myblue.bean.Bluetooth;
import net.synergyinfosys.android.myblue.service.BluetoothService;
import net.synergyinfosys.android.myblue.util.BluetoothUtil;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BluetoothHelper extends MyHelper implements OnClickListener{
	public static final String TAG = "BluetoothHelper";
	
	private static TextView mTxtWihitelistCount = null;
	private static TextView mTxtAlllistCount = null;
	private static Button mBtnScan = null;
	private ListView mListWhite = null;
	private ListView mListAll = null;
	private static BluetoothNearListAdapter mNearAdapter = null;
	private static BluetoothWhiteListAdapter mWhiteAdapter = null;
	
	public BluetoothHelper(Activity act){
		super( act );
	}
	
	@Override
	public void onCreate(int layoutId){
		super.onCreate( layoutId );
	}
	
	@Override
	public void onPostCreate(){
		super.onPostCreate();
		
		mTxtWihitelistCount = (TextView) mView.findViewById(R.id.txt_bluetooth_whitelist_count);
		mTxtAlllistCount = (TextView) mView.findViewById(R.id.txt_bluetooth_alllist_count);
		mBtnScan = (Button) mView.findViewById(R.id.btn_bluetooth_scan);
		mBtnScan.setOnClickListener(this);

		mListWhite = (ListView) mView.findViewById(R.id.list_bluetooth_whitelist);
		mListAll = (ListView) mView.findViewById(R.id.list_bluetooth_all);

		mNearAdapter = new BluetoothNearListAdapter( this.mActivity.getApplicationContext() );
		mListAll.setAdapter(mNearAdapter);

		ArrayList<Bluetooth> list = BluetoothService.INSTANCE.getWhiteList();
		mWhiteAdapter = new BluetoothWhiteListAdapter(this.mActivity.getApplicationContext(), list);
		mListWhite.setAdapter(mWhiteAdapter);
		
		setWhiteListCount(list.size());
		setNearListCount(0);
		
	}
	
	@Override
	public View getView(){
		if( this.mView == null ){
			throw new IllegalStateException("call onCreate before this method.");
		}
		return this.mView;
	}
	
	public static void setWhiteListCount(int count){
		if( mTxtWihitelistCount!=null ){
			mTxtWihitelistCount.setText("(" + count + ")");
			mTxtWihitelistCount.invalidate();
		}
	}
	
	public static void setNearListCount(int count){
		if( mTxtAlllistCount !=null){
			mTxtAlllistCount.setText( "(" + count + ")" );
			mTxtAlllistCount.invalidate();
		}
	}

	public static void addToNearList(BluetoothDevice device, String extraInfo) {
		BluetoothNearListAdapter.addDevice(device, extraInfo);
		if (mNearAdapter != null) {
			mNearAdapter.notifyDataSetChanged();
		}
	}

	public static void refresh() {
		ArrayList<Bluetooth> list = BluetoothService.INSTANCE.getWhiteList();
		BluetoothWhiteListAdapter.setData(list);
		setWhiteListCount( list.size() );
		if (mWhiteAdapter != null) {
			mWhiteAdapter.notifyDataSetChanged();
		}
	}
	
	private void startScanning(){
		BluetoothNearListAdapter.clearDevice();
		setNearListCount( 0 );
		BluetoothUtil.INSTANCE.startSearch();
		makeCorrectStatus();
		showScanning();
	}
	
	private void stopScanning(){
		BluetoothUtil.INSTANCE.stopSearch();
		makeCorrectStatus();
		showStopping();
	}
	
	public static void makeCorrectStatus(){
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if ((bluetoothAdapter != null) && (bluetoothAdapter.isDiscovering())){
			showScanning();
		}else{
			showStopping();
		}
	}
	
	public static void showScanning(){
		mBtnScan.setText("正在扫描...点击停止");
	}
	
	public static void showStopping(){
		mBtnScan.setText("扫描附近的蓝牙设备(蓝牙" + (BluetoothUtil.isBLE()?"BLE":"3.0") + ")");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bluetooth_scan:
			BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if ((bluetoothAdapter != null) && (bluetoothAdapter.isDiscovering())){
				stopScanning();
			}else{
				startScanning();
			}
			break;
		}		
	}
	
}
