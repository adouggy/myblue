package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.CallRecord;
import net.synergyinfosys.android.myblue.bean.CallStatus;
import net.synergyinfosys.android.myblue.util.Constants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class CallRecordDao extends AbstractDBDao{
	public static final String TAG = "CallRecordDao";
	 /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final CallRecordDao INSTANCE = new CallRecordDao();
     }

     public static CallRecordDao getInstance() {
             return SingletonHolder.INSTANCE;
     }
	
	private CallRecordDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_CALL_RECORD_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insert( CallRecord c ){
		Log.i(TAG, "insert");
		ContentValues cv = new ContentValues();
		cv.put("contactId", c.getContactId());
		cv.put("recordTime", c.getRecordTime());
		cv.put("status", c.getStatus().toString());
		cv.put("androidId", c.getAndroidId());
		cv.put("isDelete", c.isDelete());
		return mDBInstance.insert(Constants.DB_TABLE_CALL_RECORD_NAME, null, cv);
	}
	
	public long insert( ArrayList<CallRecord> list ){
		//TODO: make it transaction
		Log.i(TAG, "insert list");
		long id = -1;
		for( CallRecord c : list){
			id = insert(c);
		}
		
		return id;
	}
	
	public int remove( long id ){
		Log.i(TAG, "remove");
		return mDBInstance.delete(Constants.DB_TABLE_CALL_RECORD_NAME, "id=?", new String[]{ id+"" });
//		CallRecord c = get(id);
//		if( c == null )
//			return -1;
//		c.setDelete(true);
//		return update( c );
	}
	
	public int removeAll(){
		Log.i(TAG, "remove");
		return mDBInstance.delete(Constants.DB_TABLE_CALL_RECORD_NAME, null, null);
	}
	
	public int update(CallRecord c){
		Log.i(TAG, "update");
		ContentValues cv = new ContentValues();
		cv.put("contactId", c.getContactId());
		cv.put("recordTime", c.getRecordTime());
		cv.put("status", c.getStatus().toString());
		cv.put("androidId", c.getAndroidId());
		cv.put("isDelete", c.isDelete());
		return mDBInstance.update(Constants.DB_TABLE_CALL_RECORD_NAME, cv, "id=?", new String[]{ c.getId()+"" });
	}
	
	public CallRecord get(long id){
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_CALL_RECORD_NAME + " where id='" + id +"'" , null);
		while (cursor.moveToNext()) {
			CallRecord c= new CallRecord();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setContactId( cursor.getLong( cursor.getColumnIndex("contactId") ) );
			c.setRecordTime( cursor.getLong( cursor.getColumnIndex("recordTime") ) );
			c.setStatus( CallStatus.valueOf(cursor.getString( cursor.getColumnIndex("status") ) ) );
			c.setAndroidId( cursor.getLong( cursor.getColumnIndex("androidId")) );
			c.setDelete( cursor.getInt( cursor.getColumnIndex("isDelete") )==1?true:false );
			return c;
		}
		return null;
	}
	
	public ArrayList<CallRecord> getAll(){
		Log.i(TAG, "getAll");
		ArrayList<CallRecord> list = new ArrayList<CallRecord>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_CALL_RECORD_NAME + " order by recordTime desc", null);
		while (cursor.moveToNext()) {
			CallRecord c= new CallRecord();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setContactId( cursor.getLong( cursor.getColumnIndex("contactId") ) );
			c.setRecordTime( cursor.getLong( cursor.getColumnIndex("recordTime") ) );
			c.setStatus( CallStatus.valueOf(cursor.getString( cursor.getColumnIndex("status") ) ) );
			c.setAndroidId( cursor.getLong( cursor.getColumnIndex("androidId")) );
			c.setDelete( cursor.getInt( cursor.getColumnIndex("isDelete") )==1?true:false );
			list.add( c );
		}
		Log.i( TAG, list.size() + " results returned" );
		Log.d( TAG, list.toString() );
		return list;
	}
}
