package net.synergyinfosys.android.myblue.service;

import android.util.Log;
import net.synergyinfosys.android.myblue.bean.GestureAction;
import net.synergyinfosys.android.myblue.dao.GestureActionDao;

public enum GestureLockStatusService {
	INSTANCE;

	public static final String TAG = "LockStatusService";
	public static final long LOCK_THRESHOLD = 60 * 1000;

	private GestureLockStatusService() {
	}

	public void lock() {
		Log.i( TAG, "lock" );
		GestureActionDao.getInstance().insert(GestureAction.LOCK);
	}

	public void unlock() {
		Log.i( TAG, "unlock" );
		GestureActionDao.getInstance().insert(GestureAction.UNLOCK);
	}

	public boolean isLock() {
		Log.i( TAG, "isLock" );
		long curr = System.currentTimeMillis();
		long lockTime = GestureActionDao.getInstance().getLatest(GestureAction.LOCK);
		long unlockTime = GestureActionDao.getInstance().getLatest(GestureAction.UNLOCK);
		
		Log.d( TAG, "lock:" + lockTime + "/" + "unlock:" + unlockTime );

		// if no lock or unlock action, judge it as lock
		if (lockTime < 0 && unlockTime < 0) {
			return true;
		}

		if (lockTime > unlockTime) {
			return true;
		} else {
			return (curr - unlockTime) > LOCK_THRESHOLD;
		}
	}

	public int lockTimeRemainingInSeconds() {
		Log.i( TAG, "lockTimeRemainingInSeconds" );
		if (isLock()) {
			return 0;
		}

		long unlockTime = GestureActionDao.getInstance().getLatest(GestureAction.UNLOCK);
		int remainingSec = (int)( (LOCK_THRESHOLD - Math.abs(System.currentTimeMillis() - unlockTime )) / 1000 );
		return remainingSec;
	}
}
