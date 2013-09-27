package net.synergyinfosys.android.myblue.adapter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.service.ContactService;
import net.synergyinfosys.android.myblue.ui.cache.MediaCache;
import net.synergyinfosys.android.myblue.ui.cache.SMSCache;
import net.synergyinfosys.android.myblue.util.StringUtil;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SMSListAdapter extends BaseAdapter {
	public static final String TAG = "SMSListAdapter";

	private String mContactName = null;
	private List<SMS> mSMSList = null;
	private LayoutInflater mInflater = null;
	private DateFormat formatter = SimpleDateFormat.getDateTimeInstance();

	public SMSListAdapter(Context ctx, String contactName) {
		mInflater = LayoutInflater.from(ctx);
		mContactName  = contactName;
		mSMSList = SMSCache.getInstance().getAllSMS().get(mContactName);
		
		Log.i( TAG, mSMSList.toString() );
	}
	
	@Override
	public int getCount() {
		return mSMSList.size();
	}

	@Override
	public Object getItem(int position) {
		return mSMSList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.sms_list_item, null);
			holder.friend = (ImageView) convertView.findViewById(R.id.img_sms_friend);
			holder.me = (ImageView) convertView.findViewById(R.id.img_sms_me);
			holder.from = (TextView) convertView.findViewById(R.id.txt_sms_from);
			holder.date = (TextView) convertView.findViewById(R.id.txt_sms_date);
			holder.summary = (TextView) convertView.findViewById(R.id.txt_sms_summary);
			holder.commentDlg = (LinearLayout) convertView.findViewById(R.id.layout_comment);
			holder.commentDlgInner = (LinearLayout) convertView.findViewById(R.id.layout_comment_inner);
			holder.commentDlgMiddle = (LinearLayout) convertView.findViewById(R.id.layout_comment_middle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SMS sms = mSMSList.get(position);
		Contact c = ContactService.INSTANCE.getContactByNumber(sms.getAddress());
		
		int gravity = -1;
		
		if( sms.getType() == 1 ){
			//in box
			holder.from.setText( sms.getAddress() + (c==null?"":"(from "+c.getName()+")"));
			gravity = Gravity.LEFT;
			holder.commentDlgInner.setBackgroundResource(R.drawable.comment_from);
			holder.friend.setBackground(MediaCache.getInstance().getPhotoMap().get(mContactName));
			holder.friend.setVisibility(View.VISIBLE);
			holder.me.setVisibility(View.GONE);
		}else if( sms.getType() == 2 ){
			//out box
			holder.from.setText( sms.getAddress() + (c==null?"":"(to "+c.getName()+")"));
			gravity = Gravity.RIGHT;
			holder.commentDlgInner.setBackgroundResource(R.drawable.comment);
			holder.me.setBackground(MediaCache.getInstance().getMyPhoto());
			holder.me.setVisibility(View.VISIBLE);
			holder.friend.setVisibility(View.GONE);
		}else{
			//error
			holder.from.setText( sms.getAddress() + (c==null?"":"(?? "+c.getName()+")"));
			
			holder.friend.setVisibility(View.GONE);
			holder.me.setVisibility(View.GONE);			
		}
		
		holder.summary.setText(StringUtil.INSTACE.shorten(sms.getBody(), 40));
		holder.date.setText(formatter.format(new Date(sms.getDate())));
		
		holder.commentDlg.setGravity(gravity);
		holder.commentDlgMiddle.setGravity(gravity);
		holder.date.setGravity(gravity);
		holder.date.setTextAlignment(gravity);
		holder.from.setVisibility(View.GONE);
		return convertView;
	}

	static final class ViewHolder {
		ImageView friend;
		ImageView me;
		TextView from;
		TextView date;
		TextView summary;
		LinearLayout commentDlgMiddle;
		LinearLayout commentDlg;
		LinearLayout commentDlgInner;
	}

}
