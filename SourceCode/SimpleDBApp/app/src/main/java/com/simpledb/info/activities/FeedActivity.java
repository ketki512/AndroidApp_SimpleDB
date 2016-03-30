package com.simpledb.info.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.simpledb.info.objects.EventFeed;
import com.simpledb.info.helper.ExpandableListAdapterLA;
import com.simpledb.info.R;
import com.simpledb.info.database.ReadEventDB;
import com.simpledb.info.database.StoreToFeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KETKI on 11/16/2015.
 */
public class FeedActivity extends Activity{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        try {
            EventFeed[] eventList= new ReadEventDB("select EVENTQUESTION from EventInfo where UNIVERSITY = 'RIT'").execute().get();
            String[] list= new String[eventList.length];
            for (int i = 0; i < list.length; i++) {
                listDataHeader.add(eventList[i].EVENTQUESTION);
                List<String> event1 = new ArrayList<String>();
                event1.add("Join");
                event1.add("Teach");
                listDataChild.put(eventList[i].EVENTQUESTION, event1);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        listAdapter = new ExpandableListAdapterLA(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                if(listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition).equals("Join")){
                    try {
                        EventFeed [] eventList= new ReadEventDB("select EVENTQUESTION, TOPIC, DATE,NUMBER OF STUDENTS, TUTOR, UNIVERSITY " +
                                "from EventInfo where EVENTQUESTION=='"+listDataHeader.get(groupPosition)+"'").execute().get();
                        //ADD AGAIN
                        new StoreToFeed().saveFeed(eventList[0].TOPIC,eventList[0].CREATOR, eventList[0].EVENTQUESTION, eventList[0].DATE,
                                eventList[0].UNIVERSITY, eventList[0].TUTOR, Integer.toString((Integer.parseInt(eventList[0].NUMBER_OF_STUDENTS))+1) );
                        Toast.makeText(getApplicationContext(),"Event joined",Toast.LENGTH_LONG).show();
                    }
                   catch(Exception e){

                   }
                }
                else{
                    try {
                        EventFeed [] eventList= new ReadEventDB("select EVENTQUESTION, TOPIC, DATE,NUMBER OF STUDENTS, TUTOR, UNIVERSITY " +
                                "from EventInfo where EVENTQUESTION=='"+listDataHeader.get(groupPosition)+"'").execute().get();
                        //ADD AGAIN
                        new StoreToFeed().saveFeed(eventList[0].TOPIC,eventList[0].CREATOR, eventList[0].EVENTQUESTION, eventList[0].DATE,
                                eventList[0].UNIVERSITY, "                   ", Integer.toString((Integer.parseInt(eventList[0].NUMBER_OF_STUDENTS))+1) );

                        Toast.makeText(getApplicationContext(),"Added as a tutor",Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e){

                    }
                }

                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.get, menu);
        return true;
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_add:
                    startActivity(new Intent(FeedActivity.this, EventActivity.class));
                    return true;
                case R.id.action_logout:

                default:
                    return super.onOptionsItemSelected(item);
            }
        }



}
