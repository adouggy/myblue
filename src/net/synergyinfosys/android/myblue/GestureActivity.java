package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.adao.GestureADao;
import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class GestureActivity extends Activity implements OnGestureListener {

	private static final String TAG = "GestureActivity";
	
	private GestureOverlayView mGestureView;// 创建一个手写绘图区
	private Gesture mGesture;// 手写实例

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gesture);

		mGestureView = (GestureOverlayView) findViewById(R.id.view_gesture);
		mGestureView.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);

		mGestureView.addOnGestureListener(this);
		GestureADao.INSTACE.initial();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.gesture, menu);
		return true;
	}

	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {

	}

	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "gesture cancelled");
	}

	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "gesture ended");
		mGesture = overlay.getGesture();// 从绘图区取出形成的手势
		if (mGesture.getStrokesCount() == 2) {// 我判定当用户用了两笔划
			// (强调：如果一开始设置手势笔画类型是单一笔画，那你这里始终得到的只是1！)
			if (event.getAction() == MotionEvent.ACTION_UP) {// 判定第两笔划离开屏幕
				Log.i(TAG, "准备处理手势...");
				Object[] gestureNames = GestureADao.INSTACE.getGesturesNames();
				int index = gestureNames.length;
				GestureADao.INSTACE.addMyGesture("No." + index, mGesture, this.mGestureView);

				if (gestureNames.length >= 9) {
					GestureADao.INSTACE.deleteGesture();
				}
			}
		} else {
			Log.i(TAG, "请您在紧凑的时间内用两笔划来完成一个手势");
		}
	}

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "gesture started");
	}
}
