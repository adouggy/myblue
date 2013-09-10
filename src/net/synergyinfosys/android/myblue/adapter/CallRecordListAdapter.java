package net.synergyinfosys.android.myblue.adapter;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.CallRecord;
import net.synergyinfosys.android.myblue.bean.CallStatus;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CallRecordListAdapter extends BaseAdapter {
	public static final String TAG = "CallRecordListAdapter";

	private static ArrayList<CallRecord> mCallRecordList = null;
	private LayoutInflater mInflater = null;
	
	private RotateAnimation rotateAnimation_in, rotateAnimation_out, rotateAnimation_miss;
	

	public CallRecordListAdapter(Context ctx, ArrayList<CallRecord> list) {
		mInflater = LayoutInflater.from(ctx);
		mCallRecordList = list;
		
		rotateAnimation_in = new RotateAnimation(0, 180,
		        Animation.RELATIVE_TO_SELF, 0.5f,
		        Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation_in.setInterpolator(new LinearInterpolator());
		rotateAnimation_in.setDuration(500);
		rotateAnimation_in.setRepeatCount(0);
		rotateAnimation_in.setFillAfter(true);
		
		rotateAnimation_out = new RotateAnimation(0, 360,
		        Animation.RELATIVE_TO_SELF, 0.5f,
		        Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation_out.setInterpolator(new LinearInterpolator());
		rotateAnimation_out.setDuration(500);
		rotateAnimation_out.setRepeatCount(0);
		rotateAnimation_out.setFillAfter(true);
		
		rotateAnimation_miss = new RotateAnimation(0, 90,
		        Animation.RELATIVE_TO_SELF, 0.5f,
		        Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation_miss.setInterpolator(new LinearInterpolator());
		rotateAnimation_miss.setDuration(500);
		rotateAnimation_miss.setRepeatCount(0);
		rotateAnimation_miss.setFillAfter(true);
	}

	public static void setData(ArrayList<CallRecord> list) {
		mCallRecordList = list;
	}

	@Override
	public int getCount() {
		return mCallRecordList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCallRecordList.get(position);
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
			convertView = mInflater.inflate(R.layout.call_record_list_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.txt_call_record_item_name);
			holder.number = (TextView) convertView.findViewById(R.id.txt_call_record_item_number);
			holder.time = (TextView) convertView.findViewById(R.id.txt_call_record_item_time);
			holder.status = (ImageView) convertView.findViewById(R.id.img_call_record_item_status);
			holder.call = (ImageView) convertView.findViewById(R.id.img_call_record_item_call);
			holder.call.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CallRecord c = mCallRecordList.get(position);
		long contactId = c.getContactId();
		Contact contact = ContactDao.getInstance().getContact(contactId);
		holder.name.setText( contact.getName() );
		holder.number.setText( contact.getNumber() );
		holder.time.setText( c.getRecordTimeStr() );
		
		CallStatus status =  c.getStatus();
		if( status.compareTo( CallStatus.incoming ) ==0 ){
			holder.status.startAnimation( rotateAnimation_in );
			Log.i( TAG, "in" );
		}else if( status.compareTo( CallStatus.outgoing ) ==0 ){
			holder.status.startAnimation( rotateAnimation_out );
			Log.i( TAG, "out" );
		}else{
			holder.status.startAnimation( rotateAnimation_miss );
			Log.i( TAG, "miss" );
		}

		return convertView;
	}

	static final class ViewHolder {
		TextView name;
		TextView number;
		TextView time;
		ImageView status;
		ImageView call;
	}
}
