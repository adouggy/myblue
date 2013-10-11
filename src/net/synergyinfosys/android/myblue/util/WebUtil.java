package net.synergyinfosys.android.myblue.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public enum WebUtil {
	INSTANCE;
	
	WebUtil(){
		
	}
	
	public static void goToHomePage(Context context) {
		Uri uriUrl = Uri.parse("http://www.synergyinfo.cn");
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl); 
		context.startActivity(launchBrowser);
	}
}
