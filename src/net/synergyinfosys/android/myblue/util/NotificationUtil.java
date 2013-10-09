package net.synergyinfosys.android.myblue.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public enum NotificationUtil {

	INSTANCE;

	// flags
	final static public int FLAG_ONGOING_EVENT_AUTO_CANCEL = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT;
	final static public int FLAG_ONGOING_EVENT = Notification.FLAG_ONGOING_EVENT;
	final static public int FLAG_NO_CLEAR = Notification.FLAG_NO_CLEAR;
	final static public int FLAG_AUTO_CANCEL = Notification.FLAG_AUTO_CANCEL;

	private Context mContext = null;
	private NotificationManager mNotificationManager = null;

	public void init(
			Context ctx) {
		mContext = ctx;
		mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	private NotificationUtil() {
	}

	/**
	 * 生成Notification对象
	 */
	public Notification genNotification(
			Context ctx,
			int iconResId,
			String notifyShowText,
			String titleText,
			String contentText,
			Class<?> cls,
			int flag) {

		if (cls == null)
			return null;

		// 控制点击通知后显示内容的类
		final PendingIntent ip = PendingIntent.getActivity(ctx,
				0, // as you wish, no matter
				new Intent(ctx, cls),
				0 // PendingIntent的flag，在update这个通知的时候可以加特别的flag
				);

//		Notification.Builder nb = new Notification.Builder(ctx);
//		Notification n = nb.setSmallIcon(iconResId).setTicker(notifyShowText).setContentTitle(titleText).setContentText(contentText).setContentIntent(ip).build();
		
		Notification oldFashionNotification = new Notification();
		oldFashionNotification.tickerText = notifyShowText;
		oldFashionNotification.contentIntent = ip;
		
		oldFashionNotification.flags = flag;

		return oldFashionNotification;
	}
	
	public void sendNotification( int id,  Notification n ){
		if( mNotificationManager != null )
			mNotificationManager.notify(id, n);
	}

	/**
	 * 取消消息
	 * 
	 * @param c
	 * @param notifyId
	 * @return void
	 */
	public static void cancel(
			Context c,
			int notifyId) {
		((NotificationManager) ((Activity) c).getSystemService(Context.NOTIFICATION_SERVICE)).cancel(notifyId);
	}

}
