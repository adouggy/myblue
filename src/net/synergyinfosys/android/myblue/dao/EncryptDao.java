package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Encrypt;
import net.synergyinfosys.android.myblue.util.Constants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class EncryptDao extends AbstractDBDao{
	public static final String TAG = "EncryptDao";
	 /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final EncryptDao INSTANCE = new EncryptDao();
     }

     public static EncryptDao getInstance() {
             return SingletonHolder.INSTANCE;
     }
	
	private EncryptDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_ENCRYPT_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insert( Encrypt encrypt ){
		Log.i(TAG, "insert");
		ContentValues cv = new ContentValues();
		cv.put("name", encrypt.getName());
		cv.put("password", encrypt.getPassword());
		cv.put("comment", encrypt.getComment());
		return mDBInstance.insert(Constants.DB_TABLE_ENCRYPT_NAME, null, cv);
	}
	
	public int remove( long id ){
		Log.i(TAG, "remove");
		return mDBInstance.delete(Constants.DB_TABLE_ENCRYPT_NAME, "id=?", new String[]{ id+"" });
	}
	
	public int update(Encrypt encrypt){
		Log.i(TAG, "update");
		ContentValues cv = new ContentValues();
		cv.put("name", encrypt.getName());
		cv.put("password", encrypt.getPassword());
		cv.put("comment", encrypt.getComment());
		return mDBInstance.update(Constants.DB_TABLE_ENCRYPT_NAME, cv, "id=?", new String[]{ encrypt.getId()+"" });
	}
	
	public Encrypt get(long id){
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_ENCRYPT_NAME + " where id='" + id + "'", null);
		while (cursor.moveToNext()) {
			Encrypt c= new Encrypt();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			c.setPassword( cursor.getString( cursor.getColumnIndex("password") ) );
			c.setComment( cursor.getString( cursor.getColumnIndex("comment") ) );
			return c;
		}
		cursor.close();
		return null;
	}
	
	public ArrayList<Encrypt> getAll(){
		Log.i(TAG, "getAll");
		ArrayList<Encrypt> list = new ArrayList<Encrypt>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_ENCRYPT_NAME, null);
		while (cursor.moveToNext()) {
			Encrypt c= new Encrypt();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			c.setPassword( cursor.getString( cursor.getColumnIndex("password") ) );
			c.setComment( cursor.getString( cursor.getColumnIndex("comment") ) );
			list.add( c );
		}
		Log.i( TAG, list.size() + " results returned" );
		Log.d( TAG, list.toString() );
		cursor.close();
		return list;
	}
}
