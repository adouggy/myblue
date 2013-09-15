package net.synergyinfosys.android.myblue;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class HomeTabActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		TabHost mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("first").setIndicator("关于").setContent(new Intent(this, HomeActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("second").setIndicator("密友").setContent(new Intent(this, ContactActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("third").setIndicator("通话").setContent(new Intent(this, CallRecordActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("fourth").setIndicator("短信").setContent(new Intent(this, SMSActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("sixth").setIndicator("密码").setContent(new Intent(this, EncryptActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("fifth").setIndicator("设置").setContent(new Intent(this, KeyConfigureActivity.class)));
		mTabHost.setCurrentTab(0);

	}

}
