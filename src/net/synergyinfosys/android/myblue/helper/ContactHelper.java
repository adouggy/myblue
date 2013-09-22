package net.synergyinfosys.android.myblue.helper;

import net.synergyinfosys.android.myblue.ContactAddActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adapter.ContactListAdapter;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ContactHelper extends MyHelper implements OnClickListener {
	public static final String TAG = "ContactHelper";

	private TextView mButtonAdd = null;
	private static ListView mListContact = null;
	private static ContactListAdapter mContactAdapter = null;
	private static OpenEditHandler mOpenEditHandler = new OpenEditHandler();
	
	private static Context mContext = null;

	public ContactHelper(Activity act) {
		super(act);
		mContext = act.getApplicationContext();
	}

	@Override
	public void onCreate(int layoutId){
		super.onCreate( layoutId );
	}

	@Override
	public void onPostCreate() {
		super.onPostCreate();
		
		mButtonAdd = (TextView) getView().findViewById(R.id.txt_contact_add);
		mButtonAdd.setOnClickListener(this);

		mListContact = (ListView) getView().findViewById(R.id.list_contact_hidden);
		mContactAdapter = new ContactListAdapter( mActivity, ContactDao.getInstance().getContactAll());
		mListContact.setAdapter(mContactAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_contact_add:
			Intent addContactIntent = new Intent();
			ComponentName addContactCN = new ComponentName(mActivity, ContactAddActivity.class);
			addContactIntent.setComponent(addContactCN);
			mActivity.startActivity(addContactIntent);
			break;
		}
	}

	public static void editContact(Contact c) {
		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putParcelable("contact", c);
		msg.setData(bundle);
		mOpenEditHandler.sendMessage(msg);
	}

	public static void refreshContact() {
		ContactListAdapter.setData(ContactDao.getInstance().getContactAll());
		if (mContactAdapter != null)
			mContactAdapter.notifyDataSetChanged();
	}

	static class OpenEditHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Contact c = (Contact) msg.getData().get("contact");

			Intent addContactIntent = new Intent();
			ComponentName addContactCN = new ComponentName( mContext, ContactAddActivity.class);
			addContactIntent.putExtra("contact", c);
			addContactIntent.setComponent(addContactCN);
			addContactIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(addContactIntent);
		}
	};
}
