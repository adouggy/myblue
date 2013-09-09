package net.synergyinfosys.android.myblue.adapter;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.util.StringUtil;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SMSListAdapter extends BaseAdapter {
	public static final String TAG = "SMSListAdapter";
	
	private ArrayList<SMS> mSMSList = null;
	private LayoutInflater mInflater = null;
	
	public SMSListAdapter(Context ctx, ArrayList<SMS> list){
		mInflater = LayoutInflater.from(ctx);
		this.mSMSList = list;
	}
	
	@Override
	public int getCount() {
		return this.mSMSList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mSMSList.get(position);
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
			holder.img = (ImageView) convertView.findViewById(R.id.img_sms_icon);
			holder.from = (TextView) convertView.findViewById(R.id.txt_sms_from);
			holder.count = (TextView) convertView.findViewById(R.id.txt_sms_count);
			holder.summary = (TextView) convertView.findViewById(R.id.txt_sms_summary);
			convertView.setTag(holder);
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		SMS sms = this.mSMSList.get(position);
		holder.from.setText( sms.getAddress() );
		holder.summary.setText( StringUtil.INSTACE.shorten( sms.getBody(), 40 ) );
		Log.i(TAG, holder.summary.getText().toString());
//		holder.count.setText("(1)");
		
		return convertView;
	}
	
	static final class ViewHolder{
		ImageView img;
		TextView from;
		TextView count;
		TextView summary;
	}

}
