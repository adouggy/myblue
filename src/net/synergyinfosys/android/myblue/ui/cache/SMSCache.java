package net.synergyinfosys.android.myblue.ui.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import net.synergyinfosys.android.myblue.dao.SMSDao;
import android.content.Context;
import android.telephony.PhoneNumberUtils;

public class SMSCache implements IUIDataCache {
	
	public static final String TAG = "SMSCache";
	
	private List<String> mContactList = null;
	private HashMap<String, List<SMS>> mSMSMap = null;
	private Context mContext = null;

	private static class SingletonHolder {
		public static final SMSCache INSTANCE = new SMSCache();
	}

	public static SMSCache getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	@Override
	public void initialData(Context ctx) {
		mContext = ctx;
		mContactList = new ArrayList<String>();
		mSMSMap = new HashMap<String, List<SMS>>();
		
		ArrayList<SMS> smsAllList = SMSDao.getInstance().getAll();
		
		ArrayList<Contact> list = ContactDao.getInstance().getContactAll();
		for( Contact c : list ){
			mContactList.add( c.getName() );
			
			ArrayList<SMS> smsList = new ArrayList<SMS>();
			for( SMS s : smsAllList ){
				if( PhoneNumberUtils.compare(s.getAddress(), c.getNumber()) ){
					smsList.add(s);
				}
			}
			
			mSMSMap.put(c.getName(), smsList);
		}
	}
	
	public List<String> getContactNames(){
		return mContactList;
	}
	
	public Map<String, List<SMS>> getAllSMS(){
		return mSMSMap;
	}

	@Override
	public void reload() {
		if( mContext != null ){
			initialData(mContext);
		}
	}
}
