package net.synergyinfosys.android.myblue.dao;

public class DebugDao extends AbstractDBDao {
	public static final String TAG = "DebugDao";

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final DebugDao INSTANCE = new DebugDao();
	}

	public static DebugDao getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private DebugDao() {
		super();
		
	}

	public void executeRawSql(String sql){
		mDBInstance.execSQL(sql);
	}
}
