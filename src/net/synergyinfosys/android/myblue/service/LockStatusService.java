package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import android.app.Notification;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.bean.LockStatus;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import net.synergyinfosys.android.myblue.util.Constants;
import net.synergyinfosys.android.myblue.util.NotificationUtil;

public enum LockStatusService {
	INSTANCE;

	private LockStatusService() {
	}

	public LockStatus isLock() {
		if (!GestureLockStatusService.INSTANCE.isLock()) {
			return LockStatus.GESTURE_UNLOCK;
		}

		if (BluetoothService.INSTANCE.isWhiteListDeviceBonded()) {
			return LockStatus.BLUETOOTH_UNLOCK;
		}

		// if( WifiService.INSTANCE.isLock() ){
		// return LockStatus.WIFI_LOCK;
		// }
		//
		// if( LocationService.INSTANCE.isLock() ){
		// return LockStatus.LOCATION_LOCK;
		// }

		return LockStatus.GESTURE_LOCK;
	}

	/**
	 * from lock to unlock, this is a strong state change this function will be
	 * invoke periodical like per second
	 * 
	 * 
	 * @param status
	 */
	public void doSth(
			LockStatus status) {
		boolean isLock = false;
		int timeRemaining = 0;
		if (status.toString().contains("UNLOCK")) {
			// restore all SMS
			SMSService.INSTANCE.resumeSMS();

			// call record restore
			CallRecordService.INSTANCE.resumeCallRecord();
			
			// contact
			ArrayList<Contact> list = ContactDao.getInstance().getContactAll();
			for( Contact c: list )
				ContactService.INSTANCE.restoreContact(c);
			
			timeRemaining = GestureLockStatusService.INSTANCE.lockTimeRemainingInSeconds();
		} else {
			// lock is lock .. do nothing.
			isLock = true;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Msg:");
		sb.append(SMSService.INSTANCE.getNewMsgCount());
		sb.append(",");
		sb.append("Call:");
		sb.append(CallRecordService.INSTANCE.getNewCallRecordCount());
		sb.append(".");
		if( timeRemaining != 0 ){
			sb.append("(");
			sb.append(timeRemaining);
			sb.append(")");
		}
		
		Notification n = NotificationUtil.INSTANCE.makeMyBlueNotification(isLock,
				"",
				sb.toString(),
				NotificationUtil.FLAG_ONGOING_EVENT_AUTO_CANCEL);
		NotificationUtil.INSTANCE.sendNotification(Constants.NOTI_STATUS,
				n);
	}

	public void hideAll() {
		
		ArrayList<Contact> list = ContactDao.getInstance().getContactAll();
		for( Contact c: list ){
			ContactService.INSTANCE.hideContactPhoneAccount(c);
			SMSService.INSTANCE.hideSMS(c.getNumber());
			CallRecordService.INSTANCE.hideCallRecord(c.getNumber(),
					false);
		}
		
	}
}
