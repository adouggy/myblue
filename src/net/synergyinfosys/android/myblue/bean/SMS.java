package net.synergyinfosys.android.myblue.bean;

public class SMS {
	private int id;
	private String address;
	private String body;
	private int type;
	private int read;
	private long date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public void setDate(long date) {
		this.date = date;
	}
	
	
	
}
