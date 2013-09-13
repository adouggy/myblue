package net.synergyinfosys.android.myblue.adapter;

import java.util.List;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.WifiActivity;
import net.synergyinfosys.android.myblue.bean.Wifi;
import net.synergyinfosys.android.myblue.service.WifiService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WifiNearListAdapter extends BaseAdapter {
	public static final String TAG = "WifiNearListAdapter";

	private static List<Wifi> mWifiList = null;
	private static LayoutInflater mInflater = null;

	public WifiNearListAdapter(Context ctx, List<Wifi> list) {
		mInflater = LayoutInflater.from(ctx);
		mWifiList = list;
	}

	public static void setData(List<Wifi> list) {
		mWifiList = list;
	}

	@Override
	public int getCount() {
		return mWifiList.size();
	}

	@Override
	public Object getItem(int position) {
		return mWifiList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.wifi_list_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.txt_wifi_name);
			holder.others = (TextView) convertView.findViewById(R.id.txt_wifi_others);
			holder.add = (ImageView) convertView.findViewById(R.id.img_wifi_add);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Wifi wifi = mWifiList.get(position);
		holder.name.setText(wifi.getSsid());
		holder.others.setText(wifi.getBssid());
		holder.add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Wifi wifi = mWifiList.get(position);
				WifiService.INSTANCE.addBlackList(wifi);
				WifiActivity.refresh();
			}
		});

		return convertView;
	}

	static final class ViewHolder {
		TextView name;
		TextView others;
		ImageView add;
	}
}
