package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.bean.CallMode;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import net.synergyinfosys.android.myblue.fragment.ContactFragment;
import net.synergyinfosys.android.myblue.service.ContactService;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class ContactAddActivity extends Activity implements OnClickListener {
	public static final String TAG = "ContactAddActivity";

	private Button mBtnSave = null;
	private Button mBtnDel = null;
	private EditText mEdtNumber = null;
	private EditText mEdtName = null;
	private CheckBox mChkCallRecord = null;
	private CheckBox mChkSMSRecord = null;
	private Spinner mSpnCallMode = null;

	private Contact mUpdateContact = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact_add);

		mBtnSave = (Button) findViewById(R.id.btn_contact_add_save);
		mBtnSave.setOnClickListener(this);

		mBtnDel = (Button) findViewById(R.id.btn_contact_add_delete);
		mBtnDel.setOnClickListener(this);

		mEdtNumber = (EditText) findViewById(R.id.txt_contact_add_number);
		mEdtName = (EditText) findViewById(R.id.txt_contact_add_name);
		mChkCallRecord = (CheckBox) findViewById(R.id.chk_contact_add_hidden_mode_phone);
		mChkSMSRecord = (CheckBox) findViewById(R.id.chk_contact_add_hidden_mode_sms);
		mSpnCallMode = (Spinner) findViewById(R.id.spin_contact_add_call_mode);

		Bundle b = getIntent().getExtras();
		if( b != null ){
			mUpdateContact = (Contact)b.getParcelable("contact");
		}
		
		if (mUpdateContact != null) {
			mEdtNumber.setText(mUpdateContact.getNumber());
			mEdtName.setText(mUpdateContact.getName());
			mChkCallRecord.setChecked(mUpdateContact.isHideCallRecord());
			mChkSMSRecord.setChecked(mUpdateContact.isHideSMS());
			int index = 0;
			CallMode[] values = CallMode.values();
			for (index = 0; index < values.length; index++) {
				if (mUpdateContact.getCallMode() == values[index]) {
					break;
				}
			}
			mSpnCallMode.setSelection(index);

			this.mBtnDel.setVisibility(View.VISIBLE);
		} else {
			this.mBtnDel.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_add, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_contact_add_save:
			if (this.mUpdateContact == null) {
				Contact c = new Contact();
				c.setNumber(this.mEdtNumber.getText().toString());
				c.setName(this.mEdtName.getText().toString());
				c.setHideCallRecord(this.mChkCallRecord.isChecked());
				c.setHideSMS(this.mChkSMSRecord.isChecked());
				c.setCallMode(CallMode.values()[this.mSpnCallMode.getSelectedItemPosition()]);
				c.setSelected(true);
				if( c.isValid() ){
					long id = ContactDao.getInstance().insertContact(c);
					Log.i(TAG, "new contact id=" + id);
//				ContactActivity.refreshContact();
					ContactFragment.refresh();
				}
				this.finish();
			} else {
				this.mUpdateContact.setNumber(this.mEdtNumber.getText().toString());
				this.mUpdateContact.setName(this.mEdtName.getText().toString());
				this.mUpdateContact.setHideCallRecord(this.mChkCallRecord.isChecked());
				this.mUpdateContact.setHideSMS(this.mChkSMSRecord.isChecked());
				this.mUpdateContact.setCallMode(CallMode.values()[this.mSpnCallMode.getSelectedItemPosition()]);
//				Bundle bundle = new Bundle();
//				bundle.putParcelable("contact", this.mUpdateContact);
//				this.setResult(RESULT_OK, this.getIntent().putExtras(bundle));
				int count = ContactDao.getInstance().updateContact(this.mUpdateContact);
				Log.i( TAG, "updated " + count );
//				ContactActivity.refreshContact();
				ContactFragment.refresh();
				this.finish();
			}
			break;
		case R.id.btn_contact_add_delete:
//			int count = ContactDao.getInstance().removeContact( this.mUpdateContact.getId() );
			ContactService.INSTANCE.restoreContact( this.mUpdateContact );
			Log.i( TAG, "restore " );
			ContactFragment.refresh();
			this.finish();
			break;
		}
	}

}
