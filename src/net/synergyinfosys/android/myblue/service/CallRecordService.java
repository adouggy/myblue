package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import android.util.Log;

import net.synergyinfosys.android.myblue.adao.CallRecordADao;
import net.synergyinfosys.android.myblue.bean.CallRecord;
import net.synergyinfosys.android.myblue.dao.CallRecordDao;

public enum CallRecordService {
	INSTANCE;
	
	public static final String TAG = "CallRecordService";
	
	CallRecordService(){}
	
	public void hideCallRecord(boolean hide, String number){
		if( hide ){
			ArrayList<CallRecord> list = CallRecordADao.INSTANCE.getCallRecord(number);
			CallRecordDao.getInstance().insert(list);
		}else{
			CallRecordDao.getInstance().removeAll();
		}
	}
	
	public void hideCallRecord(String number){
		Log.i( TAG, "hideLatestCallRecord:" + number );
		
		//get all call record
		ArrayList<CallRecord> list = CallRecordADao.INSTANCE.getCallRecord(number);
		
		if( list != null ){
			for( CallRecord c : list ){
				long id = CallRecordDao.getInstance().insert( c );
				int res = CallRecordADao.INSTANCE.removeCallRecord( c.getAndroidId() );
				
				Log.i( TAG, id + " inserted, now removing from android:" + c.getAndroidId() + " result->" + res );
			}
		}
	}
	
	public void resumeCallRecord(){
		ArrayList<CallRecord> list = CallRecordDao.getInstance().getAll();
		CallRecordADao.INSTANCE.resumeCallRecord(list);
		CallRecordDao.getInstance().removeAll();
	}
}
