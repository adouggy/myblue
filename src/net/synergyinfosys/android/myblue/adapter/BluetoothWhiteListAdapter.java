package net.synergyinfosys.android.myblue.adapter;

import java.util.List;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Bluetooth;
import net.synergyinfosys.android.myblue.helper.BluetoothHelper;
import net.synergyinfosys.android.myblue.service.BluetoothService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BluetoothWhiteListAdapter extends BaseAdapter {
	public static final String TAG = "BluetoothWhiteListAdapter";

	private static List<Bluetooth> mBluetoothWhiteList = null;
	private static LayoutInflater mInflater = null;

	public BluetoothWhiteListAdapter(Context ctx, List<Bluetooth> list) {
		mInflater = LayoutInflater.from(ctx);
		mBluetoothWhiteList = list;
	}

	public static void setData(List<Bluetooth> list) {
		mBluetoothWhiteList = list;
	}

	@Override
	public int getCount() {
		return mBluetoothWhiteList.size();
	}

	@Override
	public Object getItem(int position) {
		return mBluetoothWhiteList.get(position);
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
			convertView = mInflater.inflate(R.layout.bluetooth_white_list_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.txt_bluetooth_whitelist_name);
			holder.others = (TextView) convertView.findViewById(R.id.txt_bluetooth_whitelist_others);
			holder.del = (ImageView) convertView.findViewById(R.id.img_bluetooth_del);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Bluetooth b = mBluetoothWhiteList.get(position);
		holder.name.setText(b.getName());
		holder.others.setText(b.getMac());
		
		holder.del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bluetooth b = mBluetoothWhiteList.get(position);
				BluetoothService.INSTANCE.removeWhiteList( b.getId() );
				BluetoothHelper.refresh();
			}
		});

		return convertView;
	}

	static final class ViewHolder {
		TextView name;
		TextView others;
		ImageView del;
	}
}
