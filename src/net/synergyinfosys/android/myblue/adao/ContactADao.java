package net.synergyinfosys.android.myblue.adao;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import net.synergyinfosys.android.myblue.util.StringUtil;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Base64;
import android.util.Log;

public enum ContactADao {
	INSTANCE;

	public static final String TAG = "ContactUtil";

	ContactADao() {
	}

	private Context mContext;

	public void initial(Context ctx) {
		this.mContext = ctx;

	}

	/**
	 * 这里的思路是根据姓名查找到联系人， 如果要隐藏，将姓名和手机号码base64，然后更新，这样就看不到联系人了
	 * 目前先用base64作为映射，未来可以加密存储并删除联系人
	 * 
	 * @param username
	 * @param isHide
	 */
	public void hideContact(String username, boolean isHide) {
		if (username == null || username.length() == 0)
			return;

		Cursor cursor = this.mContext.getContentResolver().query(Phone.CONTENT_URI, null, Phone.DISPLAY_NAME + " = '" + username + "'", null, null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				Log.i(TAG, "No. " + i);

				String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
				Log.i(TAG, "Name->" + name);

				String contactId = cursor.getString(cursor.getColumnIndex(Phone.RAW_CONTACT_ID));
				Log.i(TAG, "Id->" + contactId);

				String type = cursor.getString(cursor.getColumnIndex(Phone.MIMETYPE));
				Log.i(TAG, "Type->" + type);

				String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
				Log.i(TAG, "Number->" + number);

				try {
					ContentValues values = new ContentValues();
					if (isHide) {
						byte[] mappedMobile = Base64.encode(number.getBytes("utf-8"), Base64.DEFAULT);
						values.put(Phone.NUMBER, new String(mappedMobile));
					} else {
						String mappedMobile = new String(Base64.decode(number.getBytes(), Base64.DEFAULT));
						values.put(Phone.NUMBER, mappedMobile);
					}

					Log.i(TAG, "base64->" + values.getAsString(Phone.NUMBER));

					int rows = this.mContext.getContentResolver().update(Uri.parse("content://com.android.contacts/data"), values,
							Phone.RAW_CONTACT_ID + " = '" + contactId + "' and " + Phone.MIMETYPE + " ='" + type + "'", null);

					Log.i(TAG, rows + " rows updated");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				cursor.moveToNext();
			}
		}
		cursor.close();
	}

	/**
	 * for storing vCard
	 */
	// String vcardStr = null;
	// String lookupKey =
	// cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
	// Uri uri =
	// Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI,
	// lookupKey);
	//
	// AssetFileDescriptor fd;
	// FileOutputStream fos = null;
	// FileInputStream fis = null;
	// try {
	// fd = this.mActivity.getContentResolver().openAssetFileDescriptor(uri,
	// "r");
	// int len = (int)fd.getDeclaredLength();
	// Log.i(TAG, "length = " + len );
	// fis = new FileInputStream(fd.getFileDescriptor());
	// byte[] buf = new byte[len];
	// fis.read(buf);
	// vcardStr = new String(buf);
	//
	// Log.i(TAG, "read vcard:" + uri.toString() + "-->" + vcardStr);
	// Log.i(TAG,"");
	//
	// String storage_path =
	// Environment.getExternalStorageDirectory().toString() + File.separator +
	// "vcards" + File.separator + fileName;
	// fos = new FileOutputStream(storage_path, false);
	// fos.write(vcardStr.getBytes());
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// } finally {
	// if (fis != null)
	// try {
	// fis.close();
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// if (fos != null)
	// try {
	// fos.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return vcardStr;

	public Bitmap getContactPhotoByNumber(String number) {
		Uri uriNumber2Contacts = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
		Cursor cursorCantacts = mContext.getContentResolver().query(uriNumber2Contacts, null, null, null, null);
		if (cursorCantacts.getCount() > 0) { // 若游标不为0则说明有头像,游标指向第一条记录
			cursorCantacts.moveToFirst();
			Long contactID = cursorCantacts.getLong(cursorCantacts.getColumnIndex("contact_id"));
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(mContext.getContentResolver(), uri);
			Bitmap bmp = BitmapFactory.decodeStream(input);
			return bmp;
		} else {
			return null;
		}
	}

	public Bitmap getFacebookPhoto(String phoneNumber) {
		if( !StringUtil.INSTACE.isNoneBlank(phoneNumber) ){
			return null;
		}
		Uri phoneUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
		Uri photoUri = null;
		ContentResolver cr = mContext.getContentResolver();
		Cursor contact = cr.query(phoneUri, new String[] { ContactsContract.Contacts._ID }, null, null, null);

		if (contact.moveToFirst()) {
			long userId = contact.getLong(contact.getColumnIndex(ContactsContract.Contacts._ID));
			photoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, userId);
		} else {
			Bitmap defaultPhoto = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_report_image);
			return defaultPhoto;
		}
		if (photoUri != null) {
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, photoUri);
			if (input != null) {
				return BitmapFactory.decodeStream(input);
			}
		} else {
			Bitmap defaultPhoto = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_report_image);
			return defaultPhoto;
		}
		Bitmap defaultPhoto = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_report_image);
		return defaultPhoto;
	}
}
