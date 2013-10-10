package net.synergyinfosys.android.myblue.dao;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.bean.CallMode;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.util.Constants;
import net.synergyinfosys.android.myblue.util.StringUtil;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class ContactDao extends AbstractDBDao{
	public static final String TAG = "ContactDao";
	 /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final ContactDao INSTANCE = new ContactDao();
     }

     public static ContactDao getInstance() {
             return SingletonHolder.INSTANCE;
     }
	
	private ContactDao() {
		super();
		if (this.mDBInstance != null) {
			try {
				mDBInstance.execSQL(Constants.DB_TABLE_CONTACT_SQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(TAG, "db == null");
		}
	}

	public long insertContact( Contact contact ){
		Log.i(TAG, "insertContact");
		ContentValues cv = new ContentValues();
		cv.put("name", StringUtil.makeNotNull(contact.getName()));
		cv.put("number", StringUtil.makeNotNull(contact.getNumber()));
		cv.put("hideSMS", contact.isHideSMS());
		cv.put("hideCallRecord", contact.isHideCallRecord());
		cv.put("callMode", StringUtil.makeNotNull(contact.getCallMode().toString()));
		cv.put("isSelected", contact.isSelected());
		cv.put("lookupKey", StringUtil.makeNotNull(contact.getLookupKey()));
		cv.put("contactId", StringUtil.makeNotNull(contact.getContactId() + ""));
		return mDBInstance.insert(Constants.DB_TABLE_CONTACT_NAME, null, cv);
	}
	
	public int removeContact( long id ){
		Log.i(TAG, "removeContact");
		return mDBInstance.delete(Constants.DB_TABLE_CONTACT_NAME, "id=?", new String[]{ id+"" });
	}
	
	public int removeContactByLookupKey( String lookupKey ){
		Log.i(TAG, "removeContactByLookupKey");
		return mDBInstance.delete(Constants.DB_TABLE_CONTACT_NAME, "lookupKey=?", new String[]{ lookupKey });
	}
	
	public int updateContact(Contact contact){
		Log.i(TAG, "updateContact");
		ContentValues cv = new ContentValues();
		cv.put("name", contact.getName());
		cv.put("number", contact.getNumber());
		cv.put("hideSMS", contact.isHideSMS());
		cv.put("hideCallRecord", contact.isHideCallRecord());
		cv.put("callMode", contact.getCallMode().toString());
		cv.put("isSelected", contact.isSelected());
		cv.put("lookupKey", contact.getLookupKey());
		cv.put("contactId", StringUtil.makeNotNull(contact.getContactId() + ""));
		return mDBInstance.update(Constants.DB_TABLE_CONTACT_NAME, cv, "id=?", new String[]{ contact.getId()+"" });
	}
	
	public Contact getContact(long id){
		Log.i(TAG, "getContact");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_CONTACT_NAME + " where id='" + id + "'", null);
		while (cursor.moveToNext()) {
			Contact c= new Contact();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			c.setNumber( cursor.getString( cursor.getColumnIndex("number") ) );
			c.setHideCallRecord( cursor.getInt( cursor.getColumnIndex("hideCallRecord") )==1?true:false );
			c.setHideSMS( cursor.getInt( cursor.getColumnIndex("hideSMS") )==1?true:false );
			c.setCallMode( CallMode.valueOf( cursor.getString( cursor.getColumnIndex("callMode") ) ) );
			c.setSelected( cursor.getInt( cursor.getColumnIndex("isSelected") )==1?true:false );
			c.setLookupKey( cursor.getString( cursor.getColumnIndex("lookupKey") ) );
			c.setContactId( cursor.getLong( cursor.getColumnIndex("contactId") ) );
			return c;
		}
		cursor.close();
		return null;
	}
	
	public Contact getContactByNumber(String number){
		Log.i(TAG, "getContact");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_CONTACT_NAME + " where number='" + number + "'", null);
		while (cursor.moveToNext()) {
			Contact c= new Contact();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			c.setNumber( cursor.getString( cursor.getColumnIndex("number") ) );
			c.setHideCallRecord( cursor.getInt( cursor.getColumnIndex("hideCallRecord") )==1?true:false );
			c.setHideSMS( cursor.getInt( cursor.getColumnIndex("hideSMS") )==1?true:false );
			c.setCallMode( CallMode.valueOf( cursor.getString( cursor.getColumnIndex("callMode") ) ) );
			c.setSelected( cursor.getInt( cursor.getColumnIndex("isSelected") )==1?true:false );
			c.setLookupKey( cursor.getString( cursor.getColumnIndex("lookupKey") ) );
			c.setContactId( cursor.getLong( cursor.getColumnIndex("contactId") ) );
			return c;
		}
		cursor.close();
		return null;
	}
	
	public Contact getContact(String number){
		Log.i(TAG, "getContact by number");

		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_CONTACT_NAME + " where number='" + number + "'", null);
		while (cursor.moveToNext()) {
			Contact c= new Contact();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			c.setNumber( cursor.getString( cursor.getColumnIndex("number") ) );
			c.setHideCallRecord( cursor.getInt( cursor.getColumnIndex("hideCallRecord") )==1?true:false );
			c.setHideSMS( cursor.getInt( cursor.getColumnIndex("hideSMS") )==1?true:false );
			c.setCallMode( CallMode.valueOf( cursor.getString( cursor.getColumnIndex("callMode") ) ) );
			c.setSelected( cursor.getInt( cursor.getColumnIndex("isSelected") )==1?true:false );
			c.setLookupKey( cursor.getString( cursor.getColumnIndex("lookupKey") ) );
			c.setContactId( cursor.getLong( cursor.getColumnIndex("contactId") ) );
			return c;
		}
		cursor.close();
		return null;
	}
	
	public ArrayList<Contact> getContactAll(){
		Log.i(TAG, "getContactAll");
		ArrayList<Contact> list = new ArrayList<Contact>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_CONTACT_NAME + " order by id asc", null);
		while (cursor.moveToNext()) {
			Contact c= new Contact();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			c.setNumber( cursor.getString( cursor.getColumnIndex("number") ) );
			c.setHideCallRecord( cursor.getInt( cursor.getColumnIndex("hideCallRecord") )==1?true:false );
			c.setHideSMS( cursor.getInt( cursor.getColumnIndex("hideSMS") )==1?true:false );
			c.setCallMode( CallMode.valueOf( cursor.getString( cursor.getColumnIndex("callMode") ) ) );
			c.setSelected( cursor.getInt( cursor.getColumnIndex("isSelected") )==1?true:false );
			c.setLookupKey( cursor.getString( cursor.getColumnIndex("lookupKey") ) );
			c.setContactId( cursor.getLong( cursor.getColumnIndex("contactId") ) );
			list.add( c );
		}
		Log.i( TAG, list.size() + " results returned" );
		Log.d( TAG, list.toString() );
		cursor.close();
		return list;
	}
	
	public ArrayList<Contact> getContactSelected(){
		Log.i(TAG, "getContactAll");
		ArrayList<Contact> list = new ArrayList<Contact>();
		Cursor cursor = mDBInstance.rawQuery("select * from " + Constants.DB_TABLE_CONTACT_NAME + " where isSelected='1'" +" order by id desc", null);
		while (cursor.moveToNext()) {
			Contact c= new Contact();
			c.setId( cursor.getLong( cursor.getColumnIndex("id") ) );
			c.setName( cursor.getString( cursor.getColumnIndex("name") ) );
			c.setNumber( cursor.getString( cursor.getColumnIndex("number") ) );
			c.setHideCallRecord( cursor.getInt( cursor.getColumnIndex("hideCallRecord") )==1?true:false );
			c.setHideSMS( cursor.getInt( cursor.getColumnIndex("hideSMS") )==1?true:false );
			c.setCallMode( CallMode.valueOf( cursor.getString( cursor.getColumnIndex("callMode") ) ) );
			c.setSelected( cursor.getInt( cursor.getColumnIndex("isSelected") )==1?true:false );
			c.setLookupKey( cursor.getString( cursor.getColumnIndex("lookupKey") ) );
			c.setContactId( cursor.getLong( cursor.getColumnIndex("contactId") ) );
			list.add( c );
		}
		Log.i( TAG, list.size() + " results returned" );
		Log.d( TAG, list.toString() );
		cursor.close();
		return list;
	}
}
