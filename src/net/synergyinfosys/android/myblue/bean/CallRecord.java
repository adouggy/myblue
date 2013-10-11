package net.synergyinfosys.android.myblue.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;


public class CallRecord implements Parcelable {
	
	private long id = -1;
	private long contactId;
	private long recordTime;
	private CallStatus status;
	private long androidId = -1;
	private boolean isDelete = false;
	private boolean isNew = false; //是否是没看见过的通话记录(被拦截的)

	private DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
	
	public String getRecordTimeStr(){
		return formatter.format(new Date(this.getRecordTime()));
	}
	
	public boolean isNew() {
		return isNew;
	}

	public void setNew(
			boolean isNew) {
		this.isNew = isNew;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public CallStatus getStatus() {
		return status;
	}

	public void setStatus(CallStatus status) {
		this.status = status;
	}
	
	public long getAndroidId() {
		return androidId;
	}

	public void setAndroidId(long androidId) {
		this.androidId = androidId;
	}
	
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id->" + this.getId());
		sb.append("\n");
		sb.append("contactId->" + this.getContactId());
		sb.append("\n");
		sb.append("recordTime->" + this.getRecordTime() + "(" + this.getRecordTimeStr() + ")");
		sb.append("\n");
		sb.append("status->" + this.getStatus().toString());
		sb.append("\n");
		sb.append("androidId->" + this.getAndroidId());
		sb.append("\n");
		sb.append("isDelete->" + this.isDelete());
		sb.append("\n");
		sb.append("isNew->" + this.isNew());
		sb.append("\n");
		return sb.toString();
	}
	
	public static final Parcelable.Creator<CallRecord> CREATOR = new Parcelable.Creator<CallRecord>() {
		public CallRecord createFromParcel(Parcel in) {
			CallRecord c = new CallRecord();
			c.setId( in.readLong() );
			c.setContactId( in.readLong() );
			c.setRecordTime( in.readLong() );
			c.setStatus( CallStatus.valueOf( in.readString() ) );
			c.setAndroidId( in.readLong() );
			c.setDelete( in.readByte() == 1 );
			c.setNew( in.readByte() == 1 );
			return c;
		}

		public CallRecord[] newArray(int size) {
			return new CallRecord[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong( this.getId() );
		dest.writeLong( this.getContactId() );
		dest.writeLong( this.getRecordTime() );
		dest.writeString( this.getStatus().toString() );
		dest.writeLong( this.getAndroidId() );
		dest.writeByte((byte) (this.isDelete() ? 1 : 0));
		dest.writeByte((byte) (this.isNew() ? 1 : 0));
	}

}
