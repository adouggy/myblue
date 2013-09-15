package net.synergyinfosys.android.myblue.service;

import java.util.ArrayList;

import android.telephony.PhoneNumberUtils;

import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;

public enum ContactService {
	INSTANCE;
	ContactService() {

	}
	
	public Contact getContactByNumber(String number){
		ArrayList<Contact> list = ContactDao.getInstance().getContactAll();
		
		for( Contact c : list ){
			if( PhoneNumberUtils.compare( c.getNumber(), number) ){
				return c;
			}
		}
		
		return null;
	}
	
	
	public ArrayList<Contact> getAllSecretContacts(){
		return  ContactDao.getInstance().getContactAll();
	}
}
