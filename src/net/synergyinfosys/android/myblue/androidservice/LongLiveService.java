package net.synergyinfosys.android.myblue.androidservice;

import net.synergyinfosys.android.myblue.HomeSlideActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adao.CallRecordADao;
import net.synergyinfosys.android.myblue.adao.ContactADao;
import net.synergyinfosys.android.myblue.adao.GestureADao;
import net.synergyinfosys.android.myblue.adao.SMSADao;
import net.synergyinfosys.android.myblue.receiver.BluetoothReceiver;
import net.synergyinfosys.android.myblue.receiver.SMSAndBootReceiver;
import net.synergyinfosys.android.myblue.util.BluetoothUtil;
import net.synergyinfosys.android.myblue.util.LocationUtil;
import net.synergyinfosys.android.myblue.util.NotificationHelper;
import net.synergyinfosys.android.myblue.util.SDUtil;
import net.synergyinfosys.android.myblue.util.WifiUtil;
import android.app.Notification;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LongLiveService extends Service implements Runnable {
	public static final String TAG = "LongLiveService";

	public static final long threadInterval = 1000;
	Context mContext = null;
	Thread mThread = null;
	Notification notification = null;
	private SMSAndBootReceiver mSMSReceiver = null;
	private BluetoothReceiver mBluetoothReceiver = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		mContext = this.getApplicationContext();
		this.mThread = new Thread(this);
		notification = NotificationHelper.genNotification(this.mContext, 0, R.drawable.ic_launcher, "正在启动蓝牙锁", 0, "蓝牙锁正在运行", "来自新能聚信", HomeSlideActivity.class,
				Notification.FLAG_FOREGROUND_SERVICE);

	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		stopForeground(true);
		this.unregisterReceiver(mSMSReceiver);
		this.unregisterReceiver(mBluetoothReceiver);
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
		Log.i(TAG, "service started..");
		startForeground(Notification.FLAG_FOREGROUND_SERVICE, notification);

		mSMSReceiver = new SMSAndBootReceiver();
		IntentFilter filter = new IntentFilter(SMSAndBootReceiver.SMS_RECEIVED_ACTION);
		filter.setPriority(Integer.MAX_VALUE);
		this.registerReceiver(mSMSReceiver, filter);

		mBluetoothReceiver = new BluetoothReceiver();
		IntentFilter bluetoothFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mBluetoothReceiver, bluetoothFilter);

		initialUtil();

		if (mThread != null) {
			if (!mThread.isAlive()) {
				mThread.start();
			}
		}
	}

	private void doSth() {
		// if( HomeActivity.mLockStatusHandler!=null )
		// HomeActivity.mLockStatusHandler.sendEmptyMessage(0);
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
					Thread.sleep(sleepingTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void initialUtil() {
		// MDMUtil.INSTANCE.initial(this.getApplicationContext());
		LocationUtil.INSTANCE.initial(this.getApplicationContext());
		WifiUtil.INSTANCE.initial(this.getApplicationContext());
		BluetoothUtil.INSTANCE.initial(this.getApplicationContext());
		SDUtil.INSTANCE.initial();
		CallRecordADao.INSTANCE.initial(this.getApplicationContext());
		SMSADao.INSTANCE.initial(this.getApplicationContext());
		ContactADao.INSTANCE.initial(this.getApplicationContext());
		GestureADao.INSTACE.initial();
		// ServiceManager sm = new ServiceManager(this.getApplicationContext());
		// sm.startService();
	}
}
