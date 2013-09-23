package net.synergyinfosys.android.myblue.util;

public class Constants {
	public static final String STORAGE_PATH = "myblue";
	
	public static final String DB_NAME = "myblue.db3";
	
	public static final String DB_TABLE_SMS_NAME = "sms";
	public static final String DB_TABLE_SMS_SQL = 
			"create table if not exists sms(" +
			"id integer primary key autoincrement, " +
			"address varchar(50), " +
			"body varchar(300), " +
			"read integer, " +
			"date integer," +
			"androidId integer," +
			"isDelete integer " +
			")";
	
	public static final String DB_TABLE_CONTACT_NAME = "contact";
	public static final String DB_TABLE_CONTACT_SQL = 
			"create table if not exists contact(" +
			"id integer primary key autoincrement, " +
			"name varchar(50), " +
			"number varchar(50), " +
			"hideSMS integer, " +
			"hideCallRecord integer, " +
			"callMode varchar(10)," +
			"androidId integer," +
			"isDelete integer " +
			")";
	
	public static final String DB_TABLE_CALL_RECORD_NAME = "callrecord";
	public static final String DB_TABLE_CALL_RECORD_SQL = 
			"create table if not exists callrecord(" +
			"id integer primary key autoincrement, " +
			"contactId integer not null, " +
			"recordTime integer, " +
			"hideSMS integer, " +
			"status varchar(50), " +
			"androidId integer, " +
			"isDelete integer " +
			")";
	
	public static final String DB_TABLE_ENCRYPT_NAME = "encrypt";
	public static final String DB_TABLE_ENCRYPT_SQL = 
			"create table if not exists encrypt(" +
			"id integer primary key autoincrement, " +
			"name varchar(50), " +
			"password varchar(50), " +
			"comment varchar(500) " +
			")";
	
	public static final String DB_TABLE_WIFI_NAME = "wifi";
	public static final String DB_TABLE_WIFI_SQL = 
			"create table if not exists wifi(" +
			"id integer primary key autoincrement, " +
			"ssid varchar(50), " +
			"bssid varchar(50), " +
			"checked integer " +
			")";
	
	public static final String DB_TABLE_LOCATION_NAME = "location";
	public static final String DB_TABLE_LOCATION_SQL = 
			"create table if not exists location(" +
			"id integer primary key autoincrement, " +
			"name varchar(50), " +
			"latitude double, " +
			"longtitude double, " +
			"description varchar(200) " +
			")";
	
	public static final String DB_TABLE_GESTURE_NAME = "gesture";
	public static final String DB_TABLE_GESTURE_SQL = 
			"create table if not exists gesture(" +
			"id integer primary key autoincrement, " +
			"action varchar(20), " +
			"timestamp integer " +
			")";
	
	public static final String DB_TABLE_BLUETOOTH_NAME = "bluetooth";
	public static final String DB_TABLE_BLUETOOTH_SQL = 
			"create table if not exists bluetooth(" +
			"id integer primary key autoincrement, " +
			"name varchar(50), " +
			"mac varchar(50) " +
			")";
	
	public static final String DB_TABLE_SECRET_CODE_NAME = "secretcode";
	public static final String DB_TABLE_SECRET_CODE_SQL = 
			"create table if not exists secretcode(" +
			"id integer primary key autoincrement, " +
			"code varchar(50), " +
			"timestamp integer " +
			")";
}
