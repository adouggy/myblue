package net.synergyinfosys.android.myblue.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Bluetooth;
import net.synergyinfosys.android.myblue.helper.BluetoothHelper;
import net.synergyinfosys.android.myblue.service.BluetoothService;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BluetoothNearListAdapter extends BaseAdapter {
	public static final String TAG = "BluetoothNearListAdapter";

	private static List<BluetoothDevice> mBluetoothList = new ArrayList<BluetoothDevice>();
	private static LayoutInflater mInflater = null;
	private static HashMap<String, String> mAddressToExtraInfoMap = new HashMap<String, String>();

	public BluetoothNearListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
	}

	public static void addDevice(BluetoothDevice device, String extraInfo) {
		if( device == null || device.getAddress() == null )
			return ;
		
		mAddressToExtraInfoMap.put(device.getAddress(), extraInfo);
		
		boolean alreadyAdd = false;
		for( BluetoothDevice d : mBluetoothList ){
			if( d.getAddress().compareTo(device.getAddress()) == 0 ){
				alreadyAdd = true;
				break;
			}
		}
		
		if( !alreadyAdd ){
			mBluetoothList.add( device );
			BluetoothHelper.setNearListCount( mBluetoothList.size() );
		}
	}
	
	public static void clearDevice() {
		mBluetoothList.clear();
	}

	@Override
	public int getCount() {
		return mBluetoothList.size();
	}

	@Override
	public Object getItem(int position) {
		return mBluetoothList.get(position);
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
			convertView = mInflater.inflate(R.layout.bluetooth_list_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.txt_bluetooth_name);
			holder.others = (TextView) convertView.findViewById(R.id.txt_bluetooth_others);
			holder.add = (ImageView) convertView.findViewById(R.id.img_bluetooth_add);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final BluetoothDevice b = mBluetoothList.get(position);
		holder.name.setText(b.getName() + "(" + mAddressToExtraInfoMap.get(b.getAddress()) + ")");
		holder.others.setText(b.getAddress());
		holder.add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BluetoothDevice device = mBluetoothList.get(position);
				Bluetooth b = new Bluetooth();
				b.setName( device.getName() );
				b.setMac( device.getAddress() );
				BluetoothService.INSTANCE.addWhiteist(b);
				BluetoothHelper.refresh();
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
