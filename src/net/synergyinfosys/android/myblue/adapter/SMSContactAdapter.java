package net.synergyinfosys.android.myblue.adapter;

import java.util.List;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.asynjob.SMSRetrieveJob;
import net.synergyinfosys.android.myblue.ui.cache.SMSCache;

import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SMSContactAdapter extends BaseAdapter implements TitleProvider, OnClickListener {
	private static final String TAG = "SMSContactAdapter";
	
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
			convertView = mInflater.inflate(R.layout.activity_sms_stub, null);
			ViewStub vs = (ViewStub) convertView.findViewById(R.id.mystub);
			convertView = vs.inflate();
			
			holder.smsList = (ListView) convertView.findViewById(R.id.list_sms);
			holder.btn = (Button) convertView.findViewById(R.id.btn_sms);
			holder.btn.setOnClickListener(this);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String contact = mList.get(position);
		SMSListAdapter adapter = new SMSListAdapter(mContext, contact);
		holder.smsList.setAdapter(adapter);
		holder.smsList.setDividerHeight(0);
//		holder.smsList.setSelection(adapter.getCount()-1);
		
		SMSRetrieveJob job = new SMSRetrieveJob();
		job.execute(holder.smsList);
		
		holder.btn.setText("与" + mList.get(position) + "开始交谈");
		holder.btn.setTag(position);
		
		return convertView;
	}

	public String getTitle(
			int position) {
		return mList.get(position) + "(" + SMSCache.getInstance().getAllSMS().get(SMSCache.getInstance().getContactNames().get(position)).size() + ")";
	}

	static final class ViewHolder {
		ListView smsList;
		Button btn; 
	}

	@Override
	public void onClick(
			View v) {
		switch( v.getId() ){
		case R.id.btn_sms:
			int position = (Integer) v.getTag();
			Toast.makeText(mContext, position + " xxx", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
