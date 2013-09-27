package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Location;
import net.synergyinfosys.android.myblue.util.Constants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class LocationDao extends AbstractDBDao{
	public static final String TAG = "LocationDao";
	 /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final LocationDao INSTANCE = new LocationDao();
     }

     public static LocationDao getInstance() {
             return SingletonHolder.INSTANCE;
     }
	
	private LocationDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_LOCATION_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insert( Location loc ){
		Log.i(TAG, "insert");
		ContentValues cv = new ContentValues();
		cv.put("name", loc.getName());
		cv.put("latitude", loc.getLatitude());
		cv.put("longtitude", loc.getLongitude());
		cv.put("description", loc.getDescription());
		return mDBInstance.insert(Constants.DB_TABLE_LOCATION_NAME, null, cv);
	}
	
	public int remove( long id ){
		Log.i(TAG, "remove");
		return mDBInstance.delete(Constants.DB_TABLE_LOCATION_NAME, "id=?", new String[]{ id+"" });
	}
	
	public int update(Location loc){
		Log.i(TAG, "update");
		ContentValues cv = new ContentValues();
		cv.put("name", loc.getName());
		cv.put("latitude", loc.getLatitude());
		cv.put("longtitude", loc.getLongitude());
		cv.put("description", loc.getDescription());
		return mDBInstance.update(Constants.DB_TABLE_LOCATION_NAME, cv, "id=?", new String[]{ loc.getId()+"" });
	}
	
	public Location get(long id){
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_LOCATION_NAME + " where id='" + id + "'", null);
		while (cursor.moveToNext()) {
			Location loc = new Location();
			loc.setName( cursor.getString( cursor.getColumnIndex( "name" ) ) );
			loc.setLatitude( cursor.getDouble( cursor.getColumnIndex( "latitude" ) ) );
			loc.setLongitude( cursor.getDouble( cursor.getColumnIndex( "longtitude" ) ) );
			loc.setDescription( cursor.getString( cursor.getColumnIndex( "description" ) ) );
			return loc;
		}
		cursor.close();
		return null;
	}
	
	public ArrayList<Location> getAll(){
		Log.i(TAG, "getAll");
		ArrayList<Location> list = new ArrayList<Location>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_LOCATION_NAME, null);
		while (cursor.moveToNext()) {
			Location loc = new Location();
			loc.setId( cursor.getInt( cursor.getColumnIndex( "id" ) ) );
			loc.setName( cursor.getString( cursor.getColumnIndex( "name" ) ) );
			loc.setLatitude( cursor.getDouble( cursor.getColumnIndex( "latitude" ) ) );
			loc.setLongitude( cursor.getDouble( cursor.getColumnIndex( "longtitude" ) ) );
			loc.setDescription( cursor.getString( cursor.getColumnIndex( "description" ) ) );
			list.add( loc );
		}
		Log.i( TAG, list.size() + " results returned" );
		Log.d( TAG, list.toString() );
		cursor.close();
		return list;
	}
}
