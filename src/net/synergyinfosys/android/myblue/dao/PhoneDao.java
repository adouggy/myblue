package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Phone;
import net.synergyinfosys.android.myblue.util.Constants;
import net.synergyinfosys.android.myblue.util.StringUtil;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class PhoneDao extends AbstractDBDao {
	public static final String TAG = "PhoneDao";

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final PhoneDao INSTANCE = new PhoneDao();
	}

	public static PhoneDao getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private PhoneDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_PHONE_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			Log.i(TAG,
					"db == null");
		}
	}

	public long insert(
			Phone p) {
		Log.i(TAG,
				"insert");
		Log.i(TAG, p.toString());

		ContentValues cv = new ContentValues();
		cv.put("lookupKey",
				p.getLookupKey());
		cv.put("rawContactId",
				p.getRawContactId());
		cv.put("data1",
				StringUtil.makeNotNull( p.getData1()) );
		cv.put("data2",
				StringUtil.makeNotNull( p.getData2()) );
		cv.put("data3",
				StringUtil.makeNotNull( p.getData3()) );
		cv.put("data4",
				StringUtil.makeNotNull( p.getData4()) );
		cv.put("data5",
				StringUtil.makeNotNull( p.getData5()) );
		cv.put("data6",
				StringUtil.makeNotNull( p.getData6()) );
		cv.put("data7",
				StringUtil.makeNotNull( p.getData7()) );
		cv.put("data8",
				StringUtil.makeNotNull( p.getData8()) );
		cv.put("data9",
				StringUtil.makeNotNull( p.getData9()) );
		cv.put("data10",
				StringUtil.makeNotNull( p.getData10()) );
		cv.put("data11",
				StringUtil.makeNotNull( p.getData11()) );
		cv.put("data12",
				StringUtil.makeNotNull( p.getData12()) );
		cv.put("data13",
				StringUtil.makeNotNull( p.getData13()) );
		cv.put("data14",
				StringUtil.makeNotNull( p.getData14()) );
		cv.put("data15",
				StringUtil.makeNotNull( p.getData15()) );
		return mDBInstance.insert(Constants.DB_TABLE_PHONE_NAME,
				null,
				cv);
	}

	public int removeByLookupKey(
			String lookupKey) {
		Log.i(TAG,
				"removeByRawContactId");
		return mDBInstance.delete(Constants.DB_TABLE_PHONE_NAME,
				"lookupKey=?",
				new String[] { lookupKey });
	}

	public ArrayList<Phone> getAll(
			) {
		Log.i(TAG,
				"getAll");
		ArrayList<Phone> list = new ArrayList<Phone>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_PHONE_NAME, //+ " where androidId='" + id + "'",
				null);
		while (cursor.moveToNext()) {
			Phone p = new Phone();
			p.setId(cursor.getLong(cursor.getColumnIndex("id")));
			p.setLookupKey(cursor.getString(cursor.getColumnIndex("lookupKey")));
			p.setRawContactId(cursor.getLong(cursor.getColumnIndex("rawContactId")));
			p.setData1(cursor.getString(cursor.getColumnIndex("data1")));
			p.setData2(cursor.getString(cursor.getColumnIndex("data2")));
			p.setData3(cursor.getString(cursor.getColumnIndex("data3")));
			p.setData4(cursor.getString(cursor.getColumnIndex("data4")));
			p.setData5(cursor.getString(cursor.getColumnIndex("data5")));
			p.setData6(cursor.getString(cursor.getColumnIndex("data6")));
			p.setData7(cursor.getString(cursor.getColumnIndex("data7")));
			p.setData8(cursor.getString(cursor.getColumnIndex("data8")));
			p.setData9(cursor.getString(cursor.getColumnIndex("data9")));
			p.setData10(cursor.getString(cursor.getColumnIndex("data10")));
			p.setData11(cursor.getString(cursor.getColumnIndex("data11")));
			p.setData12(cursor.getString(cursor.getColumnIndex("data12")));
			p.setData13(cursor.getString(cursor.getColumnIndex("data13")));
			p.setData14(cursor.getString(cursor.getColumnIndex("data14")));
			p.setData15(cursor.getString(cursor.getColumnIndex("data15")));
			list.add(p);
		}
		cursor.close();
		
		Log.i( TAG, list.toString() );
		return list;
	}
	
	public ArrayList<Phone> getByLookupKey(String lookupKey
			) {
		Log.i(TAG,
				"getByLookupKey");
		ArrayList<Phone> list = new ArrayList<Phone>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_PHONE_NAME + " where lookupKey='" + lookupKey + "'",
				null);
		while (cursor.moveToNext()) {
			Phone p = new Phone();
			p.setId(cursor.getLong(cursor.getColumnIndex("id")));
			p.setLookupKey(cursor.getString(cursor.getColumnIndex("lookupKey")));
			p.setRawContactId(cursor.getLong(cursor.getColumnIndex("rawContactId")));
			p.setData1(cursor.getString(cursor.getColumnIndex("data1")));
			p.setData2(cursor.getString(cursor.getColumnIndex("data2")));
			p.setData3(cursor.getString(cursor.getColumnIndex("data3")));
			p.setData4(cursor.getString(cursor.getColumnIndex("data4")));
			p.setData5(cursor.getString(cursor.getColumnIndex("data5")));
			p.setData6(cursor.getString(cursor.getColumnIndex("data6")));
			p.setData7(cursor.getString(cursor.getColumnIndex("data7")));
			p.setData8(cursor.getString(cursor.getColumnIndex("data8")));
			p.setData9(cursor.getString(cursor.getColumnIndex("data9")));
			p.setData10(cursor.getString(cursor.getColumnIndex("data10")));
			p.setData11(cursor.getString(cursor.getColumnIndex("data11")));
			p.setData12(cursor.getString(cursor.getColumnIndex("data12")));
			p.setData13(cursor.getString(cursor.getColumnIndex("data13")));
			p.setData14(cursor.getString(cursor.getColumnIndex("data14")));
			p.setData15(cursor.getString(cursor.getColumnIndex("data15")));
			list.add(p);
		}
		cursor.close();
		
		Log.i( TAG, list.toString() );
		return list;
	}
}
