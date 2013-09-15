package net.synergyinfosys.android.myblue.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Bluetooth implements Parcelable{
	private long id = -1;
	private String name = null;
	private String mac = null;
	
	public boolean isValid(){
		if( name==null || name.length()==0 || mac==null || mac.length()==0 ){
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("id->" + this.getId() );
		sb.append("\n");
		
		sb.append("name->" + this.getName());
		sb.append("\n");
		
		sb.append("mac->" + this.getMac() );
		sb.append("\n");
		
		return sb.toString();
	}
	
	public static final Parcelable.Creator<Bluetooth> CREATOR = new Parcelable.Creator<Bluetooth>() {
		public Bluetooth createFromParcel(Parcel in) {
			Bluetooth c = new Bluetooth();
			c.setId( in.readLong() );
			c.setName( in.readString() );
			c.setMac( in.readString() );
			return c;
		}

		public Bluetooth[] newArray(int size) {
			return new Bluetooth[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.getId());
		dest.writeString(this.getName());
		dest.writeString(this.getMac());
	}

//	@Override
//	public boolean equals(Object o) {
//		Bluetooth another = (Bluetooth)o;
//		if( this.getMac() == null || another.getMac() == null ){
//			return false;
//		}
//		
//		return this.getMac().compareTo( another.getMac() ) == 0;
//	}
}
