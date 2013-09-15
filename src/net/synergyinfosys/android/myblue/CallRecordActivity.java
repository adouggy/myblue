package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.adapter.CallRecordListAdapter;
import net.synergyinfosys.android.myblue.dao.CallRecordDao;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

public class CallRecordActivity extends Activity {
	public static final String TAG = "CallRecordActivity";
	private ListView mCallRecordList = null;
	private static CallRecordListAdapter mCallRecordListAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_call_record);
		
		mCallRecordList = (ListView) findViewById(R.id.list_call_record);
		mCallRecordListAdapter = new CallRecordListAdapter( this.getApplicationContext(), CallRecordDao.getInstance().getAll());
		mCallRecordList.setAdapter( mCallRecordListAdapter );
	}
	
	public static void refresh() {
		CallRecordListAdapter.setData(CallRecordDao.getInstance().getAll());
		if( mCallRecordListAdapter!=null )
			mCallRecordListAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.call_record, menu);
		return true;
	}

}
