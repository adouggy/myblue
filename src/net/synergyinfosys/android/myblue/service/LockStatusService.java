package net.synergyinfosys.android.myblue.service;

import net.synergyinfosys.android.myblue.CallRecordActivity;
import net.synergyinfosys.android.myblue.EncryptActivity;
import net.synergyinfosys.android.myblue.SMSActivity;
import net.synergyinfosys.android.myblue.bean.LockStatus;

public enum LockStatusService {
	INSTANCE;
	
	private LockStatusService() {
	}
	
	public LockStatus isLock(){
		if( !GestureLockStatusService.INSTANCE.isLock() ){
			return LockStatus.GESTURE_UNLOCK;
		}
			
		if( BluetoothService.INSTANCE.isWhiteListDeviceBonded() ){
			return LockStatus.BLUETOOTH_UNLOCK;
		}
		
		if( WifiService.INSTANCE.isLock() ){
			return LockStatus.WIFI_LOCK;
		}
		
		if( LocationService.INSTANCE.isLock() ){
			return LockStatus.LOCATION_LOCK;
		}
		
		return LockStatus.GESTURE_LOCK;
	}
	
	/**
	 * from lock to unlock, this is a strong state change
	 * @param status
	 -- 我去！！
	 */
	public void doSth( LockStatus status ){
		if( status.toString().contains("UNLOCK") ){
			//return all SMS
			SMSService.INSTANCE.resumeSMS();
			SMSActivity.refresh();
			
			CallRecordService.INSTANCE.resumeCallRecord();
			CallRecordActivity.refresh();
			
			EncryptActivity.refreshEncrypt();
		}else{
			// lock is lock .. do nothing.
		}
	}
}
