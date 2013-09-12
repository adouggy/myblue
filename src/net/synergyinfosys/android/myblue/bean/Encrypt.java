package net.synergyinfosys.android.myblue.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Encrypt implements Parcelable{
	private long id = -1;
	private String name = null;
	private String password = null;
	private String comment = null;
	
	public boolean isValid(){
		if( name == null || name.length()==0 || password == null || password.length()==0 ){
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("id->" + this.getId() );
		sb.append("\n");
		
		sb.append("name->" + this.getName() );
		sb.append("\n");
		
		sb.append("password->" + this.getPassword() );
		sb.append("\n");
		
		sb.append("comment->" + this.getComment() );
		sb.append("\n");
		
		return sb.toString();
	}
	
	public static final Parcelable.Creator<Encrypt> CREATOR = new Parcelable.Creator<Encrypt>() {
		public Encrypt createFromParcel(Parcel in) {
			Encrypt c = new Encrypt();
			c.setId( in.readLong() );
			c.setName( in.readString() );
			c.setPassword(in.readString());
			c.setComment(in.readString());
			return c;
		}

		public Encrypt[] newArray(int size) {
			return new Encrypt[size];
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
		dest.writeString(this.getPassword());
		dest.writeString(this.getComment());
	}

}
