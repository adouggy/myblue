package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import android.util.Log;
import net.synergyinfosys.android.myblue.adao.SMSADao;
import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.dao.SMSDao;

public enum SMSService {
	INSTANCE;
	
	public static final String TAG = "SMSService";
	
	SMSService(){}
	
//	迁移短信，目前暂时不用
//	public void hideSMS(boolean isHide, String number) {
//		if (isHide) {
//			ArrayList<SMS> list = SMSADao.INSTANCE.getSMS(number);
//			for( SMS sms: list ){
//				//TODO: transction..
//				SMSDao.getInstance().insertSMS(sms);
//				SMSADao.INSTANCE.deleteSMS( sms.getAndroidId() );
//			}
//		} else {
//			//TODO: 这里也要做校验，防止丢数据
//			ArrayList<SMS> list = SMSDao.getInstance().getSMSAll();
//			for (SMS sms : list) {
//				SMSADao.INSTANCE.addSMS(sms);
//			}
//			SMSDao.getInstance().removeSMSAll();
//		}
//	}
	
	/**
	 * 有新的短信的时候，插入新的，并且把已读的短信也给导入进来
	 * @param newSms
	 */
	public void hijackSMS( SMS newSms){
		Log.i( TAG, "Hijacking.." + newSms );
		SMSDao.getInstance().insertSMS( newSms );
		
		ArrayList<SMS> list = SMSADao.INSTANCE.getSMS( newSms.getAddress() );
		for( SMS s : list ){
			SMSDao.getInstance().insertSMS(s);
			SMSADao.INSTANCE.deleteSMS( s.getAndroidId() );
		}
	}
	
	/**
	 * 把未读的短信恢复到系统，并删除 
	 */
	public void resumeSMS(  ){
		ArrayList<SMS> list = SMSDao.getInstance().getAllUnRead();
		for (SMS sms : list) {
			SMSADao.INSTANCE.addSMS(sms);
			SMSDao.getInstance().removeSMS( sms.getId() );
		}
	}
}
