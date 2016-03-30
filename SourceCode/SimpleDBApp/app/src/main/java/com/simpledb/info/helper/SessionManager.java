package com.simpledb.info.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
	// LogCat tag
	private static String TAG = SessionManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	Editor editor;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Shared preferences file name
	private static final String PREF_NAME = "AndroidLogin";
	
	private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

	public static final String KEY_NAME = "name";

	// Email address (make variable public to access from outside)
	public static final String KEY_PASSWORD = "password";


	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void createLoginSession(String name, String email){
		// Storing login value as TRUE
		editor.putBoolean(KEY_IS_LOGGEDIN, true);

		// Storing name in pref
		editor.putString(KEY_NAME, name);

		// Storing password in pref
		editor.putString(KEY_PASSWORD, email);

		// commit changes
		editor.commit();
	}

	public void setLogin(boolean isLoggedIn) {

		editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

		// commit changes
		editor.commit();

		Log.d(TAG, "User login session modified!");
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_IS_LOGGEDIN, false);
	}
}
