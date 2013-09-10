package net.synergyinfosys.android.myblue.adao;

import java.io.File;
import java.util.List;
import java.util.Set;

import net.synergyinfosys.android.myblue.util.SDUtil;
import net.synergyinfosys.android.myblue.view.MyGestureSurfaceView;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.util.Log;

public enum GestureADao {
	INSTACE;
	
	private static final String TAG = "GestureUtil";
	private GestureLibrary mGestureLib = null;
	public static final String GESTURE_LIB_NAME = "gestures";
	
	GestureADao(){
		String gesturePath = SDUtil.INSTANCE.getPath("gestures");
		File gestureFile = new File(gesturePath);
		mGestureLib = GestureLibraries.fromFile(gestureFile);
	}
	
	public void initial(){
		MyGestureSurfaceView.initialGestureBmp(mGestureLib);
	}
	
	public void deleteGesture() {
		Object[] gestureNames = getGesturesNames();
		for (int i = 0; i < gestureNames.length; i++) {
			mGestureLib.removeEntry((String) gestureNames[i]);
		}
		mGestureLib.save();
		MyGestureSurfaceView.clearGestureBmp();
	}
	
	public Object[] getGesturesNames() {
		Set<String> set = mGestureLib.getGestureEntries();
		return set.toArray();
	}
	
	public void addMyGesture(String name, Gesture gesture, GestureOverlayView gestureView) {
		// first time, create new file for gestures
		if (!SDUtil.INSTANCE.isFileExistsInRootDir(GestureADao.GESTURE_LIB_NAME)) {
			mGestureLib.addGesture(name, gesture);
			if (mGestureLib.save()) {
				gestureView.clear(true);
				Log.i(TAG, "保存手势成功");
				MyGestureSurfaceView.addGestureBmp(name, gesture);
			} else {
				Log.i(TAG, "手势创建失败");
			}

			return;
		}

		if (!mGestureLib.load()) {// 如果读取失败
			Log.i(TAG, "手势文件读取失败！");
		} else {// 读取成功
			Object ob[] = getGesturesNames();
			boolean gestureExists = false;
			for (int i = 0; i < ob.length; i++) {
				if (((String) ob[i]).equals(name)) {
					gestureExists = true;
				}
			}
			if (gestureExists) {
				mGestureLib.removeEntry(name);
			}

			mGestureLib.addGesture(name, gesture);

			if (mGestureLib.save()) {
				gestureView.clear(true);// 清除笔画
				MyGestureSurfaceView.addGestureBmp(name, gesture);
				Log.i(TAG, "保存手势成功！当前所有手势一共有：" + ob.length + "个");
			} else {
				Log.i(TAG, "保存手势失败！");
			}
		}
	}
	
	public String matchGesture(Gesture gesture) {
		if (mGestureLib.load()) {// 如果读取失败
			List<Prediction> predictions = mGestureLib.recognize(gesture);
			// recognize()的返回结果是一个prediction集合，
			// 包含了所有与gesture相匹配的结果。
			// 从手势库中查询匹配的内容，匹配的结果可能包括多个相似的结果，
			if (!predictions.isEmpty()) {
				Prediction prediction = predictions.get(0);
				// prediction的score属性代表了与手势的相似程度
				// prediction的name代表手势对应的名称
				// prediction的score属性代表了与gesture得相似程度（通常情况下不考虑score小于1的结果）。
				if (prediction.score >= 1) {
					Log.i( TAG, prediction.name + "score > 1" );
					return prediction.name; 
				}
			}
		}
		return "";
	}
}
