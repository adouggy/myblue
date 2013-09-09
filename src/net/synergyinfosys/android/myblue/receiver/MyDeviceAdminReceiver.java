package net.synergyinfosys.android.myblue.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver{

	private static final String LOGTAG = "MyDeviceAdminReceiver";

	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
		Log.d(LOGTAG, "MyAdmin enabled...");
	}

	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Log.d(LOGTAG, "MyAdmin disable...");
	}

}
