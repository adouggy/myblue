package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import android.telephony.PhoneNumberUtils;

public enum ContactService {
	INSTANCE;
	ContactService() {

	}

	public Contact getContactByNumber(String number) {
		ArrayList<Contact> list = ContactDao.getInstance().getContactAll();

		for (Contact c : list) {
			if (PhoneNumberUtils.compare(c.getNumber(), number)) {
				return c;
			}
		}

		return null;
	}

	public ArrayList<Contact> getAllSecretContacts() {
		return ContactDao.getInstance().getContactAll();
	}

}
