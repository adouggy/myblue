package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import android.util.Log;
import net.synergyinfosys.android.myblue.adao.SMSADao;
import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.dao.SMSDao;

public enum SMSService {
	INSTANCE;

	public static final String TAG = "SMSService";

	SMSService() {
	}

	/**
	 * 迁移所有短信
	 * 
	 * @param isHide
	 * @param number
	 */
	public void hideSMS(
			String number) {
		ArrayList<SMS> list = SMSADao.INSTANCE.getSMS(number);
		for (SMS sms : list) {
			// TODO: transction..
			SMSDao.getInstance().insertSMS(sms);
			SMSADao.INSTANCE.deleteSMS(sms.getAndroidId());
		}
	}

	public int getNewMsgCount() {
		return SMSDao.getInstance().getAllUnRead().size();
	}

	/**
	 * 有新的短信的时候，插入新的，并且把已读的短信也给导入进来
	 * 
	 * @param newSms
	 */
	public void hijackSMS(
			SMS newSms) {
		Log.i(TAG,
				"Hijacking.." + newSms);
		SMSDao.getInstance().insertSMS(newSms);

		ArrayList<SMS> list = SMSADao.INSTANCE.getSMS(newSms.getAddress());
		for (SMS s : list) {
			SMSDao.getInstance().insertSMS(s);
			SMSADao.INSTANCE.deleteSMS(s.getAndroidId());
		}
	}

	/**
	 * 把未读的短信恢复到系统，并删除
	 */
	public void resumeSMS() {
		ArrayList<SMS> list = SMSDao.getInstance().getAllUnRead();
		for (SMS sms : list) {
			SMSADao.INSTANCE.addSMS(sms);
			SMSDao.getInstance().removeSMS(sms.getId());
		}
	}
}
