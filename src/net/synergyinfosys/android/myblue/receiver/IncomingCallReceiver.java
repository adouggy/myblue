package net.synergyinfosys.android.myblue.receiver;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.synergyinfosys.android.myblue.CallRecordActivity;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.service.CallRecordService;
import net.synergyinfosys.android.myblue.service.ContactService;
import net.synergyinfosys.android.myblue.service.LockStatusService;

import com.android.internal.telephony.ITelephony;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IncomingCallReceiver extends BroadcastReceiver {
	private static final String TAG = "IncomingCallReceiver";
	TelephonyManager telMgr;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if( LockStatusService.INSTANCE.isLock().toString().contains("UNLOCK") ){
			return;
		}
		
		telMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
		switch (telMgr.getCallState()) {
		case TelephonyManager.CALL_STATE_RINGING:
			String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			Log.v(TAG, "number:" + number);
			
			ArrayList<Contact> list = ContactService.INSTANCE.getAllSecretContacts();
			for( Contact c: list ){
				if (PhoneNumberUtils.compare(number, c.getNumber())) {
					endCall();
					CallRecordService.INSTANCE.hideLatestCallRecord( number );
					CallRecordActivity.refresh();
					abortBroadcast();
					break;
				}
			}
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			break;
		case TelephonyManager.CALL_STATE_IDLE:
			break;
		}
	}
	
	private void endCall()  
    {  
        Class<TelephonyManager> c = TelephonyManager.class;           
        try  
        {  
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);  
            getITelephonyMethod.setAccessible(true);  
            ITelephony iTelephony = null;  
            Log.e(TAG, "End call.");  
            iTelephony = (ITelephony) getITelephonyMethod.invoke(telMgr, (Object[]) null);  
            iTelephony.endCall();  
        }  
        catch (Exception e)  
        {  
            Log.e(TAG, "Fail to answer ring call.", e);  
        }          
    }  

}