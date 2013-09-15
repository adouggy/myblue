package net.synergyinfosys.android.myblue.adapter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.bean.SMS;
import net.synergyinfosys.android.myblue.service.ContactService;
import net.synergyinfosys.android.myblue.util.StringUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SMSListAdapter extends BaseAdapter {
	public static final String TAG = "SMSListAdapter";

	private static ArrayList<SMS> mSMSList = null;
	private LayoutInflater mInflater = null;
	DateFormat formatter = SimpleDateFormat.getDateTimeInstance();

	public SMSListAdapter(Context ctx, ArrayList<SMS> list) {
		mInflater = LayoutInflater.from(ctx);
		mSMSList = list;
	}
	
	public static void setData( ArrayList<SMS> list ){
		mSMSList = list;
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
			holder.img = (ImageView) convertView.findViewById(R.id.img_sms_icon);
			holder.from = (TextView) convertView.findViewById(R.id.txt_sms_from);
			holder.date = (TextView) convertView.findViewById(R.id.txt_sms_date);
			holder.summary = (TextView) convertView.findViewById(R.id.txt_sms_summary);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SMS sms = mSMSList.get(position);
		Contact c = ContactService.INSTANCE.getContactByNumber(sms.getAddress());
		
		holder.from.setText( sms.getAddress() + (c==null?"":"("+c.getName()+")"));
		holder.summary.setText(StringUtil.INSTACE.shorten(sms.getBody(), 40));
		holder.date.setText(formatter.format(new Date(sms.getDate())));
		

		return convertView;
	}

	static final class ViewHolder {
		ImageView img;
		TextView from;
		TextView date;
		TextView summary;
	}

}
