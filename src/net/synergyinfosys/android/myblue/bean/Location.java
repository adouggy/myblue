package net.synergyinfosys.android.myblue.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Location implements Parcelable {
	public static final String TAG = "Location";
	
	private long id = -1;
	private String name = null;
	private double latitude = -1;
	private double longitude = -1;
	private String description = null;

	public static final double EPSILON = 0.00001f;

	public boolean isValid() {
		if (latitude > 0 && longitude > 0) {
			return true;
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("id->" + this.getId());
		sb.append("\n");

		sb.append("name->" + this.getName());
		sb.append("\n");

		sb.append("latitude->" + this.getLatitude());
		sb.append("\n");

		sb.append("longtitude->" + this.getLongitude());
		sb.append("\n");

		sb.append("description->" + this.getDescription());
		sb.append("\n");

		return sb.toString();
	}

	public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
		public Location createFromParcel(Parcel in) {
			Location c = new Location();
			c.setId(in.readLong());
			c.setName(in.readString());
			c.setLatitude(in.readDouble());
			c.setLongitude(in.readDouble());
			c.setDescription(in.readString());
			return c;
		}

		public Location[] newArray(int size) {
			return new Location[size];
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
		dest.writeDouble(this.getLatitude());
		dest.writeDouble(this.getLongitude());
		dest.writeString(this.getDescription());
	}

	public boolean near(double latitue, double longtitude) {
		Log.d( TAG, "near:");
		Log.d( TAG, this.toString() );
		Log.d( TAG,  latitue + "," + longtitude);

		boolean sameLatitude = Math.abs(Math.abs(this.getLatitude()) - Math.abs(latitue)) < EPSILON;
		boolean sameLongtitude = Math.abs(Math.abs(this.getLongitude()) - Math.abs(longtitude)) < EPSILON;

		return (sameLatitude && sameLongtitude);
	}
}
