package net.synergyinfosys.android.myblue.adao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.SMS;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public enum SMSADao {
	INSTANCE;

	public static final String TAG = "SMSUtil";

	private Context mContext;

	SMSADao() {
	}

	public void initial(
			Context ctx) {
		this.mContext = ctx;
	}

	public ArrayList<SMS> getSMS(
			String number) {
		ArrayList<SMS> list = new ArrayList<SMS>();
		int thread_id = -1;

		// 先通过一条短信获取到对应的thread的id
		Cursor cursor = this.mContext.getContentResolver().query(Uri.parse("content://sms/"),
				new String[] { "*" },
				" address=?",
				new String[] { number },
				"date desc");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				thread_id = cursor.getInt(cursor.getColumnIndex("thread_id")); // get
																				// the
																				// thread_id
				break;
			}
		}
		cursor.close();

		if (thread_id == -1)
			return list;

		// 再把对话中的所有短信都取到
		cursor = this.mContext.getContentResolver().query(Uri.parse("content://sms"),
				new String[] { "*" },
				" thread_id=?",
				new String[] { thread_id + "" },
				"date desc");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				SMS sms = new SMS();
				sms.setAndroidId(cursor.getLong(cursor.getColumnIndex("_id")));
				sms.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				sms.setBody(cursor.getString(cursor.getColumnIndex("body")));
				sms.setRead(cursor.getInt(cursor.getColumnIndex("read")));
				sms.setDate(cursor.getLong(cursor.getColumnIndex("date")));
				sms.setType(cursor.getInt(cursor.getColumnIndex("type")));
				list.add(sms);

				Log.i(TAG,
						sms.toString());
			}
		}
		cursor.close();

		return list;
	}

	public int deleteSMS(
			long id) {
		return this.mContext.getContentResolver().delete(Uri.parse("content://sms/" + id),
				null,
				null);
	}

	public void addSMS(
			SMS sms) {
		ContentValues values = new ContentValues();
		values.put("address",
				sms.getAddress());
		values.put("body",
				sms.getBody());
		values.put("type",
				sms.getType());
		values.put("read",
				sms.getRead());
		values.put("date",
				sms.getDate());
		Uri inserted = this.mContext.getContentResolver().insert(Uri.parse("content://sms"),
				values);
		Log.i(TAG,
				inserted.toString());
	}

	public ArrayList<SMS> getFakeSMS() {
		ArrayList<SMS> list = new ArrayList<SMS>();

		SMS sms1 = new SMS();
		sms1.setDate(System.currentTimeMillis());
		sms1.setAddress("18888888");
		sms1.setBody("我勒个去！恭喜你中奖了");
		sms1.setRead(0);

		SMS sms2 = new SMS();
		sms2.setDate(System.currentTimeMillis());
		sms2.setAddress("1860");
		sms2.setBody("号外号外，外星人入侵");
		sms2.setRead(0);

		list.add(sms1);
		list.add(sms2);
		return list;
	}

	public void testSMS() {
		SMS sms = new SMS();
		sms.setAddress("10086");
		sms.setBody("中华人民共和国万岁，世界人民大团结万岁万岁万万岁。");
		sms.setType(1);
		sms.setRead(0);
		sms.setDate(System.currentTimeMillis());
		addSMS(sms);
	}
}
