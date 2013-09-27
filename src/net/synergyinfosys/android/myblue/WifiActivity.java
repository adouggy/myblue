package net.synergyinfosys.android.myblue;

import java.util.ArrayList;
import java.util.List;

import net.synergyinfosys.android.myblue.adapter.WifiBlackListAdapter;
import net.synergyinfosys.android.myblue.adapter.WifiNearListAdapter;
import net.synergyinfosys.android.myblue.bean.Wifi;
import net.synergyinfosys.android.myblue.service.WifiService;
import net.synergyinfosys.android.myblue.util.WifiUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@Deprecated
public class WifiActivity extends Activity implements OnClickListener {

	private ListView mList = null;
	private ListView mBlackList = null;
	private static WifiNearListAdapter mWifiNearListAdapter = null;
	private static WifiBlackListAdapter mWifiBlackListAdapter = null;
	private Button mBtnScan = null;

	private static TextView mTxtBlacklistCount = null;
	private static TextView mTxtNearlistCount = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wifi);

		mList = (ListView) findViewById(R.id.list_wifi_all);
		mWifiNearListAdapter = new WifiNearListAdapter(this.getApplicationContext(), WifiService.INSTANCE.getNearWifiWithCheck());
		mList.setAdapter(mWifiNearListAdapter);

		mBlackList = (ListView) findViewById(R.id.list_wifi_black);
		mWifiBlackListAdapter = new WifiBlackListAdapter(this.getApplicationContext(), WifiService.INSTANCE.getBlackList());
		mBlackList.setAdapter(mWifiBlackListAdapter);

		mBtnScan = (Button) findViewById(R.id.btn_wifi_scan);
		mBtnScan.setOnClickListener(this);

		mTxtBlacklistCount = (TextView) findViewById(R.id.txt_wifi_blacklist_count);
		mTxtNearlistCount = (TextView) findViewById(R.id.txt_wifi_nearlist_count);
		
		refresh();
	}

	public static void refresh() {
		refreshBlackList();
		refreshNearList();
	}

	public static void refreshBlackList() {
		ArrayList<Wifi> list = WifiService.INSTANCE.getBlackList();
		WifiBlackListAdapter.setData(list);
		mWifiBlackListAdapter.notifyDataSetChanged();
		mTxtBlacklistCount.setText("(" + list.size() + ")");
	}

	public static void refreshNearList() {
		List<Wifi> list = WifiService.INSTANCE.getNearWifiWithCheck();
		WifiNearListAdapter.setData(list);
		mWifiNearListAdapter.notifyDataSetChanged();
		mTxtNearlistCount.setText("(" + list.size() + ")");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wifi, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_wifi_scan:
			boolean res = WifiUtil.INSTANCE.startScan();
			if (res) {
				refresh();
			}
			break;
		}
	}

}
