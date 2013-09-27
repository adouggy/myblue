package net.synergyinfosys.android.myblue.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMS {
	private long id;
	private String address;
	private String body;
	private int type = -1; // 1 for in box, 2 for out box
	private int read = -1; // 0 for unread, 1 for read
	private long date;
	private long androidId;
	private boolean isDelete;
	
	private static DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public long getDate() {
		return date;
	}
	public String getDateStr(){
		return formatter.format(new Date(this.getDate()));
	}
	public void setDate(long date) {
		this.date = date;
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
		sb.append( "id->" + this.getId() );
		sb.append("\n");
		
		sb.append("address->" + this.getAddress());
		sb.append("\n");
		sb.append("body->" + this.getBody());
		sb.append("\n");
		sb.append("type->" + this.getType());
		sb.append("\n");
		sb.append("read->" + this.getRead());
		sb.append("\n");
		sb.append("date->" + this.getDate() + "(" + this.getDateStr() + ")");
		sb.append("\n");
		sb.append("androidId->" + this.getAndroidId());
		sb.append("\n");
		sb.append("isDelete->" + this.isDelete());
		sb.append("\n");
		return sb.toString();
	}
	
}
