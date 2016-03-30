package com.simpledb.info.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.simpledb.info.objects.EventFeed;
import com.simpledb.info.R;
import com.simpledb.info.database.StoreToFeed;

/**
 * Created by KETKI on 11/15/2015.
 */
public class EventActivity extends Activity {

    private Button add;
    TextView topic;
    TextView event;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        topic = (TextView) findViewById(R.id.topic);
        event = (TextView) findViewById(R.id.event);
        date = (TextView)findViewById(R.id.date);

        add = (Button)findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try {// TODO Auto-generated method stub
                    EventFeed eobject = new EventFeed();
                    eobject.TOPIC = topic.getText().toString();
                    eobject.EVENTQUESTION = event.getText().toString();
                    eobject.DATE = date.getText().toString();

                    new StoreToFeed().execute(eobject).get();
                    Toast.makeText(getApplicationContext(), "New event added", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EventActivity.this,FeedActivity.class));
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
