package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.adao.GestureADao;
import net.synergyinfosys.android.myblue.androidservice.LongLiveService;
import net.synergyinfosys.android.myblue.service.GestureLockStatusService;
import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnClickListener, OnGestureListener {
	public static final String TAG = "HomeActivity";

	public static TextView mTxtHello;

	private GestureOverlayView mGestureView;
	private Gesture mGesture;
	public static LockStatusHandler mLockStatusHandler = new LockStatusHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		mTxtHello = (TextView) findViewById(R.id.txt_sms_hello);
		mGestureView = (GestureOverlayView) findViewById(R.id.view_password_gesture);
		
		mGestureView.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
		mGestureView.addOnGestureListener(this);

		this.startService(new Intent(this, LongLiveService.class));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
	}

	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
	}

	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		mGesture = overlay.getGesture();
		Log.i( TAG, "got a gesture" );
		if (mGesture.getStrokesCount() == 2) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				String guestureName = GestureADao.INSTACE.matchGesture(mGesture);
				if (guestureName.compareTo("No.0") == 0) {
					// do gesture lock
					Log.i( TAG, "unlock" );
					GestureLockStatusService.INSTANCE.unlock();
				} else if (guestureName.compareTo("No.2") == 0) {
					// do gesture unlock
					Log.i( TAG, "lock" );
					GestureLockStatusService.INSTANCE.lock();
				}
			}
		}
	}

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
	}

	public static class LockStatusHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			boolean isGestureLock = GestureLockStatusService.INSTANCE.isLock();
			mTxtHello.setText("GestureLock:" + isGestureLock + "(" + GestureLockStatusService.INSTANCE.lockTimeRemainingInSeconds() + ")");
		}
	};
}
