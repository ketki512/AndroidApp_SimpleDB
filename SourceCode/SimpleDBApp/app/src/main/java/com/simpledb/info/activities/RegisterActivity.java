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
import com.simpledb.info.database.StoreToPersonal;
import com.simpledb.info.objects.User;
import com.simpledb.info.helper.SQLiteHandler;
import com.simpledb.info.helper.SessionManager;

public class RegisterActivity extends Activity {
	private TextView username;
	private TextView password;
    private TextView major;
	private TextView interest;
	private TextView university;
	private Button register;
	private SessionManager session;
	private SQLiteHandler db;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		register= (Button) findViewById(R.id.btnRegister);
		username=(TextView)findViewById(R.id.username);
		password=(TextView)findViewById(R.id.password);
		major=(TextView)findViewById(R.id.major);
		interest =(TextView)findViewById(R.id.interest);
		university =(TextView)findViewById(R.id.university);

		session = new SessionManager(getApplicationContext());

		// SQLite database handler
		db = new SQLiteHandler(getApplicationContext());

		// Check if user is already logged in or not
		if (session.isLoggedIn()) {
			// User is already logged in. Take him to main activity
			Intent intent = new Intent(RegisterActivity.this,
					FeedActivity.class);
			startActivity(intent);
			finish();
		}
		register.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {


					try {// TODO Auto-generated method stub
						User object = new User();
						object.USERNAME=username.getText().toString();
						object.PASSWORD=password.getText().toString();
						object.MAJOR= major.getText().toString();
						object.INTEREST= interest.getText().toString();
						object.UNIVERSITY= university.getText().toString();

					new StoreToPersonal().execute(object).get();
						startActivity(new Intent(RegisterActivity.this, FeedActivity.class));
					    Toast.makeText(getApplicationContext(), "New user added", Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), object.USERNAME, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), object.PASSWORD, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), object.MAJOR, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), object.INTEREST, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), object.UNIVERSITY, Toast.LENGTH_LONG).show();
						db.addUser(object.USERNAME,object.PASSWORD,object.MAJOR,object.INTEREST,object.UNIVERSITY);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			});

}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.store, menu);
	return true;
	}


}
