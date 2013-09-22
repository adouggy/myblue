package net.synergyinfosys.android.myblue.helper;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adapter.CallRecordListAdapter;
import net.synergyinfosys.android.myblue.dao.CallRecordDao;
import android.app.Activity;
import android.widget.ListView;

public class CallRecordHelper extends MyHelper {
	
	public static final String TAG = "CallRecordHelper";
	private ListView mCallRecordList = null;
	private static CallRecordListAdapter mCallRecordListAdapter = null;

	public CallRecordHelper(Activity act) {
		super(act);
	}
	
	@Override
	public void onCreate(int layoutId) throws IllegalStateException {
		super.onCreate(layoutId);
	}
	
	@Override
	public void onPostCreate() throws IllegalStateException {
		super.onPostCreate();
		
		mCallRecordList = (ListView) getView().findViewById(R.id.list_call_record);
		mCallRecordListAdapter = new CallRecordListAdapter( mActivity, CallRecordDao.getInstance().getAll());
		mCallRecordList.setAdapter( mCallRecordListAdapter );
	}
	
	public static void refresh() {
		CallRecordListAdapter.setData(CallRecordDao.getInstance().getAll());
		if( mCallRecordListAdapter!=null )
			mCallRecordListAdapter.notifyDataSetChanged();
	}

}
