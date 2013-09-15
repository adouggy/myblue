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
	
	public void hideLatestCallRecord(String number){
		Log.i( TAG, "hideLatestCallRecord:" + number );
		ArrayList<CallRecord> list = CallRecordADao.INSTANCE.getCallRecord(number);
		
		
//		if( list != null && list.size()>0 ){
//			long id = CallRecordDao.getInstance().insert( list.get(list.size()-1) );
//			if( id > 0 ){
//				Log.i( TAG, id + " inserted, now removing from android:" + list.get(list.size()-1).getAndroidId() );
//				int res = CallRecordADao.INSTANCE.removeCallRecord( list.get(0).getAndroidId() );
//				Log.i(TAG, "remove result:" + res);
//			}
//		}
		
		//how about all
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
