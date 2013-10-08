package net.synergyinfosys.android.myblue.asynjob;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.adapter.SMSListAdapter;
import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.ui.cache.SMSCache;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class SMSRetrieveJob extends AsyncTask<ListView, Integer, ArrayList<SMS>> {

	public static final String TAG = "SMSRetrieveJob";
	
	private String mContact = null;
	private SMSListAdapter mAdapter = null;
	private ListView mListView = null;
	
	@Override
	protected ArrayList<SMS> doInBackground(
			ListView... param) {
		Log.d( TAG, "retriving..." );
		if( param == null )
			return null;
		
		mListView = param[0];
		mAdapter = (SMSListAdapter) mListView.getAdapter();
		mContact = mAdapter.getContactName();
		
		Log.d( TAG, mContact);
		
		return SMSCache.getInstance().getAllSMS().get(mContact);
	}

	@Override
	protected void onPostExecute(
			ArrayList<SMS> result) {
		Log.d( TAG, "updating UI...with " + result.size()  );
		
		if( mAdapter != null ){
			mAdapter.setData(result);
			mAdapter.notifyDataSetChanged();
		}
		
		if( mListView != null ){
			Log.d(TAG, "invalidating list view");
//			mListView.invalidate();
			mListView.invalidateViews();
		}
		
		super.onPostExecute(result);
	}
}
