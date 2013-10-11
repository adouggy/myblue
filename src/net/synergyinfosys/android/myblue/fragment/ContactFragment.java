package net.synergyinfosys.android.myblue.fragment;

import net.synergyinfosys.android.myblue.ContactAddActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import net.synergyinfosys.android.myblue.helper.ContactHelper;
import net.synergyinfosys.android.myblue.service.LockStatusService;
import net.synergyinfosys.android.myblue.util.ContactUtil;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactFragment extends Fragment implements ITitle, OnClickListener {

	public static final String TAG = "ContactFragment";
	private static ContactHelper mHelper = null;
	private TextView mButtonAdd = null;

	@Override
	public void onCreate(
			Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHelper = new ContactHelper(getActivity());
		mHelper.onCreate(R.layout.activity_contact);

	}

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		mHelper.onPostCreate();
		return mHelper.getView();
	}

	public static void refresh() {
		ContactHelper.refresh();
	}

	@Override
	public void onSaveInstanceState(
			Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityCreated(
			Bundle savedInstanceState) {
		mButtonAdd = (TextView) getView().findViewById(R.id.txt_contact_add);
		mButtonAdd.setOnClickListener(this);

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public String getTitle() {
		return "联系人";
	}

	@Override
	public void onActivityResult(
			int requestCode,
			int resultCode,
			Intent data) {
		Log.i(TAG,
				"onActivityResult->" + requestCode + "/" + resultCode);

		super.onActivityResult(requestCode,
				resultCode,
				data);

		switch (requestCode) {
		case 1: {
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				Log.i( TAG, "Uri->" + contactData.toString() );
				Contact c = ContactUtil.INSTANCE.getContactByUri(contactData);
				if (c != null) {
					
					// store the user selection
					long id = ContactDao.getInstance().insertContact(c);
					Log.d(TAG, "new contact add with id:" + id);
					
					// hide the contact, sms, call record
					LockStatusService.INSTANCE.hideAll();
					
					// make sure modify some configure for protection
					Intent addContactIntent = new Intent();
					ComponentName addContactCN = new ComponentName(getActivity(), ContactAddActivity.class);
					addContactIntent.putExtra("contact",
							c);
					addContactIntent.setComponent(addContactCN);
					addContactIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getActivity().startActivity(addContactIntent);
					ContactHelper.refresh();
				}

			}
			break;
		}

		}
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.txt_contact_add:
			// make user select a contact from android
			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			ContactFragment.this.startActivityForResult(intent,
					1);
			break;
		}
	}
}
