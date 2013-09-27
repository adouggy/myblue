package net.synergyinfosys.android.myblue.adapter;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.CallRecord;
import net.synergyinfosys.android.myblue.bean.CallStatus;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import net.synergyinfosys.android.myblue.ui.cache.MediaCache;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CallRecordListAdapter extends BaseAdapter {
	public static final String TAG = "CallRecordListAdapter";

	private static ArrayList<CallRecord> mCallRecordList = null;
	private LayoutInflater mInflater = null;
	
	public CallRecordListAdapter(Context ctx, ArrayList<CallRecord> list) {
		mInflater = LayoutInflater.from(ctx);
		mCallRecordList = list;
		
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
		
		if( contact == null ){
			//TODO: no this contact, should do nothing... this is a bad state
			return convertView;
		}
		
		holder.name.setText( contact.getName() );
		holder.number.setText( contact.getNumber() );
		holder.time.setText( c.getRecordTimeStr() );
		
		CallStatus status =  c.getStatus();
		if( status.compareTo( CallStatus.incoming ) ==0 ){
			holder.status.setBackgroundResource(R.drawable.arrow);
			holder.status.startAnimation( MediaCache.getInstance().getAnimationIncoming() );
		}else if( status.compareTo( CallStatus.outgoing ) ==0 ){
			holder.status.setBackgroundResource(R.drawable.arrow);
			holder.status.startAnimation( MediaCache.getInstance().getAnimationOutgoing() );
		}else{
			holder.status.setBackgroundResource(R.drawable.arrow_miss);
			holder.status.startAnimation(  MediaCache.getInstance().getAnimationIncoming() );
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
