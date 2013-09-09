package net.synergyinfosys.android.myblue.util;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public enum SDUtil {
	INSTANCE;
	
	public static final String TAG = "SDUtil";
	
	SDUtil() {
	}

	public void initial() {
		Log.d(TAG, "initial SDUtil");
	}
	
	public String getPath( String dirName ){
		return new File(Environment.getExternalStorageDirectory(), dirName).getAbsolutePath(); 
	}
	
	public File[] getFileList( String dirName ){
		File f = new File(Environment.getExternalStorageDirectory(), dirName);
		return f.listFiles();
	}
	
	public boolean isFileExistsInRootDir( String fileName ){
		File f = new File(Environment.getExternalStorageDirectory(), fileName);
		if( f == null || !f.exists() ){
			return false;
		}
		return true;
	}
	
	public boolean isFileExistsInDir( String fileName, String dirName ){
		File[] list = getFileList(dirName);
		if( list != null ){
			for( File f : list ){
				if( f.getName().compareTo( fileName ) == 0 ){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isSDCardReady(){
		return (Environment.getExternalStorageState() == null)?false:true;
	}
}
