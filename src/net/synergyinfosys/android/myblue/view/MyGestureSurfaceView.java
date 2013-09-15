package net.synergyinfosys.android.myblue.view;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.animation.Animation;

public class MyGestureSurfaceView extends SurfaceView implements Callback, Runnable {
	
	public static final String TAG = "MyGestureSurfaceView";
	public static final int BITMAP_WIDTH = 100;
	public static final int BITMAP_HEIGHT = 100;
	public static final int COL_NUM = 3;
	public static final int ROW_NUM = 3;
	
	private static Map<String, Bitmap> mGestureBitmap = null;
	private Canvas mCanvas;
	private SurfaceHolder mSurfaceHolder;
	private Paint mPaint, mBorderPaint, mTextPaint;
	private int mScreenWidth, mScreenHeight;
	private int mUnitWidth, mUnitHeight;
	private Thread mThread;
	
	/**
	 * for surface view, if we wanna register it within XML layout, constructor
	 * with two parameters is a necessary.
	 * 
	 * @param context
	 * @param attrs
	 */
	public MyGestureSurfaceView(Context context, AttributeSet attrs) {
		super(context);
		mGestureBitmap = new TreeMap<String, Bitmap>();

		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.RED);
		
		mBorderPaint = new Paint();
		mBorderPaint.setColor(Color.BLUE);
		mBorderPaint.setStyle(Style.STROKE);
		
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.DKGRAY);
		mTextPaint.setAntiAlias(true);
		
		mThread = new Thread(this);    

		this.setKeepScreenOn(true);// 保持屏幕常亮
	}

	public static void initialGestureBmp(GestureLibrary lib){
		if( mGestureBitmap == null ){
			mGestureBitmap = new TreeMap<String, Bitmap>();
		}
		if ( lib.load()) {
			Object ob[] = lib.getGestureEntries().toArray();
			for (int i = 0; i < ob.length; i++) {
				Gesture gesture = lib.getGestures( (String) ob[i] ).get(0);
				addGestureBmp( (String) ob[i], gesture );
			}
		} 
	}

	public static void addGestureBmp(String name, Gesture gesture) {
		mGestureBitmap.put(name, gesture.toBitmap(BITMAP_WIDTH, BITMAP_HEIGHT, 12, Color.GREEN));
	}
	
	public static void clearGestureBmp() {
		mGestureBitmap.clear();
	}

	private void draw() {
		try {
			mCanvas = mSurfaceHolder.lockCanvas(); // 得到一个canvas实例
			mCanvas.drawColor(Color.WHITE);// 刷屏
			
			int index = 0;
			Iterator<String> keyIter = mGestureBitmap.keySet().iterator();
			while(keyIter.hasNext()){
				String key = keyIter.next();
				Bitmap bmp = mGestureBitmap.get(key);
				drawGestureBitmap(index++, bmp, mCanvas, mPaint);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (mCanvas != null)
				mSurfaceHolder.unlockCanvasAndPost(mCanvas); // 将画好的画布提交
		}
	}
	
	public void drawGestureBitmap(int index, Bitmap bmp, Canvas canvas, Paint paint){
		int y = (int)( (float)index / COL_NUM) * mUnitHeight;
		int x = (int)( index % COL_NUM ) * mUnitWidth;
		Rect rect = new Rect( x, y, x+mUnitWidth, y+mUnitHeight );
		canvas.drawBitmap(bmp, null, rect, paint);
		canvas.drawText("第"+index+"个手势", x+10, y+40, mTextPaint);
		drawBorder(rect, canvas);
	}
	
	private void drawBorder(Rect rect, Canvas canvas){
		canvas.drawRect(rect.left+5, rect.top + 5, rect.right-5, rect.bottom-5, mBorderPaint);
	}

	@Override
	public void startAnimation(Animation animation) {
		super.startAnimation(animation);
	}

	/**
	 * for surface holder
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		mScreenWidth = this.getWidth();
		mScreenHeight = this.getHeight();
		mUnitWidth = mScreenWidth / COL_NUM;
		mUnitHeight = mScreenHeight / ROW_NUM;
		//make it square
		if( mUnitWidth < mUnitHeight ){
			mUnitHeight = mUnitWidth;
		}else{
			mUnitWidth = mUnitHeight;
		}
		if( mThread!= null && !mThread.isAlive() )
			mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}

	/**
	 * for runnable
	 */
	@Override
	public void run() {
		while (true) {
			draw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
