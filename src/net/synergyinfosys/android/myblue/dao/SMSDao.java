package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.util.Constants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class SMSDao extends AbstractDBDao{
	public static final String TAG = "SMSDao";
	 /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final SMSDao INSTANCE = new SMSDao();
     }

     public static SMSDao getInstance() {
             return SingletonHolder.INSTANCE;
     }
	
	private SMSDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_SMS_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insertSMS(String address, String body, int read, long date) {
		Log.i(TAG, "insertSMS");
		ContentValues cv = new ContentValues();
		cv.put("address", address);
		cv.put("body", body);
		cv.put("read", read);
		cv.put("date", date);
		return mDBInstance.insert(Constants.DB_TABLE_SMS_NAME, null, cv);
	}

	public void printSMSAll() {
		Log.i(TAG, "printSMSAll");

		Cursor cursor = mDBInstance.rawQuery("select * from sms", null);
		while (cursor.moveToNext()) {
			Log.d(TAG, cursor.getInt(0) + " \t " + cursor.getString(1) + "\t" + cursor.getString(2) + "\t" + cursor.getInt(3));
		}
	}
	
	public ArrayList<SMS> getSMSAll(){
		Log.i(TAG, "getSMSAll");
		
		ArrayList<SMS> list = new ArrayList<SMS>();
		
		final String sql = "select id, address, body, read, date from sms";
		try {
			Cursor cursor = mDBInstance.rawQuery( sql, null );
			while( cursor.moveToNext() ){
				SMS sms = new SMS();
				sms.setId( cursor.getInt(0) );
				sms.setAddress( cursor.getString(1) );
				sms.setBody( cursor.getString(2) );
				sms.setRead( cursor.getInt(3) );
				sms.setDate( cursor.getLong(4) );
				list.add(sms);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Log.i( TAG, list.size() + " sms returned");
		return list;
	}
	
	public void removeSMSAll(){
		Log.i(TAG, "removeSMSAll");	
		try {
			mDBInstance.delete("sms", null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
