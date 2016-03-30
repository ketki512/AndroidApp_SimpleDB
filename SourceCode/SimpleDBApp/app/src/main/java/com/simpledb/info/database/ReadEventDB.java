package com.simpledb.info.database;

import android.os.AsyncTask;

import com.amazonaws.services.simpledb.model.SelectRequest;
import com.simpledb.info.helper.Connection;
import com.simpledb.info.objects.EventFeed;

import java.util.List;

/**
 * Created by KETKI on 11/16/2015.
 */
public class ReadEventDB extends AsyncTask<Void, Void, EventFeed[]> {
    static String query;
    public ReadEventDB(String query){
        this.query=query;
    }

    public static EventFeed[] getFeed() throws Exception
    {
        SelectRequest selectRequest=  new SelectRequest(query).withConsistentRead(true);

        List<com.amazonaws.services.simpledb.model.Item> items  = Connection.getAwsSimpleDB().select(selectRequest).getItems();

        try
        {
            com.amazonaws.services.simpledb.model.Item temp1;
            int size= items.size();
            EventFeed [] eventList= new  EventFeed[size];

            for(int i=0; i<size;i++) {
                temp1 = ((com.amazonaws.services.simpledb.model.Item) items.get(i));

                List<com.amazonaws.services.simpledb.model.Attribute> tempAttribute = temp1.getAttributes();
                eventList[i] = new EventFeed();
                for (int j = 0; j < tempAttribute.size(); j++) {
                    if (tempAttribute.get(j).getName().equals("TOPIC")) {
                        eventList[i].TOPIC = tempAttribute.get(j).getValue();
                    } else if (tempAttribute.get(j).getName().equals("EVENTQUESTION")) {
                        eventList[i].EVENTQUESTION = tempAttribute.get(j).getValue();
                    } else if (tempAttribute.get(j).getName().equals("DATE")) {
                        eventList[i].DATE = tempAttribute.get(j).getValue();
                    } else if (tempAttribute.get(j).getName().equals("UNIVERSITY")) {
                        eventList[i].UNIVERSITY = tempAttribute.get(j).getValue();
                    }
                }
            }
            return eventList;
        }
        catch( Exception eex)
        {
            throw new Exception("FIRST EXCEPTION", eex);
        }
    }

    @Override
    protected EventFeed[] doInBackground(Void... params) {
        // TODO Auto-generated method stub
        try {
            return getFeed();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
