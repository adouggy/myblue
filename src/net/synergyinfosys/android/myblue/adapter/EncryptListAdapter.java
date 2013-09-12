package net.synergyinfosys.android.myblue.adapter;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.EncryptAddActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Encrypt;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EncryptListAdapter extends BaseAdapter {
	public static final String TAG = "EncryptListAdapter";

	private static ArrayList<Encrypt> mEncryptList = null;
	private static LayoutInflater mInflater = null;
	private static Context mContext;

	public EncryptListAdapter(Context ctx, ArrayList<Encrypt> list) {
		mContext = ctx;
		mInflater = LayoutInflater.from(ctx);
		mEncryptList = list;
	}

	public static void setData(ArrayList<Encrypt> list) {
		mEncryptList = list;
	}

	@Override
	public int getCount() {
		return mEncryptList.size();
	}

	@Override
	public Object getItem(int position) {
		return mEncryptList.get(position);
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
			convertView = mInflater.inflate(R.layout.encrypt_list_item, null);

			holder.pager = (ViewPager) convertView.findViewById(R.id.viewpager_encrypt);
			View page1 = mInflater.inflate(R.layout.encrypt_list_item_page1, null);
			View page2 = mInflater.inflate(R.layout.encrypt_list_item_page2, null);
			ArrayList<View> views = new ArrayList<View>();
			views.add(page1);
			views.add(page2);

			MyPagerAdapter myAdapter = new MyPagerAdapter(views);
			holder.pager.setAdapter(myAdapter);

			holder.name = (TextView) page1.findViewById(R.id.txt_encrypt_item_name);
			holder.comment = (TextView) page1.findViewById(R.id.txt_encrypt_item_comment);
			holder.password = (TextView) page2.findViewById(R.id.txt_encrypt_item_password);
			holder.img = (ImageView) page1.findViewById(R.id.img_encrypt_item_icon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Encrypt e = mEncryptList.get(position);
		holder.name.setText(e.getName());
		holder.password.setText(e.getPassword());
		holder.comment.setText(e.getComment());

		holder.img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				ComponentName cn = new ComponentName( mContext, EncryptAddActivity.class );
				Bundle bundle = new Bundle();
				bundle.putParcelable("encrypt", e);
				
				intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
				intent.setComponent(cn);
				intent.putExtras(bundle);
				
				mContext.startActivity(intent);
			}
		});

		return convertView;
	}

	static final class ViewHolder {
		ViewPager pager;
		TextView name;
		TextView password;
		TextView comment;
		ImageView img;
	}

	public static final class MyPagerAdapter extends PagerAdapter {
		private ArrayList<View> mViews = null;

		public MyPagerAdapter(ArrayList<View> views) {
			mViews = views;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return mViews.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mViews.get(position));
			return mViews.get(position);
		}
	}
}
