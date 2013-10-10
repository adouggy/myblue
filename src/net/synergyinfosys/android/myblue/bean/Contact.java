package net.synergyinfosys.android.myblue.bean;

import net.synergyinfosys.android.myblue.util.StringUtil;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
	private long id = -1;
	private String name;
	private String number;
	private boolean hideSMS;
	private boolean hideCallRecord;
	private CallMode callMode;
	private boolean isSelected = false;
	private String lookupKey;
	private long contactId;

	public boolean isValid() {
		if (StringUtil.isNoneBlank(name) && StringUtil.isNoneBlank(number))
			return true;
		return false;
	}
	
	public long getContactId() {
		return contactId;
	}

	public void setContactId(
			long contactId) {
		this.contactId = contactId;
	}

	public String getLookupKey() {
		return lookupKey;
	}

	public void setLookupKey(
			String lookupKey) {
		this.lookupKey = lookupKey;
	}

	public long getId() {
		return id;
	}

	public void setId(
			long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(
			String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(
			String number) {
		this.number = number;
	}

	public boolean isHideSMS() {
		return hideSMS;
	}

	public void setHideSMS(
			boolean hideSMS) {
		this.hideSMS = hideSMS;
	}

	public boolean isHideCallRecord() {
		return hideCallRecord;
	}

	public void setHideCallRecord(
			boolean hideCallRecord) {
		this.hideCallRecord = hideCallRecord;
	}

	public CallMode getCallMode() {
		if( callMode == null )
			return CallMode.normal;
		return callMode;
	}

	public void setCallMode(
			CallMode callMode) {
		this.callMode = callMode;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(
			boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("id->" + this.getId());
		sb.append("\n");

		sb.append("name->" + this.getName());
		sb.append("\n");

		sb.append("number->" + this.getNumber());
		sb.append("\n");

		sb.append("hideSMS->" + this.isHideSMS());
		sb.append("\n");

		sb.append("hideCallRecord" + this.isHideCallRecord());
		sb.append("\n");

		sb.append("callmode->" + this.getCallMode());
		sb.append("\n");

		sb.append("isSelected->" + this.isSelected());
		sb.append("\n");
		
		sb.append("lookupKey->" + this.getLookupKey());
		sb.append("\n");
		
		sb.append("contactId->" + this.getContactId());
		sb.append("\n");

		return sb.toString();
	}

	public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
		public Contact createFromParcel(
				Parcel in) {
			Contact c = new Contact();
			c.setId(in.readLong());
			c.setName(in.readString());
			c.setNumber(in.readString());
			c.setHideSMS(in.readByte() == 1);
			c.setHideCallRecord(in.readByte() == 1);
			c.setCallMode(CallMode.valueOf(in.readString()));
			c.setSelected(in.readByte() == 1);
			c.setLookupKey(in.readString());
			c.setContactId(in.readLong());
			return c;
		}

		public Contact[] newArray(
				int size) {
			return new Contact[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(
			Parcel dest,
			int flags) {
		dest.writeLong(this.getId());
		dest.writeString(this.getName());
		dest.writeString(this.getNumber());
		dest.writeByte((byte) (this.isHideSMS() ? 1 : 0));
		dest.writeByte((byte) (this.isHideCallRecord() ? 1 : 0));
		dest.writeString(this.getCallMode().toString());
		dest.writeByte((byte) (this.isSelected() ? 1 : 0));
		dest.writeString(this.getLookupKey());
		dest.writeLong(this.getContactId());
	}

}
