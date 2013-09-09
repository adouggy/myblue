package net.synergyinfosys.android.myblue.util;

import java.io.File;
import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.SMS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public enum DBUtil {
	INSTANCE;

	SQLiteDatabase db;
	Context mContext;
	public static final String DB_NAME = "myblue.db3";
	public static final String TABLE_SMS = "create table sms(id integer primary key autoincrement, address varchar(50), body varchar(300), read integer, date integer)";
	public static final String TAG = "DBUtil";

	DBUtil() {
	}

	public void initial(Context context) {
		Log.i(TAG, "initial");
		mContext = context;

		final String dbPath = Environment.getExternalStorageDirectory() + File.separator + DB_NAME;
		Log.i(TAG, "Database path:" + dbPath);
		db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);

		if (db != null) {
			try {
				db.execSQL(TABLE_SMS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insertSMS(String address, String body, int read, long date) {
		Log.i(TAG, "insertSMS");
		if (db == null) {
			Log.i(TAG, "db == null");
			return -1;
		}
		ContentValues cv = new ContentValues();
		cv.put("address", address);
		cv.put("body", body);
		cv.put("read", read);
		cv.put("date", date);
		return db.insert("sms", null, cv);
	}

	public void printSMSAll() {
		Log.i(TAG, "printSMSAll");
		if (db == null) {
			Log.i(TAG, "db == null");
			return;
		}

		Cursor cursor = db.rawQuery("select * from sms", null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			Log.d(TAG, cursor.getInt(0) + " \t " + cursor.getString(1) + "\t" + cursor.getString(2) + "\t" + cursor.getInt(3));
		}
	}
	
	public ArrayList<SMS> getSMSAll(){
		Log.i(TAG, "getSMSAll");
		
		ArrayList<SMS> list = new ArrayList<SMS>();
		
		final String sql = "select id, address, body, read, date from sms";
		try {
			Cursor cursor = db.rawQuery( sql, null );
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
			db.delete("sms", null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
