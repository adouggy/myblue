package net.synergyinfosys.android.myblue.fragment;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.helper.AboutHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends Fragment  implements ITitle{

	public static final String TAG = "TutorialFragment";
	private static AboutHelper mHelper = null;
//	public static LockStatusHandler mLockStatusHandler = new LockStatusHandler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHelper = new AboutHelper(getActivity());
		mHelper.onCreate( R.layout.fragment_about );
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mHelper.onPostCreate();
		return mHelper.getView();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
//	public static class LockStatusHandler extends Handler {
//		@Override
//		public void handleMessage(Message msg) {
//			LockStatus status = LockStatusService.INSTANCE.isLock();
//			
//			LockStatusService.INSTANCE.doSth(status);
//			
//			if( mHelper != null ){
//				if( status == LockStatus.GESTURE_UNLOCK ){
//					mHelper.setStatus("pin Unlocked (" + GestureLockStatusService.INSTANCE.lockTimeRemainingInSeconds() + ")");
//				}else if( status == LockStatus.BLUETOOTH_UNLOCK ){
//					mHelper.setStatus("Bluetooth Unlocked");
//				}else if( status == LockStatus.WIFI_LOCK ){
//					mHelper.setStatus("Wifi Locked");
//				}else if( status == LockStatus.LOCATION_LOCK ){
//					mHelper.setStatus("Location Locked");
//				}else if( status == LockStatus.GESTURE_LOCK ){
//					mHelper.setStatus("It's Locked");
//				}else {
//					mHelper.setStatus("闹鬼了！");
//				}
//			}
//			
//		}
//	}

	@Override
	public String getTitle() {
		return "关于";
	};
}
