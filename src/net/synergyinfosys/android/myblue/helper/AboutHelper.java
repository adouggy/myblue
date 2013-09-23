package net.synergyinfosys.android.myblue.helper;

import net.synergyinfosys.android.myblue.R;
import android.app.Activity;
import android.widget.TextView;

public class AboutHelper extends MyHelper{
	
	public static final String TAG = "AboutHelper";
	private TextView mTxtStatus = null;
	
	public AboutHelper(Activity act){
		super( act );
	}
	
	@Override
	public void onCreate(int layoutId){
		super.onCreate( layoutId );
	}
	
	@Override
	public void onPostCreate(){
		super.onPostCreate();
		
		mTxtStatus = (TextView) getView().findViewById(R.id.txt_about_status);
	}
	
	public void setStatus(String status){
		this.mTxtStatus.setText(status);
	}
}
