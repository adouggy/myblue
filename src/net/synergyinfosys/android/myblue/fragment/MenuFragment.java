package net.synergyinfosys.android.myblue.fragment;

import net.synergyinfosys.android.myblue.HomeSlideActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.ui.cache.SMSCache;
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
import android.widget.Toast;

public class MenuFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());

		adapter.add(new SampleItem("教程", R.drawable.tutorial));
		adapter.add(new SampleItem("联系人", R.drawable.person));
		adapter.add(new SampleItem("蓝牙", R.drawable.bluetooth));
		adapter.add(new SampleItem("Secret Code", R.drawable.pin));
		adapter.add(new SampleItem("通话记录", R.drawable.call));
		adapter.add(new SampleItem("短信", R.drawable.sms));
		adapter.add(new SampleItem("关于", R.drawable.about));
		adapter.add(new SampleItem("Debug", R.drawable.del));
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new TutorialFragment();
			break;
		case 1:
			newContent = new ContactFragment();
			break;
		case 2:
			newContent = new BluetoothFragment();
			break;
		case 3:
			newContent = new SecretCodeFragment();
			break;
		case 4:
			newContent = new CallRecordFragment();
			break;
		case 5:
			if( SMSCache.getInstance().getContactNames().size() == 0 ){
				Toast.makeText(getActivity(), "目前没有联系人", Toast.LENGTH_SHORT).show();
			}else{
				newContent = new SMSFragment();
			}
			break;
		case 6:
			newContent = new AboutFragment();
			break;
		case 7:
			newContent = new DebugFragment();
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
