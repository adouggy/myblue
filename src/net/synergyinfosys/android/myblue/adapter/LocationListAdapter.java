package net.synergyinfosys.android.myblue.adapter;

import java.util.List;

import net.synergyinfosys.android.myblue.LocationDetailActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Location;
import net.synergyinfosys.android.myblue.util.StringUtil;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationListAdapter extends BaseAdapter {
	public static final String TAG = "LocationListAdapter";

	private static List<Location> mLocationList = null;
	private static LayoutInflater mInflater = null;
	private static Context mContext = null;

	public LocationListAdapter(Context ctx, List<Location> list) {
		mContext = ctx;
		mInflater = LayoutInflater.from(ctx);
		mLocationList = list;
	}

	public static void setData(List<Location> list) {
		mLocationList = list;
	}

	@Override
	public int getCount() {
		return mLocationList.size();
	}

	@Override
	public Object getItem(int position) {
		return mLocationList.get(position);
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
			convertView = mInflater.inflate(R.layout.location_list_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.txt_location_name);
			holder.latitude = (TextView) convertView.findViewById(R.id.txt_location_latitude);
			holder.longtitude = (TextView) convertView.findViewById(R.id.txt_location_longtitude);
			holder.description = (TextView) convertView.findViewById(R.id.txt_location_description);
			holder.detail = (ImageView) convertView.findViewById(R.id.img_location_detail);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Location loc = mLocationList.get(position);
		holder.name.setText(loc.getName() == null ? "N/A" : loc.getName());
		holder.latitude.setText("(" + loc.getLatitude() + ",");
		holder.longtitude.setText(loc.getLongitude() + ")");
		holder.description.setText(StringUtil.INSTACE.shorten(loc.getDescription(), 35));
		holder.detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				ComponentName cn = new ComponentName(mContext, LocationDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("location", loc);
				
				intent.setComponent(cn);
				intent.putExtras( bundle );
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				
				mContext.startActivity( intent );
				
			}
		});

		return convertView;
	}

	static final class ViewHolder {
		TextView name;
		TextView latitude;
		TextView longtitude;
		TextView description;
		ImageView detail;
	}
}
