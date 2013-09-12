package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.adao.SMSADao;
import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.dao.SMSDao;

public enum SMSService {
	INSTANCE;
	
	SMSService(){}
	
	public void hideSMS(boolean isHide, String number) {
		if (isHide) {
			ArrayList<SMS> list = SMSADao.INSTANCE.getSMS(number);
			for( SMS sms: list ){
				//TODO: transction..
				SMSDao.getInstance().insertSMS(sms);
				SMSADao.INSTANCE.deleteSMS( sms.getAndroidId() );
			}
		} else {
			//TODO: 这里也要做校验，防止丢数据
			ArrayList<SMS> list = SMSDao.getInstance().getSMSAll();
			for (SMS sms : list) {
				SMSADao.INSTANCE.addSMS(sms);
			}
			SMSDao.getInstance().removeSMSAll();
		}
	}
}
