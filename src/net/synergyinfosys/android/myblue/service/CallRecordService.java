package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import android.util.Log;

import net.synergyinfosys.android.myblue.adao.CallRecordADao;
import net.synergyinfosys.android.myblue.bean.CallRecord;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.CallRecordDao;

public enum CallRecordService {
	INSTANCE;
	
	public static final String TAG = "CallRecordService";
	
	CallRecordService(){}
	
	public int getNewCallRecordCount(){
		return CallRecordDao.getInstance().getNew().size();
	}
	
	/**
	 * find all call record by number
	 * insert each into local database and remove each from android
	 * 
	 * @param number
	 */
	public void hideCallRecord(String number, boolean isNewIncomingCall){
		Log.i( TAG, "hideLatestCallRecord:" + number );
		
		//get all call record
		ArrayList<CallRecord> list = CallRecordADao.INSTANCE.getCallRecord(number);
		
		if( list != null ){
			for( CallRecord c : list ){
				c.setNew(isNewIncomingCall);
				long id = CallRecordDao.getInstance().insert( c );
				int res = CallRecordADao.INSTANCE.removeCallRecord( c.getAndroidId() );
				
				Log.i( TAG, id + " inserted, now removing from android:" + c.getAndroidId() + " result->" + res );
			}
		}
	}
	
	/**
	 * get all intercept call record (isNew = 1) or all call record (isNew = 1 or 0)
	 * put all this call record into android
	 * remove it from local database
	 */
	public void resumeCallRecord(boolean isNew){
		ArrayList<CallRecord> list = null;
		if( isNew )
			list = CallRecordDao.getInstance().getNew();
		else
			list = CallRecordDao.getInstance().getAll();
		
		CallRecordADao.INSTANCE.resumeCallRecord(list);
		
		if( isNew )
			CallRecordDao.getInstance().removeNew();
		else
			CallRecordDao.getInstance().removeAll();
	}
	
	public void resumeCallRecordPerContact(Contact c){
		Log.i( TAG, "resumeCallRecordPerContact" );
		Log.d( TAG, "by contact:" + c.toString() );
		ArrayList<CallRecord> list = CallRecordDao.getInstance().getByContact(c);
		Log.d( TAG, "call record count:" + list.size() );
		
		CallRecordADao.INSTANCE.resumeCallRecord(list);
		
		CallRecordDao.getInstance().removeByContact(c);
	}
}
