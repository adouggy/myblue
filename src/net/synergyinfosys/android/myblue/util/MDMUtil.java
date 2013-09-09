package net.synergyinfosys.android.myblue.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.synergyinfosys.android.myblue.command.ICommand;
import net.synergyinfosys.android.myblue.receiver.MyDeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.util.Log;

public enum MDMUtil {
	INSTANCE;

	public static final String TAG = "MDMUtil";
	public static final String mdmCommandTag = "commandType";
	public static final String mdmCommandId = "commandId";

	public static final String tag = "MDMUtil";

	private DevicePolicyManager mDPM = null;
	public Context mContext;
	public boolean active = false;
	private ComponentName mDeviceReceiver = null;
	private List<ICommand> mCommands = null;
	private WifiManager mWifi = null;

	private PackageManager mPM = null;

	MDMUtil() {
		mCommands = new ArrayList<ICommand>();
	}

	public void initial(Context ctx) {
		Log.d(tag, "开始获取admin权限");
		this.mContext = ctx;
		mDPM = (DevicePolicyManager) this.mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);

		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		mDeviceReceiver = new ComponentName(this.mContext, MyDeviceAdminReceiver.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceReceiver);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "如需启用MDM请选择同意...");
//		act.startActivityForResult(intent, 1);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.mContext.startActivity(intent);
		active = isActive();
		Log.d(tag, "Admin权限获取:" + active);

		mWifi = (WifiManager) this.mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

		mPM = this.mContext.getApplicationContext().getPackageManager();
	}

	public PackageManager getPackageManager() {
		return this.mPM;
	}

	public WifiManager getWifiManager() {
		return this.mWifi;
	}

	public void log(String log) {
		Log.d(tag, log);
	}

	public DevicePolicyManager getPolicyManager() {
		return this.mDPM;
	}

	public ComponentName getReceiverComponent() {
		return this.mDeviceReceiver;
	}

	public boolean isActive() {
		return mDPM.isAdminActive(mDeviceReceiver);
	}

	synchronized public void addCommand(ICommand cmd) {
		this.mCommands.add(cmd);
	}

	synchronized public void clearCommand() {
		this.mCommands.clear();
	}

	synchronized public void runAll() {
		for (ICommand cmd : this.mCommands) {
			cmd.execute();
		}
	}

	/**
	 * 把相片隐藏起来
	 * 
	 * @param isHide
	 */
	public void hidePhoto(boolean isHide) {
		// Cursor cursor = this.mActivity.getContentResolver().query(
		// Media.EXTERNAL_CONTENT_URI,
		// null,
		// Media.DISPLAY_NAME + " like 'IMG_%'",
		// null,
		// null);
		//
		// Log.i( TAG, Media.EXTERNAL_CONTENT_URI.toString() );
		//
		// while( cursor.moveToNext() ){
		// String name = cursor.getString(
		// cursor.getColumnIndex(Media.DISPLAY_NAME) );
		// String id = cursor.getString( cursor.getColumnIndex(Media._ID) );
		// Log.i( TAG, "find Image->" + name + "(" + id + ")");
		//
		// ContentValues values = new ContentValues();
		// if( isHide && !name.endsWith("_hide")){
		// name = name + "_hide";
		// Log.i( TAG, "-->" + name );
		//
		// values.put( Media.DISPLAY_NAME , name);
		// this.mActivity.getContentResolver().update(
		// Media.EXTERNAL_CONTENT_URI, values, Media._ID + " = '" + id + "'" ,
		// null);
		// }else if( !isHide && name.endsWith("_hide") ){
		// name = name.substring(0, name.length()-5);
		// Log.i( TAG, "-->" + name );
		//
		// values.put( Media.DISPLAY_NAME , name);
		// this.mActivity.getContentResolver().update(
		// Media.EXTERNAL_CONTENT_URI, values, Media._ID + " = '" + id + "'" ,
		// null);
		// }
		// }

		// 还是用简单粗暴最有效的方法吧
		// String storage_path =
		// Environment.getExternalStorageDirectory().toString() + File.separator
		// +"DCIM" + File.separator + "Camera";
		// String hide_path = storage_path + File.separator + ".nomedia";
		// Log.i( TAG, "storage:" + storage_path );
		// File f = new File( storage_path );
		// if( f.exists() && f.isDirectory() ){
		// if( isHide ){
		// File tagFile = new File( hide_path );
		// boolean res = tagFile.mkdir();
		// Log.i( TAG, "create .nomedia result:" + res);
		// }else{
		// File tagFile = new File( hide_path );
		// boolean res = tagFile.delete();
		// Log.i( TAG, "delete .nomedia result:" + res);
		// }
		// }
	}

	public void hideIcon(boolean isHide, String activityName) {
		Log.i(TAG, "Prepare to hide:" + activityName);
		this.getPackageManager().setComponentEnabledSetting(new ComponentName(this.mContext, activityName),
				isHide ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		Log.i(TAG, "done.");
	}

	/**
	 * PackageManager介绍： 本类API是对所有基于加载信息的数据结构的封装，包括以下功能： 安装，卸载应用
	 * 查询permission相关信息 查询Application相关
	 * 信息(application，activity，receiver，service，provider及相应属性等） 查询已安装应用
	 * 增加，删除permission 清除用户数据、缓存，代码段等 非查询相关的API需要特定的权限。 主要包含了，安装在当前设备上的应用包的相关信息
	 * 如下：获取已经安装的应用程序的信息
	 */
	public HashMap<String, String> installPackagesInfo() {
		// 获取packageManager对象
		// PackageManager packageManager = this.getPackageManager();

		/*
		 * getInstalledApplications 返回当前设备上安装的应用包集合
		 * ApplicationInfo对应着androidManifest
		 * .xml中的application标签。通过它可以获取该application对应的信息
		 */
		List<ApplicationInfo> applicationInfos = this.getPackageManager().getInstalledApplications(0);
		HashMap<String, String> resultMap = new HashMap<String, String>();
		Iterator<ApplicationInfo> iterator = applicationInfos.iterator();
		while (iterator.hasNext()) {
			ApplicationInfo applicationInfo = iterator.next();
			String className = applicationInfo.className;
			// String packageName = applicationInfo.packageName;// 包名
			String packageLabel = this.getPackageManager().getApplicationLabel(applicationInfo).toString();// 获取label
			resultMap.put(packageLabel, /* packageName */className);
		}

		return resultMap;
	}

}
