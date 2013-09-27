package net.synergyinfosys.android.myblue.helper;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adao.SMSADao;
import android.app.Activity;
import android.widget.TextView;

public class DebugHelper extends MyHelper{
	
	public static final String TAG = "DebugHelper";
	private TextView mTxtStatus = null;
	
	public DebugHelper(Activity act){
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
		
		StringBuilder sb = new StringBuilder();
		sb.append("Debug..");
		
//		String[] dbs = "sms contact callrecord encrypt wifi location gesture bluetooth secretcode".split(" ");
//		for( String d : dbs ){
//			sb.append("deleting.." + d); 
//			sb.append( "\n" );
//			DebugDao.getInstance().executeRawSql("drop table if exists " + d + ";");
//		}
		
		SMSADao.INSTANCE.getSMS("13601303722");
		
		mTxtStatus.setTextSize(10f);
		mTxtStatus.setText(sb.toString());
	}
	
	public void setStatus(String status){
		this.mTxtStatus.setText(status);
	}
}
