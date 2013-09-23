package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.SecretCode;
import net.synergyinfosys.android.myblue.util.Constants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class SecretCodeDao extends AbstractDBDao{
	public static final String TAG = "SecretCodeDao";
	 /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final SecretCodeDao INSTANCE = new SecretCodeDao();
     }

     public static SecretCodeDao getInstance() {
             return SingletonHolder.INSTANCE;
     }
	
	private SecretCodeDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_SECRET_CODE_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insert( SecretCode s ){
		Log.i(TAG, "insert");
		ContentValues cv = new ContentValues();
		cv.put("code", s.getCode());
		cv.put("timestamp", System.currentTimeMillis());
		return mDBInstance.insert(Constants.DB_TABLE_SECRET_CODE_NAME, null, cv);
	}
	
	public int remove( long id ){
		Log.i(TAG, "remove");
		return mDBInstance.delete(Constants.DB_TABLE_SECRET_CODE_NAME, "id=?", new String[]{ id+"" });
	}
	
	public int update(SecretCode s){
		Log.i(TAG, "update");
		ContentValues cv = new ContentValues();
		cv.put("code", s.getCode());
		cv.put("timestamp", System.currentTimeMillis());
		return mDBInstance.update(Constants.DB_TABLE_SECRET_CODE_NAME, cv, "id=?", new String[]{ s.getId()+"" });
	}
	
	public SecretCode get(long id){
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_SECRET_CODE_NAME + " where id='" + id + "'", null);
		while (cursor.moveToNext()) {
			SecretCode s = new SecretCode();
			s.setId( cursor.getInt( cursor.getColumnIndex("id") ) );
			s.setCode( cursor.getString( cursor.getColumnIndex("code") ) );
			s.setTimestamp( cursor.getLong( cursor.getColumnIndex("timestamp") ) );
			return s;
		}
		return null;
	}
	
	public ArrayList<SecretCode> getAll(){
		Log.i(TAG, "getAll");
		ArrayList<SecretCode> list = new ArrayList<SecretCode>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_SECRET_CODE_NAME + " order by timestamp desc", null);
		while (cursor.moveToNext()) {
			SecretCode s = new SecretCode();
			s.setId( cursor.getInt( cursor.getColumnIndex("id") ) );
			s.setCode( cursor.getString( cursor.getColumnIndex("code") ) );
			s.setTimestamp( cursor.getLong( cursor.getColumnIndex("timestamp") ) );
			list.add( s );
		}
		Log.i( TAG, list.size() + " results returned" );
		Log.d( TAG, list.toString() );
		return list;
	}
}
