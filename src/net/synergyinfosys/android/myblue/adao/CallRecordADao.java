package net.synergyinfosys.android.myblue.adao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.CallRecord;
import net.synergyinfosys.android.myblue.bean.CallStatus;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

public enum CallRecordADao {
	INSTANCE;

	public static final String TAG = "CallRecordUtil";

	private Context mContext;

	private String[] fields = { 
			android.provider.CallLog.Calls._ID, 
			android.provider.CallLog.Calls.NUMBER, 
			android.provider.CallLog.Calls.TYPE, 
			android.provider.CallLog.Calls.DATE };
	
	CallRecordADao() {
	}

	public void initial(Context ctx) {
		this.mContext = ctx;
	}

	public ArrayList<CallRecord> getCallRecord(String number) {
		Log.i(TAG, "getCallRecord");
		Contact contact = ContactDao.getInstance().getContact(number);
		if( contact == null ){
			Log.i(TAG, "no contact found by number:" + number);
			return null;
		}
		long contactId = contact.getId();
		
		Cursor cursor = this.mContext.getContentResolver().query(
				CallLog.Calls.CONTENT_URI, 
				fields, 
				"number = ?", 
				new String[] { number }, 
				CallLog.Calls.DEFAULT_SORT_ORDER);

		ArrayList<CallRecord> list = new ArrayList<CallRecord>();
		while (cursor.moveToNext()) {
			CallRecord c = new CallRecord();
			c.setAndroidId( cursor.getLong( cursor.getColumnIndex("_id") ) );
			c.setContactId( contactId );
			c.setRecordTime( cursor.getLong( cursor.getColumnIndex("date")) );
			c.setStatus( CallStatus.values()[ cursor.getInt(cursor.getColumnIndex("type"))] );
			list.add(c);
		}
		Log.i( TAG, list.size() + " call record gocha by number:" + number);
		Log.i( TAG, list.toString());
		return list;
	}
}
