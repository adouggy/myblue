package net.synergyinfosys.android.myblue.adapter;

import net.synergyinfosys.android.myblue.R;

import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HomeViewFlowAdapter extends BaseAdapter implements TitleProvider {
	private final int VIEW_COUNT = 3;
	private final String[] names = { "设置", /*"联系人",*/ "通话记录", "短信" };

	private LayoutInflater mInflater;

	public HomeViewFlowAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_COUNT;
	}

	@Override
	public int getCount() {
		return VIEW_COUNT;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int view = getItemViewType(position);
		if (convertView == null) {
			switch (view) {
			case 0:
				convertView = mInflater.inflate(R.layout.activity_home, null);
				break;
//			case 1:
//				convertView = mInflater.inflate(R.layout.activity_contact, null);
//				break;
			case 1:
				convertView = mInflater.inflate(R.layout.activity_call_record, null);
				break;
			case 2:
				convertView = mInflater.inflate(R.layout.activity_sms, null);
			}
		}
		return convertView;
	}

	public String getTitle(int position) {
		return names[position];
	}

}
