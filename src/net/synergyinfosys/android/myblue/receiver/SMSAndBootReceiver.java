package net.synergyinfosys.android.myblue.receiver;

import java.util.Date;

import net.synergyinfosys.android.service.LongLiveService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSAndBootReceiver extends BroadcastReceiver {
	public static final String TAG = "SMSReceiver";
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	public static final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "短信事件/开机事件");
		String action = intent.getAction();
		if (SMS_RECEIVED_ACTION.equals(action)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				// 取pdus内容,转换为Object[]
				Object[] pdus = (Object[]) bundle.get("pdus");
				// 解析短信
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < messages.length; i++) {
					byte[] pdu = (byte[]) pdus[i];
					messages[i] = SmsMessage.createFromPdu(pdu);
				}
				// 解析完内容后分析具体参数
				for (SmsMessage msg : messages) {
					// 获取短信内容
					String content = msg.getMessageBody();
					String sender = msg.getOriginatingAddress();
					Date date = new Date(msg.getTimestampMillis());
					Log.i( TAG, sender + "->" + content + "->" + date.toString());
//					if (sender.contains("13601303722")) {
//						SMS sms = new SMS();
//						sms.setAddress(sender);
//						sms.setBody(content);
//						sms.setRead(0);
//						SMSUtil.INSTANCE.addHiddenSMS(sms);
//						// 对于特定的内容,取消广播
//						abortBroadcast();
//					}
				}

			}
		}else if( BOOT_ACTION.equals(action)){
			
		}

		/**
		 * 无论如何，启动我们的longliveservice
		 */
		context.startService(new Intent(context, LongLiveService.class));
	}

}
