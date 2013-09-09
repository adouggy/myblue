package net.synergyinfosys.android.myblue.util;

import java.io.UnsupportedEncodingException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Base64;
import android.util.Log;

public enum ContactUtil {
	INSTANCE;

	public static final String TAG = "ContactUtil";
	
	ContactUtil() {
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
}
