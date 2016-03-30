package com.simpledb.info.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.simpledb.info.R;
import com.simpledb.info.database.ReadPersonalDB;
import com.simpledb.info.objects.User;
import com.simpledb.info.helper.SQLiteHandler;
import com.simpledb.info.helper.SessionManager;

public class MainActivity extends Activity {

	protected Button login,register;
	private TextView username, password;
	private SessionManager session;
	private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login = (Button) findViewById(R.id.btnLogin);
		register = (Button) findViewById(R.id.btnRegister);
		username = (TextView) findViewById(R.id.username);
		password = (TextView) findViewById(R.id.password);

		db = new SQLiteHandler(getApplicationContext());

		// Session manager
		session = new SessionManager(getApplicationContext());
		// Check if user is already logged in or not
		if (session.isLoggedIn()) {
			Intent intent = new Intent(MainActivity.this, FeedActivity.class);
			startActivity(intent);
			finish();
		}
//		if(!session.isLoggedIn()){
//			logoutUser();
//		}

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				User object = new User();
				object.USERNAME=username.getText().toString();
				object.PASSWORD=password.getText().toString();

				//new StoreToDB().execute(object).get();
				Toast.makeText(getApplicationContext(), "New user added", Toast.LENGTH_LONG).show();
				try{
				User [] userList= new ReadPersonalDB(object.USERNAME).execute().get();
				String[] list= new String[userList.length];
				for (int i = 0; i < list.length; i++) {
					list[i]= userList[i].PASSWORD;
				}
//					if(list.length==0){
//						Toast.makeText(getApplicationContext(),"List is null", Toast.LENGTH_LONG).show();
//					}
					if(list[0].equals(object.PASSWORD)){
						session.setLogin(true);
						// db.deleteUsers();
						// Inserting row in users table
						startActivity(new Intent(MainActivity.this, EventActivity.class));
					}
					else{
						Toast.makeText(getApplicationContext(),"Authentication failed. Please try again ", Toast.LENGTH_LONG).show();
					}
					}
				catch(Exception e) {
					Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
				}
			}

		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//	private void logoutUser() {
//		session.setLogin(false);
//
//		db.deleteUsers();
//
//		// Launching the login activity
//		Intent intent = new Intent(MainActivity.this, SplashActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		startActivity(intent);
//		finish();
//	}
}
