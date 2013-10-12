package net.synergyinfosys.android.myblue.util;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.bean.Phone;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Profile;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

public enum ContactUtil {
	INSTANCE;

	public static final String TAG = "ContactUtil";

	private Context mContext = null;

	ContactUtil() {

	}

	public void initial(
			Context ctx) {
		this.mContext = ctx;
		// getAllPhonesWithHideContactFilter(ContactDao.getInstance().getContactAll());
	}

	public void getUserProfile() {
		// Sets the columns to retrieve for the user profile
		String[] mProjection = new String[] { Profile._ID, Profile.DISPLAY_NAME_PRIMARY, Profile.LOOKUP_KEY, Profile.PHOTO_THUMBNAIL_URI };

		// Retrieves the profile from the Contacts Provider
		Cursor c = mContext.getContentResolver().query(Profile.CONTENT_URI,
				mProjection,
				null,
				null,
				null);

		if (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex(Profile._ID));
			String name = c.getString(c.getColumnIndex(Profile.DISPLAY_NAME_PRIMARY));
			String lookupKey = c.getString(c.getColumnIndex(Profile.LOOKUP_KEY));

			Log.i(TAG,
					"profile:" + id + "/" + name + "/" + lookupKey);
		}
	}

	/**
	 * retrieve a contact from contact book, record its lookup_key, ID,
	 * phoneNumber, etc.
	 * 
	 * @param uri
	 *            , use intent to select a contact uri from android
	 * @return
	 */
	public Contact getContactByUri(
			Uri uri) {
		ContentResolver cr = mContext.getContentResolver();
		Cursor cur = cr.query(uri,
				null,
				null,
				null,
				null);
		Log.i(TAG,
				"getContactByUri, uri=" + uri + ", count=" + cur.getCount());
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				Contact c = new Contact();
				Long id = cur.getLong(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				c.setName(name);
				c.setLookupKey(lookupKey);
				c.setSelected(true);
				c.setContactId(id);

				Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
						new String[] { id + "" },
						null);

				while (pCur.moveToNext()) {
					String data1 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
					c.setNumber(data1);
				}

				return c;
			}
		} // end of if
		cur.close();

		return null;
	}

	/**
	 * 
	 * @param rawContactId
	 */
	public void deletePhoneAccountGenericData(
			long rawContactId) {

		ContentResolver cr = mContext.getContentResolver();

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(RawContacts.CONTENT_URI,
				rawContactId)).build());
		try {
			cr.applyBatch(ContactsContract.AUTHORITY,
					ops);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * create new raw contact account within android
	 * 
	 * @param contactName, conact's name which new raw contact will attach to 
	 * @param list, phone accounts, should retrieve from local database
	 */
	public void restorePhoneAccountGenericDate(
			String contactName, ArrayList<Phone> list) {
		Log.i( TAG,  "restorePhoneAccountGenericDate");
		ContentResolver cr = mContext.getContentResolver();
		Log.d(TAG,
				"list->" + list.toString());

		for (Phone p : list) {
			// 创建空的 content values
			ContentValues values = new ContentValues();
			// 向rawContacts插入空值,获取rawContactId
			Uri rawContactUri = cr.insert(RawContacts.CONTENT_URI,
					values);
			long rawContactId = ContentUris.parseId(rawContactUri);
			Log.i(TAG,
					"new raw contactId:" + rawContactId);

			values.clear();
			values.put(Data.RAW_CONTACT_ID,
					rawContactId);
			// 设置内容类型
			values.put(Data.MIMETYPE,
					StructuredName.CONTENT_ITEM_TYPE);
			// 设置联系人的名字
			values.put(StructuredName.GIVEN_NAME,
					contactName);
			// 向联系人Uri添加联系人名字
			cr.insert(android.provider.ContactsContract.Data.CONTENT_URI,
					values);

			values.clear();
			values.put(Data.RAW_CONTACT_ID,
					rawContactId);

			values.put(Data.MIMETYPE,
					ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
			values.put("data1",
					p.getData1());
			values.put("data2",
					p.getData2());
			values.put("data3",
					p.getData3());
			values.put("data4",
					p.getData4());
			values.put("data5",
					p.getData5());
			values.put("data6",
					p.getData6());
			values.put("data7",
					p.getData7());
			values.put("data8",
					p.getData8());
			values.put("data9",
					p.getData9());
			values.put("data10",
					p.getData10());
			values.put("data11",
					p.getData11());
			values.put("data12",
					p.getData12());
			values.put("data13",
					p.getData13());
			values.put("data14",
					p.getData14());
			values.put("data15",
					p.getData15());
			cr.insert(android.provider.ContactsContract.Data.CONTENT_URI,
					values);

		}
	}
	
	
	public Contact getContactByLookupKey(String lookupKey){
		Uri lookupUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
	    Uri res = ContactsContract.Contacts.lookupContact(mContext.getContentResolver(), lookupUri);
	    return getContactByUri( res );
	}

	/**
	 * 
	 * To make information complete, we retrieve all generic data
	 * 
	 * @param contactId
	 *            , android contact ID.
	 * @return
	 */
	public ArrayList<Phone> getPhoneAccountGenericData(
			/*long contactId, */String lookupKey) {

		ContentResolver cr = mContext.getContentResolver();
		
		Contact c = getContactByLookupKey( lookupKey );
		
		
		ArrayList<Phone> list = new ArrayList<Phone>();
		Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
				new String[] { c.getContactId() + "" },
				null);

		while (pCur.moveToNext()) {
			long rawContactId = pCur.getLong(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID));
			String data1 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
			String data2 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2));
			String data3 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA3));
			String data4 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA4));
			String data5 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA5));
			String data6 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA6));
			String data7 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA7));
			String data8 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA8));
			String data9 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA9));
			String data10 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA10));
			String data11 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA11));
			String data12 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA12));
			String data13 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA13));
			String data14 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA14));
			String data15 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA15));

			Phone p = new Phone();
			p.setLookupKey(lookupKey);
			p.setRawContactId(rawContactId);
			p.setData1(data1);
			p.setData2(data2);
			p.setData3(data3);
			p.setData4(data4);
			p.setData5(data5);
			p.setData6(data6);
			p.setData7(data7);
			p.setData8(data8);
			p.setData9(data9);
			p.setData10(data10);
			p.setData11(data11);
			p.setData12(data12);
			p.setData13(data13);
			p.setData14(data14);
			p.setData15(data15);

			list.add(p);
		}
		pCur.close();

		return list;
	}

}
