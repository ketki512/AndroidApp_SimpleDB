package com.simpledb.info.database;

import android.os.AsyncTask;

import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.simpledb.info.helper.Connection;
import com.simpledb.info.objects.EventFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KETKI on 11/16/2015.
 */
public class StoreToFeed extends AsyncTask<EventFeed, Void, Void> {

    public static void saveFeed(String topic, String creator, String eventquestion, String date, String university, String tutor, String number)
    {
        try {
            Connection.getAwsSimpleDB().createDomain(new CreateDomainRequest("EventInfo"));
            List<ReplaceableAttribute> attribute= new ArrayList<ReplaceableAttribute>(1);
            attribute.add(new ReplaceableAttribute().withName("TOPIC").withValue(topic));
            attribute.add(new ReplaceableAttribute().withName("CREATOR").withValue(creator));
            attribute.add(new ReplaceableAttribute().withName("EVENTQUESTION").withValue(eventquestion));
            attribute.add(new ReplaceableAttribute().withName("DATE").withValue(date));
            attribute.add(new ReplaceableAttribute().withName("UNIVERSITY").withValue("RIT"));
            attribute.add(new ReplaceableAttribute().withName("TUTOR").withValue(""));
            attribute.add(new ReplaceableAttribute().withName("NUMBER OF STUDENTS").withValue(""));

            Connection.awsSimpleDB.putAttributes(new PutAttributesRequest("EventInfo",topic, attribute));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected Void doInBackground(EventFeed... params) {
        // TODO Auto-generated method stub
        saveFeed(params[0].TOPIC, "                     "  , params[0].EVENTQUESTION, params[0].DATE,params[0].UNIVERSITY,"","1");
        return null;
    }


}
