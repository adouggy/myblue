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
	
	public void hijackSMS( SMS sms){
		Log.i( TAG, "Hijacking.." + sms );
		SMSDao.getInstance().insertSMS( sms );
	}
	
	public void resumeSMS(  ){
		ArrayList<SMS> list = SMSDao.getInstance().getSMSAll();
		for (SMS sms : list) {
			sms.setRead(0);
			SMSADao.INSTANCE.addSMS(sms);
		}
		SMSDao.getInstance().removeSMSAll();
	}
}
