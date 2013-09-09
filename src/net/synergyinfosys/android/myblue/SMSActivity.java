package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.adapter.SMSListAdapter;
import net.synergyinfosys.android.myblue.util.SMSUtil;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

public class SMSActivity extends Activity {
	
	private TextView mTxtHello;
	private ListView mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sms);
		
		mTxtHello = (TextView) findViewById(R.id.txt_sms_hello);
		mList = (ListView) findViewById(R.id.list_sms);
		mList.setAdapter(new SMSListAdapter(this.getApplicationContext(), SMSUtil.INSTANCE.getHiddenSMS()));
		
		if( HomeActivity.isLock ){
			mTxtHello.setText("Locked status");
			if( HomeActivity.isFake ){
				mTxtHello.setText("Fake status");
			}
		}else{
			mTxtHello.setText("unlocked status");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sm, menu);
		return true;
	}

}
