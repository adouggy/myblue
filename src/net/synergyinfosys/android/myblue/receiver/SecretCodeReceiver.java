package net.synergyinfosys.android.myblue.receiver;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.SecretCode;
import net.synergyinfosys.android.myblue.dao.SecretCodeDao;
import net.synergyinfosys.android.myblue.service.GestureLockStatusService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class SecretCodeReceiver extends BroadcastReceiver {
	
	public static final String TAG = "SecretCodeReceiver";
	public static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i( TAG, "onReceive");
		String action = intent.getAction();
		if ( SECRET_CODE_ACTION.compareTo(action) == 0 ) {
			Log.i( TAG, "Whoray..." );
			Uri uri = intent.getData();
			Log.i( TAG, "uri is:" + uri );
			Log.i( TAG, "secret code is:"  + intent.getData().getHost());
			
			ArrayList<SecretCode> list = SecretCodeDao.getInstance().getAll();
			if( list != null && list.size() > 0 ){
				String code = list.get(0).getCode();
				if( code.compareTo( intent.getData().getHost() ) == 0){
					Log.i( TAG, "bingo~ unlock it!" );
					GestureLockStatusService.INSTANCE.unlock();
				}
			}
        }
	}
}
