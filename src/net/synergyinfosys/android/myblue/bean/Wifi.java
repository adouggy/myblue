package net.synergyinfosys.android.myblue.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Wifi implements Parcelable{
	private long id = -1;
	private String ssid = null;
	private String bssid = null;
	private boolean checked = false;
	
	public boolean isValid(){
		if( ssid==null || ssid.length()==0 || bssid==null || bssid.length()==0 ){
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

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getBssid() {
		return bssid;
	}

	public void setBssid(String bssid) {
		this.bssid = bssid;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("id->" + this.getId() );
		sb.append("\n");
		
		sb.append("ssid->" + this.getSsid());
		sb.append("\n");
		
		sb.append("bssid->" + this.getBssid() );
		sb.append("\n");
		
		sb.append("checked->" + this.isChecked() );
		sb.append("\n");
		
		return sb.toString();
	}
	
	public static final Parcelable.Creator<Wifi> CREATOR = new Parcelable.Creator<Wifi>() {
		public Wifi createFromParcel(Parcel in) {
			Wifi c = new Wifi();
			c.setId( in.readLong() );
			c.setSsid( in.readString() );
			c.setBssid( in.readString() );
			c.setChecked( in.readByte() == 1 );
			return c;
		}

		public Wifi[] newArray(int size) {
			return new Wifi[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.getId());
		dest.writeString(this.getSsid());
		dest.writeString(this.getBssid());
		dest.writeByte( (byte)(this.isChecked()?1:0) );
	}

//	@Override
//	public boolean equals(Object o) {
//		Wifi another = (Wifi)o;
//		if( this.getBssid() == null || another.getBssid() == null ){
//			return false;
//		}
//		
//		return this.getBssid().compareTo( another.getBssid() ) == 0;
//	}
}
