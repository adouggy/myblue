package net.synergyinfosys.android.myblue.util;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.SMS;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public enum SMSUtil {
	INSTANCE;

	public static final String TAG = "SMSUtil";

	private Context mContext;

	SMSUtil() {
	}

	public void initial(Context ctx) {
		this.mContext = ctx;
	}

	public void hideSMS(boolean isHide, String number) {
		if (isHide) {
			// 读取收件箱中指定号码的短信，并且是已读
			Cursor cursor = this.mContext.getContentResolver().query(
					Uri.parse("content://sms/inbox"), 
					new String[] { "_id", "address", "body", "read", "date" }, " address=? and read=? ", 
					new String[] { number, 1+"" },
					"date desc");
			if (cursor != null) {
				while (cursor.moveToNext()) {
					Log.d(TAG, "Msg:" + cursor.getInt(0) + "-->" + cursor.getString(2));
					long id = DBUtil.INSTANCE.insertSMS(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
					// delete
					int smsId = cursor.getInt(0);
					//TODO: 再做一个读取校验，并且处理并发问题
					this.mContext.getContentResolver().delete(Uri.parse("content://sms/" + smsId), null, null);
				}
			}
			cursor.close();
		} else {
			//TODO: 这里也要做校验，防止丢数据
			ArrayList<SMS> list = DBUtil.INSTANCE.getSMSAll();
			for (SMS sms : list) {
				addSMS( sms );
			}
			DBUtil.INSTANCE.removeSMSAll();
		}
	}
	
	public void addSMS( SMS sms ){
		ContentValues values = new ContentValues();
		values.put("address", sms.getAddress());
		values.put("body", sms.getBody());
		values.put("type", 1);// 1 for inbox, 2 for out
		values.put("read", sms.getRead());
		values.put("date", sms.getDate());
		Uri inserted = this.mContext.getContentResolver().insert(Uri.parse("content://sms"), values);
		Log.i(TAG, inserted.toString());
	}
	
	public void addHiddenSMS( SMS sms ){
		DBUtil.INSTANCE.insertSMS(sms.getAddress(), sms.getBody(), sms.getRead(), sms.getDate());
	}
	
	public ArrayList<SMS> getHiddenSMS(){
		ArrayList<SMS> list = DBUtil.INSTANCE.getSMSAll();
		return list;
	}
	
	public void testSMS(){
		SMS sms = new SMS();
		sms.setAddress("10086");
		sms.setBody("中华人民共和国万岁，世界人民大团结万岁万岁万万岁。");
		sms.setType(1);
		sms.setRead(0);
		sms.setDate(System.currentTimeMillis());
		addSMS( sms );
	}
}
