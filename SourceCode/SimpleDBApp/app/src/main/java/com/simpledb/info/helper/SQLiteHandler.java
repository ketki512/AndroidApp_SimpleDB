package com.simpledb.info.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "androidsqlite.db";

	// Login table name
	private static final String TABLE_LOGIN = "login";

	// Login user table name
	private static final String TABLE_LOGIN_USER = "login_user";

	//
	private static final String TABLE_USER_STATS = "user_stats";

	// Login Table Columns names
	private static final String KEY_NAME = "name";
	private static final String KEY_MAJOR = "major";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_INTEREST = "interest";
	private static final String KEY_UNIVERSITY = "university";
	private static final String KEY_USER_COUNT = "count";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN
				+ "("  + KEY_NAME + " TEXT,"
				+ KEY_PASSWORD + " TEXT," + KEY_MAJOR + " TEXT,"
				+ KEY_INTEREST + " TEXT," + KEY_UNIVERSITY + " TEXT" +")";
		db.execSQL(CREATE_LOGIN_TABLE);

		String CREATE_LOGIN_USER_TABLE = "CREATE TABLE " + TABLE_LOGIN_USER + "("
				+ KEY_NAME + " TEXT," + KEY_PASSWORD + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_USER_TABLE);

		String CREATE_USER_STATS="CREATE TABLE "+ TABLE_USER_STATS + "(" + KEY_USER_COUNT + " TEXT" + ")";

		db.execSQL(CREATE_USER_STATS);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN_USER);

		db.execSQL("DROP TABLE IF EXISTS"+ TABLE_USER_STATS);

		Log.d(TAG, "tables drop");
		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing Login User Details
	 */
	public void addUserLogin(String mobile, String password){
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_LOGIN_USER, null, null);

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, mobile); // Mobile
		values.put(KEY_PASSWORD, password); // Password

		// Inserting Row
		long id = db.insert(TABLE_LOGIN_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String password, String major, String interest,String university) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LOGIN, null, null);
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_PASSWORD, password);
		values.put(KEY_MAJOR, major);
		values.put(KEY_INTEREST, interest);
		values.put(KEY_UNIVERSITY, university);

		// Inserting Row
		long id = db.insert(TABLE_LOGIN, null, values);
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

		db.close(); // Closing database connection

		Log.d(TAG, "data inserted into sqlite: " + values.get(KEY_INTEREST));
	}


	//storing customer count in database

	public  void addCustomerCount(String count)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		//db.delete(TABLE_CUST_STATS,null,null);
		ContentValues values=new ContentValues();
		values.put(KEY_USER_COUNT,count);
		long id = db.insert(TABLE_USER_STATS, null, values);
		db.close();
		Log.d(TAG, "user count inserted into sqlite: " + values.toString());
	}


	/**
	 * Getting login user data from database
	 * */
	public ArrayList<String> getLoginUserDetails() {
		ArrayList<String> values = new ArrayList<>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_LOGIN_USER, null, null, null, null, null, null);

		Log.d(TAG, "cursor login= " + cursor.getCount());
		cursor.moveToPosition(0);
		values.add(cursor.getString(0));

		cursor.close();
		db.close();
		// return user

		Log.d(TAG, "Fetching user from Sqlite: " + values.toString());

		return values;
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();

		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
//		Cursor cursor = db.rawQuery(selectQuery,null);
		Cursor c = db.rawQuery(selectQuery, null);
		int i=c.getCount();
		Log.d(TAG,"cursor count= "+i);

		c.moveToFirst();

		if (c.moveToFirst()) {
			do {
				user.put("name", c.getString(c.getColumnIndex(KEY_NAME)));
				user.put("password", c.getString(c.getColumnIndex(KEY_PASSWORD)));
				user.put("major", c.getString(c.getColumnIndex(KEY_MAJOR)));
				user.put("interest", c.getString(c.getColumnIndex(KEY_INTEREST)));
				user.put("university", c.getString(c.getColumnIndex(KEY_UNIVERSITY)));
			} while (c.moveToNext());
		}

		c.close();
		db.close();
		// return user

		Log.d(TAG, "Fetching user details from Sqlite: " + user.get("name"));

		return user;
	}


	//getting customer count from sqlite db
	public HashMap<String, String> getCustomerCount() {
		HashMap<String, String> user = new HashMap<String, String>();

		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_USER_STATS;
		Cursor c = db.rawQuery(selectQuery, null);
		int i=c.getCount();
		Log.d(TAG,"user count cursor= "+i);

		c.moveToFirst();

		if (c.moveToFirst()) {
			do {
				user.put("count", c.getString(c.getColumnIndex(KEY_USER_COUNT)));
			} while (c.moveToNext());
		}

		c.close();
		db.close();
		// return user

		Log.d(TAG, "Fetching user count from Sqlite: " + user.get("count"));

		return user;
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT COUNT(*) FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();

		cursor.close();
		db.close();
		return rowCount;

	}


	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_LOGIN, null, null);
		db.delete(TABLE_LOGIN_USER, null, null);
		db.delete(TABLE_USER_STATS,null,null);
		db.close();
		Log.d(TAG, "Deleted all user info from sqlite");
	}

}
