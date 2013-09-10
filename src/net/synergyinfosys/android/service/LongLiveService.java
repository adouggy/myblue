package net.synergyinfosys.android.service;

import net.synergyinfosys.android.myblue.HomeActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adao.CallRecordADao;
import net.synergyinfosys.android.myblue.adao.ContactADao;
import net.synergyinfosys.android.myblue.adao.GestureADao;
import net.synergyinfosys.android.myblue.adao.SMSADao;
import net.synergyinfosys.android.myblue.receiver.SMSAndBootReceiver;
import net.synergyinfosys.android.myblue.util.NotificationHelper;
import net.synergyinfosys.android.myblue.util.SDUtil;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LongLiveService extends Service implements Runnable{
	public static final String TAG = "LongLiveService";

	public static final long threadInterval = 3000;
	Context mContext = null;
	Thread mThread = null;
	Notification notification = null;
	private SMSAndBootReceiver mSMSReceiver = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		mContext = this.getApplicationContext();
		this.mThread = new Thread(this);
		notification = NotificationHelper.genNotification(
				this.mContext, 
				0, 
				R.drawable.ic_launcher, 
				"后台安全监控正在运行...", 
				0, 
				"后台安全监控正在运行...",
				"新能聚信出品", 
				HomeActivity.class, 
				Notification.FLAG_FOREGROUND_SERVICE);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		stopForeground(true);
		this.unregisterReceiver(mSMSReceiver);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "OnStartCommand");
		handleStart();
		return START_STICKY; // for restart this service..
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Log.i(TAG, "onStart");
		handleStart();
	}

	public void handleStart() {
		Log.i( TAG, "service started.." );
		startForeground(Notification.FLAG_FOREGROUND_SERVICE, notification);
		
		mSMSReceiver = new SMSAndBootReceiver();
		IntentFilter filter = new IntentFilter(SMSAndBootReceiver.SMS_RECEIVED_ACTION);
		filter.setPriority(Integer.MAX_VALUE);
		this.registerReceiver( mSMSReceiver, filter );

		initialUtil();
		
		if( mThread != null ){
			if( !mThread.isAlive() ){
				mThread.start();
			}
		}
	}

	private void doSth() {
		Log.d( TAG , "do sth.." );
	}

	@Override
	public void run() {
		while (true) {
			long curr = System.currentTimeMillis();
			doSth();
			try {
				long interval = System.currentTimeMillis() - curr;
				long sleepingTime = (threadInterval - interval);
				if (sleepingTime > 0) {
//					Log.d(TAG, "sleeping.." + sleepingTime + " ms. elapse:" + interval + " ms.");
					Thread.sleep(sleepingTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initialUtil(){
//		MDMUtil.INSTANCE.initial(this.getApplicationContext());
		SDUtil.INSTANCE.initial();
		CallRecordADao.INSTANCE.initial(this.getApplicationContext());
		SMSADao.INSTANCE.initial(this.getApplicationContext());
		ContactADao.INSTANCE.initial(this.getApplicationContext());
		GestureADao.INSTACE.initial();
//		ServiceManager sm = new ServiceManager(this.getApplicationContext());
//		sm.startService();
	}
}
