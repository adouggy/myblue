package net.synergyinfosys.android.myblue.helper;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adapter.SMSListAdapter;
import net.synergyinfosys.android.myblue.dao.SMSDao;
import android.app.Activity;
import android.widget.ListView;

public class SMSHelper extends MyHelper {
	
	private static ListView mList;
	private static SMSListAdapter mAdapter = null;

	public SMSHelper(Activity act) {
		super(act);
	}
	
	@Override
	public void onCreate(int layoutId) throws IllegalStateException {
		super.onCreate(layoutId);
	}

	@Override
	public void onPostCreate() throws IllegalStateException {
		super.onPostCreate();
		
		mList = (ListView) getView().findViewById(R.id.list_sms);
		mAdapter  = new SMSListAdapter( mActivity, SMSDao.getInstance().getSMSAll());
		mList.setAdapter( mAdapter );
	}
	
	public static void refresh() {
		SMSListAdapter.setData(SMSDao.getInstance().getSMSAll());
	}
}
