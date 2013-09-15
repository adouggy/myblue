package net.synergyinfosys.android.myblue;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class KeyConfigureActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		TabHost mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("first").setIndicator("手势").setContent(new Intent(this, GestureActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("second").setIndicator("蓝牙").setContent(new Intent(this, BluetoothActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("third").setIndicator("Wifi").setContent(new Intent(this, WifiActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("fourth").setIndicator("GPS").setContent(new Intent(this, LocationActivity.class)));
		mTabHost.setCurrentTab(0);
	}


}
