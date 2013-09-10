/**
 * 
 */
package net.synergyinfosys.android.myblue.dao;

import java.io.File;

import net.synergyinfosys.android.myblue.util.Constants;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

/**
 * @author ade
 *
 */
public abstract class AbstractDBDao {
	
	public static final String TAG = "AbstractDBDao";
	
	protected SQLiteDatabase mDBInstance;
	
	public AbstractDBDao(){
		Log.i(TAG, "initial");
		File f = new File(Environment.getExternalStorageDirectory() + File.separator + Constants.STORAGE_PATH);
		if( !f.exists() ){
			f.mkdir();
		}
		final String dbPath = Environment.getExternalStorageDirectory() + File.separator + Constants.STORAGE_PATH + File.separator + Constants.DB_NAME;
		mDBInstance = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
	}
}
