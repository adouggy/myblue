package net.synergyinfosys.android.myblue.ui.cache;

import java.util.List;

import net.synergyinfosys.android.myblue.bean.CallRecord;
import net.synergyinfosys.android.myblue.dao.CallRecordDao;
import android.content.Context;

public class CallRecordCache implements IUIDataCache {

	public static final String TAG = "CallRecordCache";

	private List<CallRecord> mCallRecords = null;
	private Context mContext = null;

	private static class SingletonHolder {
		public static final CallRecordCache INSTANCE = new CallRecordCache();
	}

	public static CallRecordCache getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public void initialData(
			Context ctx) {
		mContext = ctx;
		mCallRecords = CallRecordDao.getInstance().getAll();
	}

	public List<CallRecord> getCallRecords() {
		return this.mCallRecords;
	}

	@Override
	public void reload() {
		if (mContext != null) {
			initialData(mContext);
		}
	}
}
