package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.util.GestureUtil;
import net.synergyinfosys.android.myblue.util.SMSUtil;
import net.synergyinfosys.android.service.LongLiveService;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener, OnGestureListener  {
	public static final String TAG = "HomeActivity";
	private Button mBtnConfig = null;
	private Button mBtnGesture = null;
	private Button mBtnSMS = null;
	private Button mBtnTest = null;
	
	private GestureOverlayView mGestureView;// 创建一个手写绘图区
	private Gesture mGesture;// 手写实例
	
	public static boolean isLock = true;
	public static boolean isFake = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		this.mBtnConfig = (Button) findViewById(R.id.button_config);
		this.mBtnConfig.setOnClickListener(this);
		
		this.mBtnGesture = (Button) findViewById(R.id.button_gesture);
		this.mBtnGesture.setOnClickListener(this);
		
		this.mBtnSMS = (Button) findViewById(R.id.button_sms);
		this.mBtnSMS.setOnClickListener(this);
		
		this.mBtnTest = (Button) findViewById(R.id.button_test);
		this.mBtnTest.setOnClickListener(this);
		
		mGestureView = (GestureOverlayView) findViewById(R.id.view_password_gesture);
		mGestureView.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
		mGestureView.addOnGestureListener(this);
		
		this.startService( new Intent(this, LongLiveService.class) );
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
		switch (v.getId()) {
		case R.id.button_config:
			Intent intent = new Intent();
			ComponentName cn = new ComponentName(this.getApplicationContext(), ConfigActivity.class);
			intent.setComponent(cn);
			this.startActivity(intent);
			break;
		case R.id.button_gesture:
			Intent intent2 = new Intent();
			ComponentName cn2 = new ComponentName(this.getApplicationContext(), GestureActivity.class);
			intent2.setComponent(cn2);
			this.startActivity(intent2);
			break;
		case R.id.button_sms:
			Intent intent3= new Intent();
			ComponentName cn3 = new ComponentName(this.getApplicationContext(), SMSActivity.class);
			intent3.setComponent(cn3);
			this.startActivity(intent3);
			break;
		case R.id.button_test:
			SMSUtil.INSTANCE.testSMS();
			break;
		}
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
		if (mGesture.getStrokesCount() == 2) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				String guestureName = GestureUtil.INSTACE.matchGesture(mGesture);
				if( guestureName.compareTo( "No.0" ) == 0 ){
					if( isLock ){
						isLock = false;
						isFake = false;
						
						SMSUtil.INSTANCE.hideSMS(false, "10086");
					}
				}else if( guestureName.compareTo( "No.1" ) == 0 ){
					isLock = true;
					isFake = true;
				}else if( guestureName.compareTo( "No.2" ) == 0 ){
					if( !isLock ){
						isLock = true;
						isFake = false;
						
						SMSUtil.INSTANCE.hideSMS(true, "10086");
					}
				}
			}
		}
	}

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
	}
}
