package net.synergyinfosys.android.myblue.receiver;

import net.synergyinfosys.android.myblue.command.LockDeviceCommand;
import net.synergyinfosys.android.myblue.util.MDMUtil;
import net.synergyinfosys.xmppclient.Constants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "PushReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "action=" + action);
        

        if ("org.androidpn.client.SHOW_NOTIFICATION".equals(action)) {
            String id = intent.getStringExtra("NOTIFICATION_ID");
            Log.d(TAG, "id=" + id);
            String notificationData = intent.getStringExtra(Constants.NOTIFICATION_MESSAGE);
            Log.d( TAG, "data=" + notificationData );
            
            if( notificationData != null && notificationData.compareTo("lock") == 0 ){
            	LockDeviceCommand lCmd = new LockDeviceCommand(true);
            	MDMUtil.INSTANCE.addCommand(lCmd);
            }else if(  notificationData != null && notificationData.compareTo("unlock") == 0  ){
            	LockDeviceCommand uCmd = new LockDeviceCommand(false);
            	MDMUtil.INSTANCE.addCommand(uCmd);
            }

            MDMUtil.INSTANCE.runAll();
            MDMUtil.INSTANCE.clearCommand();
        }
    }

}
