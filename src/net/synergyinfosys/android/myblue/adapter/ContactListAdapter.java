package net.synergyinfosys.android.myblue.adapter;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListAdapter extends BaseAdapter {
	public static final String TAG = "ContactListAdapter";

	private static ArrayList<Contact> mContactList = null;
	private LayoutInflater mInflater = null;

	public ContactListAdapter(Context ctx, ArrayList<Contact> list) {
		mInflater = LayoutInflater.from(ctx);
		mContactList = list;
	}

	public static void setData(ArrayList<Contact> contactList) {
		mContactList = contactList;
	}
	
	@Override
	public int getCount() {
		return mContactList.size();
	}

	@Override
	public Object getItem(int position) {
		return mContactList.get(position);
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
			convertView = mInflater.inflate(R.layout.contact_list_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.txt_contact_item_name);
			holder.number = (TextView) convertView.findViewById(R.id.txt_contact_item_number);
			holder.hidden = (TextView) convertView.findViewById(R.id.txt_contact_item_about_hidden);
			holder.callMode = (TextView) convertView.findViewById(R.id.txt_contact_item_about_call);
			holder.edit = (ImageView) convertView.findViewById(R.id.btn_contact_item_edit);
			holder.edit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Contact c = (Contact) v.getTag();
//					ContactActivity.editContact(c);
				}
			});
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Contact c = mContactList.get(position);
		holder.name.setText(c.getName());
		holder.number.setText("(" + c.getNumber() + ")");
		holder.hidden.setText("隐藏:" + (c.isHideSMS() ? "sms" : "") + (c.isHideCallRecord() ? " call" : ""));
		holder.callMode.setText("来电模式:" + c.getCallMode().toString());
		holder.edit.setTag(c);
		

		return convertView;
	}

	static final class ViewHolder {
		TextView name;
		TextView number;
		TextView hidden;
		TextView callMode;
		ImageView edit;
	}
}
