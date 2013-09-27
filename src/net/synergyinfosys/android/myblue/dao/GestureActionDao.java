package net.synergyinfosys.android.myblue.dao;

import net.synergyinfosys.android.myblue.bean.GestureAction;
import net.synergyinfosys.android.myblue.util.Constants;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class GestureActionDao extends AbstractDBDao {
	public static final String TAG = "GestureActionDao";

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final GestureActionDao INSTANCE = new GestureActionDao();
	}

	public static GestureActionDao getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private GestureActionDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_GESTURE_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			Log.i(TAG, "db == null");
		}
	}

	public long insert(GestureAction act) {
		Log.i(TAG, "insert");
		ContentValues cv = new ContentValues();
		cv.put("action", act.toString());
		cv.put("timestamp", System.currentTimeMillis());
		return mDBInstance.insert(Constants.DB_TABLE_GESTURE_NAME, null, cv);
	}

	public long getLatest(GestureAction act) {
		Log.i(TAG, "get");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_GESTURE_NAME + " where action='" + act.toString() + "' order by timestamp desc", null);
		while (cursor.moveToNext()) {
			return cursor.getLong(cursor.getColumnIndex("timestamp"));
		}
		cursor.close();
		return -1;
	}
}
