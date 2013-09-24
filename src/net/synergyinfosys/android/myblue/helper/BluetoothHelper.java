package net.synergyinfosys.android.myblue.helper;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.BluetoothActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adapter.BluetoothNearListAdapter;
import net.synergyinfosys.android.myblue.adapter.BluetoothWhiteListAdapter;
import net.synergyinfosys.android.myblue.bean.Bluetooth;
import net.synergyinfosys.android.myblue.service.BluetoothService;
import net.synergyinfosys.android.myblue.util.BluetoothUtil;
import android.app.Activity;
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
	private Button mBtnScan = null;
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

		mWhiteAdapter = new BluetoothWhiteListAdapter(this.mActivity.getApplicationContext(), BluetoothService.INSTANCE.getWhiteList());
		mListWhite.setAdapter(mWhiteAdapter);
		
//		BluetoothUtil.INSTANCE.startSearch();
	}
	
	@Override
	public View getView(){
		if( this.mView == null ){
			throw new IllegalStateException("call onCreate before this method.");
		}
		return this.mView;
	}
	
	public static void setWhiteListCount(int count){
		if( mTxtWihitelistCount!=null )
			mTxtWihitelistCount.setText("(" + count + ")");
	}
	
	public static void setNearListCount(int count){
		if( mTxtAlllistCount !=null){
			mTxtAlllistCount.setText( "(" + count + ")" );
		}
	}

	public static void addToNearList(BluetoothDevice device) {
		BluetoothNearListAdapter.addDevice(device);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bluetooth_scan:
			BluetoothNearListAdapter.clearDevice();
			BluetoothActivity.setNearListCount( 0 );
			BluetoothUtil.INSTANCE.startSearch();
			break;
		}		
	}
}
