package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.bean.Phone;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import net.synergyinfosys.android.myblue.dao.PhoneDao;
import net.synergyinfosys.android.myblue.util.ContactUtil;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

public enum ContactService {

	INSTANCE;

	public static final String TAG = "ContactService";
	
	ContactService() {

	}

	/**
	 * retrieve a contact from local database
	 * 
	 * @param number
	 * @return
	 */
	public Contact getContactByNumber(
			String number) {
		ArrayList<Contact> list = ContactDao.getInstance().getContactAll();

		for (Contact c : list) {
			if (PhoneNumberUtils.compare(c.getNumber(),
					number)) {
				return c;
			}
		}

		return null;
	}

	/**
	 * get all contact from local database
	 * @return
	 */
	public ArrayList<Contact> getAllSecretContacts() {
		return ContactDao.getInstance().getContactAll();
	}

	/**
	 * retrieve all phone numbers from android
	 * store in local database
	 * delete from android
	 * 
	 * @param c, contactId is a necessary
	 */
	public void hideContactPhoneAccount(
			Contact c) {
		Log.i( TAG, "hideContactPhoneAccount for contact" );
		if( c==null || c.getContactId() < 0 )
			return;
		Log.d( TAG, c.toString() );
		
		//get all phone
		ArrayList<Phone> list = ContactUtil.INSTANCE.getPhoneAccountGenericData(/*c.getContactId(),*/ c.getLookupKey());
		Log.d( TAG, "got phone account count:" + list.size() );
		
		//store in local database
		for (Phone p : list) {
			long id = PhoneDao.getInstance().insert(p);
			Log.d(TAG,
					"inserted:" + id);
			
			//delete from android
			if( id > 0 ){
				Log.d( TAG, "delete from android" );
				ContactUtil.INSTANCE.deletePhoneAccountGenericData(p.getRawContactId());
			}
		}
	}
	
	/**
	 * restore to android
	 * delete from local database
	 * 
	 * @param c, lookup_key, contact name is necessary
	 */
	public void restoreContact( Contact c ){
		Log.i( TAG, "restoreContact" );
		if( c == null || c.getLookupKey()==null || c.getName() == null )
			return;
		
		Log.d(TAG, c.toString());
		
		//get all hide phone
		ArrayList<Phone> list = PhoneDao.getInstance().getByLookupKey(c.getLookupKey());
		Log.d(TAG, "phone account count:" + list.size());
		
		//restore to android
		Log.d(TAG, "restore to android");
		ContactUtil.INSTANCE.restorePhoneAccountGenericDate(c.getName(), list);
		
		//delete from local database
		Log.d(TAG, "remove from local database");
		PhoneDao.getInstance().removeByLookupKey(c.getLookupKey());
	}

}
