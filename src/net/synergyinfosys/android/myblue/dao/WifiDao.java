package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Wifi;
import net.synergyinfosys.android.myblue.util.Constants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class WifiDao extends AbstractDBDao{
	public static final String TAG = "WifiDao";
	 /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final WifiDao INSTANCE = new WifiDao();
     }

     public static WifiDao getInstance() {
             return SingletonHolder.INSTANCE;
     }
	
	private WifiDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_WIFI_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insert( Wifi wifi ){
		Log.i(TAG, "insert");
		ContentValues cv = new ContentValues();
		cv.put("ssid", wifi.getSsid());
		cv.put("bssid", wifi.getBssid());
		cv.put("checked", wifi.isChecked());
		return mDBInstance.insert(Constants.DB_TABLE_WIFI_NAME, null, cv);
	}
	
	public int remove( long id ){
		Log.i(TAG, "remove");
		return mDBInstance.delete(Constants.DB_TABLE_WIFI_NAME, "id=?", new String[]{ id+"" });
	}
	
	public int remove( String bssid ){
		Log.i(TAG, "remove via bssid");
		return mDBInstance.delete(Constants.DB_TABLE_WIFI_NAME, "bssid=?", new String[]{ bssid });
	}
	
	public int update(Wifi wifi){
		Log.i(TAG, "update");
		ContentValues cv = new ContentValues();
		cv.put("ssid", wifi.getSsid());
		cv.put("bssid", wifi.getBssid());
		cv.put("checked", wifi.isChecked());
		return mDBInstance.update(Constants.DB_TABLE_WIFI_NAME, cv, "id=?", new String[]{ wifi.getId()+"" });
	}
	
	public Wifi get(long id){
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_WIFI_NAME + " where id='" + id + "'", null);
		while (cursor.moveToNext()) {
			Wifi wifi = new Wifi();
			wifi.setId( cursor.getInt( cursor.getColumnIndex("id")) );
			wifi.setSsid( cursor.getString( cursor.getColumnIndex("ssid") ) );
			wifi.setBssid( cursor.getString( cursor.getColumnIndex("bssid") ) );
			wifi.setChecked( cursor.getInt( cursor.getColumnIndex("checked") )==1?true:false );
			return wifi;
		}
		return null;
	}
	
	public Wifi get(String bssid){
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_WIFI_NAME + " where bssid='" + bssid + "'", null);
		while (cursor.moveToNext()) {
			Wifi wifi = new Wifi();
			wifi.setId( cursor.getInt( cursor.getColumnIndex("id") ) );
			wifi.setSsid( cursor.getString( cursor.getColumnIndex("ssid") ) );
			wifi.setBssid( cursor.getString( cursor.getColumnIndex("bssid") ) );
			wifi.setChecked( cursor.getInt( cursor.getColumnIndex("checked") )==1?true:false );
			return wifi;
		}
		return null;
	}
	
	public ArrayList<Wifi> getAll(){
		Log.i(TAG, "getAll");
		ArrayList<Wifi> list = new ArrayList<Wifi>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_WIFI_NAME, null);
		while (cursor.moveToNext()) {
			Wifi wifi = new Wifi();
			wifi.setId( cursor.getInt( cursor.getColumnIndex("id") ) );
			wifi.setSsid( cursor.getString( cursor.getColumnIndex("ssid") ) );
			wifi.setBssid( cursor.getString( cursor.getColumnIndex("bssid") ) );
			wifi.setChecked( cursor.getInt( cursor.getColumnIndex("checked") )==1?true:false );
			list.add( wifi );
		}
		Log.i( TAG, list.size() + " results returned" );
		Log.d( TAG, list.toString() );
		return list;
	}
}
