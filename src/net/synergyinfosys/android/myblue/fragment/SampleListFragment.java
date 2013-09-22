package net.synergyinfosys.android.myblue.fragment;

import net.synergyinfosys.android.myblue.HomeSlideActivity;
import net.synergyinfosys.android.myblue.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SampleListFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
//		for (int i = 0; i < 20; i++) {
//			adapter.add(new SampleItem("Sample List", android.R.drawable.ic_menu_search));
//		}
		adapter.add(new SampleItem("联系人", R.drawable.add_contact));
		adapter.add(new SampleItem("蓝牙", R.drawable.bluetooth));
		adapter.add(new SampleItem("通话记录", R.drawable.call));
		adapter.add(new SampleItem("短信", R.drawable.sms));
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new ContactFragment();
			break;
		case 1:
			newContent = new BluetoothFragment();
			break;
		case 2:
			newContent = new CallRecordFragment();
			break;
		case 3:
			newContent = new SMSFragment();
			break;
		}
		if (newContent != null)
			((HomeSlideActivity)getActivity()).switchContent(newContent);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_list_row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
}
