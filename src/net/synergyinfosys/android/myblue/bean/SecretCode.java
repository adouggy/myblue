package net.synergyinfosys.android.myblue.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class SecretCode implements Parcelable{
	private long id = -1;
	private String code = null;
	private long timestamp = -1;
	
	private static DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
	
	public boolean isValid(){
		if( code==null || code.length()==0){
			return false;
		}
		return true;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("id->" + this.getId() );
		sb.append("\n");
		
		sb.append("code->" + this.getCode());
		sb.append("\n");
		
		sb.append("timestamp->" +formatter.format(new Date( this.getTimestamp())) );
		sb.append("\n");
		
		return sb.toString();
	}
	
	public static final Parcelable.Creator<SecretCode> CREATOR = new Parcelable.Creator<SecretCode>() {
		public SecretCode createFromParcel(Parcel in) {
			SecretCode c = new SecretCode();
			c.setId( in.readLong() );
			c.setCode( in.readString() );
			c.setTimestamp( in.readLong() );
			return c;
		}

		public SecretCode[] newArray(int size) {
			return new SecretCode[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.getId());
		dest.writeString(this.getCode());
		dest.writeLong(this.getTimestamp());
	}

}
