package net.synergyinfosys.android.myblue.helper;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.ContactAddActivity;
import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adao.ContactADao;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import net.synergyinfosys.android.myblue.ui.cards.MyImageCard;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fima.cardsui.views.CardUI;

public class ContactHelper extends MyHelper implements OnClickListener {
	public static final String TAG = "ContactHelper";

	private TextView mButtonAdd = null;
	private static CardUI mCardView = null;
	private static Context mContext = null;

	public ContactHelper(Activity act) {
		super(act);
		mContext = act.getApplicationContext();
	}

	@Override
	public void onCreate(int layoutId) {
		super.onCreate(layoutId);
	}

	@Override
	public void onPostCreate() {
		super.onPostCreate();

		mButtonAdd = (TextView) getView().findViewById(R.id.txt_contact_add);
		mButtonAdd.setOnClickListener(this);

		mCardView = (CardUI) getView().findViewById(R.id.cardsview);
		mCardView.setSwipeable(false);

		refresh();
	}

	public static void refresh() {
		if (mCardView == null) {
			return;
		}

		mCardView.clearCards();
		ArrayList<Contact> list = ContactDao.getInstance().getContactSelected();
		for (int i = 0; i < list.size(); ++i) {
			
			final Contact contact = list.get(i);
			
			Bitmap bmp = ContactADao.INSTANCE.getFacebookPhoto(contact.getNumber());
			Log.i( TAG, contact.getName() + "->" + bmp );
			
			final MyImageCard c = new MyImageCard( contact.getName(), R.drawable.person, contact);
			
			c.setPhoto(bmp);
			
			StringBuilder sb = new StringBuilder();
			sb.append( "Tel:" + contact.getNumber() );
			sb.append("\n");
			sb.append( "来电:" + contact.getCallMode() );
			sb.append("\n");
			sb.append( "保护:" + (contact.isHideCallRecord()?"通话记录":"") + " " + (contact.isHideSMS()?"短信记录":"") );
			sb.append("\n");
			c.setText( sb.toString() );
			
			c.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Toast.makeText(mContext, c.getTitle() + "(" + c.getContact().getNumber() + ")", Toast.LENGTH_SHORT).show();
					Intent addContactIntent = new Intent();
					ComponentName addContactCN = new ComponentName(mContext, ContactAddActivity.class);
					addContactIntent.putExtra("contact", contact);
					addContactIntent.setComponent(addContactCN);
					addContactIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(addContactIntent);
				}
			});
			mCardView.addCardToLastStack(c);
		}

		mCardView.refresh();
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

}
