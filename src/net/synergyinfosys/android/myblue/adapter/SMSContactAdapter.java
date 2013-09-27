package net.synergyinfosys.android.myblue.adapter;

import java.util.List;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.ui.cache.SMSCache;

import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SMSContactAdapter extends BaseAdapter implements TitleProvider {

	private LayoutInflater mInflater = null;
	private List<String> mList = null;

	private Context mContext = null;

	public SMSContactAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList = SMSCache.getInstance().getContactNames();// ContactDao.getInstance().getContactAll();
	}

	@Override
	public int getItemViewType(
			int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return mList.size();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(
			int position) {
		return position;
	}

	@Override
	public long getItemId(
			int position) {
		return position;
	}

	@Override
	public View getView(
			int position,
			View convertView,
			ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_sms,
					null);
			holder.smsList = (ListView) convertView.findViewById(R.id.list_sms);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SMSListAdapter adapter = new SMSListAdapter(mContext, mList.get(position));
		holder.smsList.setAdapter(adapter);
		
		holder.smsList.setDividerHeight(0);

		return convertView;
	}

	public String getTitle(
			int position) {
		return mList.get(position);
	}

	static final class ViewHolder {
		ListView smsList;
	}

}
