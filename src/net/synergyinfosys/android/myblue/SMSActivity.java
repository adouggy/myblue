package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.adapter.SMSListAdapter;
import net.synergyinfosys.android.myblue.dao.SMSDao;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

public class SMSActivity extends Activity {
	
	
	private static ListView mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sms);
		
		mList = (ListView) findViewById(R.id.list_sms);
		mList.setAdapter(new SMSListAdapter(this.getApplicationContext(), SMSDao.getInstance().getSMSAll()));
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sm, menu);
		return true;
	}

}
