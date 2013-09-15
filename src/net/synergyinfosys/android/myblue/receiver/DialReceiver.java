package net.synergyinfosys.android.myblue.receiver;

import net.synergyinfosys.android.myblue.HomeTabActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class DialReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent arg1) {
		Intent intent = new Intent();
		ComponentName cn = new ComponentName(ctx, HomeTabActivity.class);
		intent.setComponent(cn);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity( intent );
	}

}
