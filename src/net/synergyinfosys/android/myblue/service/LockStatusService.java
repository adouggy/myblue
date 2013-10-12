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

	private boolean mIsLock = false;

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
	 * 当锁定状态下想要再次触发锁定操作时，调用此方法，这样轮询时会触发锁定
	 * 如新加入联系人等情况...
	 */
	public void triggerLock(){
		this.mIsLock = false;
	}

	/**
	 * from lock to unlock, this is a strong state change this function will be
	 * invoke periodical like per second
	 * 
	 * 
	 * @param status
	 */
	public void checkStatus(
			LockStatus status) {
		int timeRemaining = 0;
		if (status.toString().contains("UNLOCK")) {
			timeRemaining = GestureLockStatusService.INSTANCE.lockTimeRemainingInSeconds();
			restoreNew();
		} else {
			hideAll();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Msg:");
		sb.append(SMSService.INSTANCE.getNewMsgCount());
		sb.append(",");
		sb.append("Call:");
		sb.append(CallRecordService.INSTANCE.getNewCallRecordCount());
		sb.append(".");
		if (timeRemaining != 0) {
			sb.append("(");
			sb.append(timeRemaining);
			sb.append(")");
		}

		Notification n = NotificationUtil.INSTANCE.makeMyBlueNotification(mIsLock,
				"",
				sb.toString(),
				NotificationUtil.FLAG_ONGOING_EVENT_AUTO_CANCEL);
		NotificationUtil.INSTANCE.sendNotification(Constants.NOTI_STATUS,
				n);
	}

	/**
	 * when trigger lock->unlock switch, call this only restore unread sms, new
	 * incoming call and contact phone account
	 * 
	 */
	public void restoreNew() {
		if (mIsLock) {
			// restore all SMS
			SMSService.INSTANCE.resumeSMS(true);

			// call record restore
			CallRecordService.INSTANCE.resumeCallRecord(true);

			// contact
			ArrayList<Contact> list = ContactDao.getInstance().getContactAll();
			for (Contact c : list)
				ContactService.INSTANCE.restoreContact(c);
			
			mIsLock = false;
		}
	}

	/**
	 * when trigger delete hide contact, call this restore all sms, all incoming
	 * call and contact phone account
	 * 
	 */
	public void restoreContact(Contact c) {
		// restore all SMS
		SMSService.INSTANCE.resumeSMSPerContact(c);

		// call record restore
		CallRecordService.INSTANCE.resumeCallRecordPerContact(c);

		// contact
		ContactService.INSTANCE.restoreContact(c);
	}

	/**
	 * when trigger unlock->lock, call this hide contact, sms, callrecord
	 * 
	 */
	public void hideAll() {
		if (!mIsLock) {
			ArrayList<Contact> list = ContactDao.getInstance().getContactAll();
			for (Contact c : list) {
				ContactService.INSTANCE.hideContactPhoneAccount(c);
				SMSService.INSTANCE.hideSMS(c.getNumber());
				CallRecordService.INSTANCE.hideCallRecord(c.getNumber(),
						false);
			}

			mIsLock = true;
		}

	}
}
