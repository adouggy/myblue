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
	
	public long insertSMS( SMS sms ){
		ContentValues cv = new ContentValues();
		cv.put("address", sms.getAddress());
		cv.put("body", sms.getBody());
		cv.put("read", sms.getRead());
		cv.put("type", sms.getType());
		cv.put("date", sms.getDate());
		cv.put("androidId", sms.getAndroidId());
		cv.put("isDelete", sms.isDelete());
		return mDBInstance.insert(Constants.DB_TABLE_SMS_NAME, null, cv);
	}
	
	public ArrayList<SMS> getAll(){
		Log.i(TAG, "getAll");
		
		ArrayList<SMS> list = new ArrayList<SMS>();
		final String sql = "select * from " + Constants.DB_TABLE_SMS_NAME + " order by date desc";
		try {
			Cursor cursor = mDBInstance.rawQuery( sql, null );
			while( cursor.moveToNext() ){
				SMS sms = new SMS();
				sms.setId( cursor.getLong(cursor.getColumnIndex("id")) );
				sms.setAddress( cursor.getString(cursor.getColumnIndex("address")) );
				sms.setBody( cursor.getString(cursor.getColumnIndex("body")) );
				sms.setRead( cursor.getInt(cursor.getColumnIndex("read")) );
				sms.setType( cursor.getInt(cursor.getColumnIndex("type")) );
				sms.setDate( cursor.getLong(cursor.getColumnIndex("date")) );
				sms.setAndroidId( cursor.getLong(cursor.getColumnIndex("androidId")) );
				sms.setDelete( cursor.getInt(cursor.getColumnIndex("isDelete"))==1?true:false );
				list.add(sms);
			}
			cursor.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Log.i( TAG, list.size() + " sms returned");
		Log.i( TAG, list.toString() );
		return list;
	}
	
	public ArrayList<SMS> getAll(int read){
		Log.i(TAG, "getAll");
		
		ArrayList<SMS> list = new ArrayList<SMS>();
		final String sql = "select * from " + Constants.DB_TABLE_SMS_NAME + " where read=" + read + " order by date desc";
		try {
			Cursor cursor = mDBInstance.rawQuery( sql, null );
			while( cursor.moveToNext() ){
				SMS sms = new SMS();
				sms.setId( cursor.getLong(cursor.getColumnIndex("id")) );
				sms.setAddress( cursor.getString(cursor.getColumnIndex("address")) );
				sms.setBody( cursor.getString(cursor.getColumnIndex("body")) );
				sms.setRead( cursor.getInt(cursor.getColumnIndex("read")) );
				sms.setDate( cursor.getLong(cursor.getColumnIndex("date")) );
				sms.setType( cursor.getInt(cursor.getColumnIndex("type")) );
				sms.setAndroidId( cursor.getLong(cursor.getColumnIndex("androidId")) );
				sms.setDelete( cursor.getInt(cursor.getColumnIndex("isDelete"))==1?true:false );
				list.add(sms);
			}
			cursor.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Log.i( TAG, list.size() + " sms returned");
		Log.i( TAG, list.toString() );
		
		return list;
	}
	
	public ArrayList<SMS> getAllRead(){
		return getAll(1); 
	}
	
	public ArrayList<SMS> getAllUnRead(){
		return getAll(0);
	}
	
	public int removeSMS( long id ){
		return mDBInstance.delete(Constants.DB_TABLE_SMS_NAME, "id=?", new String[]{ id+"" });
	}
	
	public void removeSMSAll(){
		Log.i(TAG, "removeSMSAll");	
		try {
			mDBInstance.delete(Constants.DB_TABLE_SMS_NAME, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
