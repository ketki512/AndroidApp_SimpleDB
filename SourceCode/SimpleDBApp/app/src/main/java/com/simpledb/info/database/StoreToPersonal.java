package com.simpledb.info.database;

import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;

import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.simpledb.info.helper.Connection;
import com.simpledb.info.objects.User;

public class StoreToPersonal extends AsyncTask<User, Void, Void>  {

	public static void saveUser(String username, String password, String major, String interest, String university, String tutor)
	{
		try {

			 Connection.getAwsSimpleDB().createDomain(new CreateDomainRequest("PersonalInfo"));
			 List<ReplaceableAttribute> attribute= new ArrayList<ReplaceableAttribute>(1);
			 attribute.add(new ReplaceableAttribute().withName("USERNAME").withValue(username));
			 attribute.add(new ReplaceableAttribute().withName("PASSWORD").withValue(password));
			 attribute.add(new ReplaceableAttribute().withName("MAJOR").withValue(major));
			 attribute.add(new ReplaceableAttribute().withName("INTEREST").withValue(interest));
			 attribute.add(new ReplaceableAttribute().withName("UNIVERSITY").withValue(university));
			attribute.add(new ReplaceableAttribute().withName("TOPIC").withValue(tutor));
			Connection.awsSimpleDB.putAttributes(new PutAttributesRequest("PersonalInfo",username, attribute));
			
		} catch (Exception e) {
				System.out.println(e.getMessage());
		}
	}
	
	@Override
	protected Void doInBackground(User... params) {
		// TODO Auto-generated method stub
		saveUser(params[0].USERNAME, params[0].PASSWORD, params[0].MAJOR, params[0].INTEREST, params[0].UNIVERSITY,"");
		return null;
	}



	
}
