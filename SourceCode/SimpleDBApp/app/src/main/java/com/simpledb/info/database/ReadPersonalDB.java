package com.simpledb.info.database;

import java.util.List;

import com.amazonaws.services.simpledb.model.SelectRequest;
import com.simpledb.info.helper.Connection;
import com.simpledb.info.objects.User;

import android.os.AsyncTask;

public class ReadPersonalDB extends AsyncTask<Void, Void, User[]> {

	static String user;
	public ReadPersonalDB(String pass) {
		this.user = pass;
	}
	public ReadPersonalDB(){

	}

	public static User [] getAllUsers() throws Exception
	{
		SelectRequest selectRequest=  new SelectRequest("select PASSWORD from PersonalInfo where USERNAME='"+user+"'").withConsistentRead(true);
		
		List<com.amazonaws.services.simpledb.model.Item> items  = Connection.getAwsSimpleDB().select(selectRequest).getItems();
		
		try
		{
		com.amazonaws.services.simpledb.model.Item temp1;
		int size= items.size();
		User [] userList= new  User[size];
		
		for(int i=0; i<size;i++)
		{
			temp1= ((com.amazonaws.services.simpledb.model.Item)items.get( i ));

			List<com.amazonaws.services.simpledb.model.Attribute> tempAttribute= temp1.getAttributes();
			userList[i]= new User();
			for(int j=0; j< tempAttribute.size();j++)
			{
				if(tempAttribute.get(j).getName().equals("USERNAME"))
				{
					userList[i].USERNAME=tempAttribute.get(j).getValue();
				}
				else if(tempAttribute.get(j).getName().equals("PASSWORD"))
				{
					userList[i].PASSWORD =tempAttribute.get(j).getValue();
				}
				else if(tempAttribute.get(j).getName().equals("MAJOR"))
				{
					userList[i].MAJOR =tempAttribute.get(j).getValue();
				}
				else if(tempAttribute.get(j).getName().equals("INTEREST"))
				{
					userList[i].INTEREST =tempAttribute.get(j).getValue();
				}
				else if(tempAttribute.get(j).getName().equals("UNIVERSITY"))
				{
					userList[i].UNIVERSITY =tempAttribute.get(j).getValue();
				}
			}
		}
		return userList;
		}
		catch( Exception eex)
		{
			throw new Exception("FIRST EXCEPTION", eex);
		}
	}
	
	@Override
	protected User[] doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			return getAllUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
