package net.synergyinfosys.android.myblue.util;

import net.synergyinfosys.xmppclient.Constants;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public enum XMPPUtil {
	INSTANCE;
	
	public static final String SHARED_PREFERENCE_NAME = "client_preferences";
	public static final String PREFERENCE_USERNAME = "username";
	public static final String PREFERENCE_PASSWORD = "password";
	public static final String PREFERENCE_PUSHNAME = Constants.XMPP_USERNAME;
	public static final String PREFERENCE_PUSHPSWD = Constants.XMPP_PASSWORD;
	
	XMPPUtil(){}
	
	private Context mContext;
	
	public void initial(Context ctx){
		this.mContext = ctx;
		
		this.initialUser();
	}
	
	private void initialUser() {
		SharedPreferences sharedPrefs = this.mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putString(PREFERENCE_USERNAME, "myblue");
		editor.putString(PREFERENCE_PASSWORD, "myblue");
		editor.putString(PREFERENCE_PUSHNAME, "myblue");
		editor.putString(PREFERENCE_PUSHPSWD, "myblue");
		editor.putString("XMPP_HOST", "114.242.189.52");
		editor.putInt("XMPP_PORT", 5222);
		editor.putString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME, "net.synergyinfosys.xmppclienttest");
		editor.putString(Constants.CALLBACK_ACTIVITY_CLASS_NAME, this.getClass().getName());
		editor.commit();
	}
}
