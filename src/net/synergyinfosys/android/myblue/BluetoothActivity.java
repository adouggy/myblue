package net.synergyinfosys.android.myblue;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.adapter.BluetoothNearListAdapter;
import net.synergyinfosys.android.myblue.adapter.BluetoothWhiteListAdapter;
import net.synergyinfosys.android.myblue.bean.Bluetooth;
import net.synergyinfosys.android.myblue.service.BluetoothService;
import net.synergyinfosys.android.myblue.util.BluetoothUtil;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@Deprecated
public class BluetoothActivity extends Activity implements OnClickListener {
	public static final String TAG = "BluetoothActivity";

	private static TextView mTxtWihitelistCount = null;
	private static TextView mTxtAlllistCount = null;
	private Button mBtnScan = null;
	private ListView mListWhite = null;
	private ListView mListAll = null;
	private static BluetoothNearListAdapter mNearAdapter = null;
	private static BluetoothWhiteListAdapter mWhiteAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bluetooth);

		mTxtWihitelistCount = (TextView) findViewById(R.id.txt_bluetooth_whitelist_count);
		mTxtAlllistCount = (TextView) findViewById(R.id.txt_bluetooth_alllist_count);
		mBtnScan = (Button) findViewById(R.id.btn_bluetooth_scan);
		mBtnScan.setOnClickListener(this);

		mListWhite = (ListView) findViewById(R.id.list_bluetooth_whitelist);
		mListAll = (ListView) findViewById(R.id.list_bluetooth_all);

		mNearAdapter = new BluetoothNearListAdapter(this.getApplicationContext());
		mListAll.setAdapter(mNearAdapter);

		mWhiteAdapter = new BluetoothWhiteListAdapter(this.getApplicationContext(), BluetoothService.INSTANCE.getWhiteList());
		mListWhite.setAdapter(mWhiteAdapter);
		
		BluetoothUtil.INSTANCE.startSearch();
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
		BluetoothNearListAdapter.addDevice(device, "");
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
