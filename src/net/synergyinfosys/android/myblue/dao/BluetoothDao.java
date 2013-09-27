package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Bluetooth;
import net.synergyinfosys.android.myblue.util.Constants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class BluetoothDao extends AbstractDBDao{
	public static final String TAG = "BluetoothDao";
	 /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final BluetoothDao INSTANCE = new BluetoothDao();
     }

     public static BluetoothDao getInstance() {
             return SingletonHolder.INSTANCE;
     }
	
	private BluetoothDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_BLUETOOTH_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insert( Bluetooth b ){
		Log.i(TAG, "insert");
		ContentValues cv = new ContentValues();
		cv.put("name",b.getName());
		cv.put("mac", b.getMac());
		return mDBInstance.insert(Constants.DB_TABLE_BLUETOOTH_NAME, null, cv);
	}
	
	public int remove( long id ){
		Log.i(TAG, "remove");
		return mDBInstance.delete(Constants.DB_TABLE_BLUETOOTH_NAME, "id=?", new String[]{ id+"" });
	}
	
	public int remove( String mac ){
		Log.i(TAG, "remove via mac");
		return mDBInstance.delete(Constants.DB_TABLE_BLUETOOTH_NAME, "mac=?", new String[]{ mac });
	}
	
	public int update(Bluetooth b){
		Log.i(TAG, "update");
		ContentValues cv = new ContentValues();
		cv.put("name", b.getName());
		cv.put("mac", b.getMac());
		return mDBInstance.update(Constants.DB_TABLE_BLUETOOTH_NAME, cv, "id=?", new String[]{ b.getId()+"" });
	}
	
	public Bluetooth get(long id){
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_BLUETOOTH_NAME + " where id='" + id + "'", null);
		while (cursor.moveToNext()) {
			Bluetooth b = new Bluetooth();
			b.setId( cursor.getInt( cursor.getColumnIndex("id") ) );
			b.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			b.setMac( cursor.getString( cursor.getColumnIndex("mac") ) );
			return b;
		}
		cursor.close();
		return null;
	}
	
	public Bluetooth get(String mac){
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_BLUETOOTH_NAME + " where mac='" + mac + "'", null);
		while (cursor.moveToNext()) {
			Bluetooth b = new Bluetooth();
			b.setId( cursor.getInt( cursor.getColumnIndex("id") ) );
			b.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			b.setMac( cursor.getString( cursor.getColumnIndex("mac") ) );
			return b;
		}
		cursor.close();
		return null;
	}
	
	public ArrayList<Bluetooth> getAll(){
		Log.i(TAG, "getAll");
		ArrayList<Bluetooth> list = new ArrayList<Bluetooth>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_BLUETOOTH_NAME, null);
		while (cursor.moveToNext()) {
			Bluetooth b = new Bluetooth();
			b.setId( cursor.getInt( cursor.getColumnIndex("id") ) );
			b.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			b.setMac( cursor.getString( cursor.getColumnIndex("mac") ) );
			list.add( b );
		}
		Log.i( TAG, list.size() + " results returned" );
		Log.d( TAG, list.toString() );
		cursor.close();
		return list;
	}
}
