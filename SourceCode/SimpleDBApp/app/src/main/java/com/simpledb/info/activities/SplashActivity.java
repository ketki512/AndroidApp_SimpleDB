package com.simpledb.info.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpledb.info.R;
import com.simpledb.info.helper.SQLiteHandler;
import com.simpledb.info.helper.SessionManager;

/**
 * Created by KETKI on 10/30/2015.
 */
public class SplashActivity extends Activity {
    private ImageView logo;
    private TextView welcome;
    private Button login;
    private Button register;

    private SessionManager session;
    private SQLiteHandler db;
    private static long SLEEP_TIME = 3;    // Sleep for some time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        logo = (ImageView) findViewById(R.id.imgLogo);
        logo.setAlpha(1.0F);
        Animation anim_logo = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        logo.startAnimation(anim_logo);


        welcome = (TextView) findViewById(R.id.welcome_text);
        Animation anim_welcome = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        welcome.startAnimation(anim_welcome);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);


        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {

            //Login and Register Button Click Listener
            login.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    // viewFlipper.showNext();
                    Intent i = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(i);
                    finish();

                }
            });

            register.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Intent i = new Intent(getApplicationContext(),
                            RegisterActivity.class);
                    startActivity(i);
                    finish();

                }
            });
        } else {
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);

        }

        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }


    private class IntentLauncher extends Thread {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

        @Override
        public void run() {
            if (session.isLoggedIn()) {
                try {
                    // Sleeping
                    Thread.sleep(SLEEP_TIME * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, FeedActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }
    }
}
