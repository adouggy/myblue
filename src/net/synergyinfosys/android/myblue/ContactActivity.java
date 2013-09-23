package net.synergyinfosys.android.myblue;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ContactActivity extends Activity implements OnClickListener {
	public static final String TAG = "ContactActivity";

	private TextView mButtonAdd = null;
//	private static ListView mListContact = null;
//	private static ContactListAdapter mContactAdapter = null;
//	private static OpenEditHandler mOpenEditHandler = new OpenEditHandler();
	private static Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact);
		
		mContext = this.getApplicationContext();

		mButtonAdd = (TextView) findViewById(R.id.txt_contact_add);
		mButtonAdd.setOnClickListener(this);

//		mListContact = (ListView) findViewById(R.id.list_contact_hidden);
//		mContactAdapter = new ContactListAdapter(this.getApplicationContext(), ContactDao.getInstance().getContactAll());
//		mListContact.setAdapter(mContactAdapter);
	}

//	public static void refreshContact() {
//		ContactListAdapter.setData(ContactDao.getInstance().getContactAll());
//		if( mContactAdapter!=null )
//			mContactAdapter.notifyDataSetChanged();
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contact, menu);
		return true;
	}

	@Override
	public void onClick(View src) {
		switch (src.getId()) {
		case R.id.txt_contact_add:
			Intent addContactIntent = new Intent();
			ComponentName addContactCN = new ComponentName(mContext, ContactAddActivity.class);
			addContactIntent.setComponent(addContactCN);
			startActivity(addContactIntent);
			break;
		}
	}
	
//	public static void editContact(Contact c){
//		Message msg = new Message();
//		Bundle bundle = new Bundle();
//		bundle.putParcelable("contact", c);
//		msg.setData(bundle);
//		mOpenEditHandler.sendMessage(msg);
//	}

//	static class OpenEditHandler extends Handler {
//		@Override
//		public void handleMessage(Message msg) {
//			Contact c = (Contact) msg.getData().get("contact");
//			
//			Intent addContactIntent = new Intent();
//			ComponentName addContactCN = new ComponentName(mContext, ContactAddActivity.class);
//			addContactIntent.putExtra("contact", c);
//			addContactIntent.setComponent(addContactCN);
//			addContactIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			mContext.startActivity(addContactIntent);
//		}
//	};

}
