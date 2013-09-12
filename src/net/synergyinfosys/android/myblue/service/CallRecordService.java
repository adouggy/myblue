package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.adao.CallRecordADao;
import net.synergyinfosys.android.myblue.bean.CallRecord;
import net.synergyinfosys.android.myblue.dao.CallRecordDao;

public enum CallRecordService {
	INSTANCE;
	
	CallRecordService(){}
	
	public void hideCallRecord(boolean hide, String number){
		if( hide ){
			ArrayList<CallRecord> list = CallRecordADao.INSTANCE.getCallRecord(number);
			CallRecordDao.getInstance().insert(list);
		}else{
			CallRecordDao.getInstance().removeAll();
		}
	}
}
