package com.FCI.SWE.ServicesModels;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Controller.CurrentUser;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;
	private long id;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	private void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

	
	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
				returnedUser.setId(entity.getKey().getId());
				return returnedUser;
			}
		}

		return null;
	}
//------------------------------------------------------------------------------------
	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @return Constructed user entity
	 */
	public static UserEntity getUser(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(name)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
				returnedUser.setId(entity.getKey().getId());
				return returnedUser;
			}
		}

		return null;
	}
//--------------------------------------------------------------------------------------------
	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		Entity employee = new Entity("users", list.size() + 2);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		
		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;

	}
//---------------------------------------------------------
	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveRequest() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Friends");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		Entity employee = new Entity("Friends", list.size() + 2);

		employee.setProperty("Sender", this.name);
		employee.setProperty("Reciever", this.email);
		employee.setProperty("Status", this.password);
		
		datastore.put(employee);
		txn.commit();
		
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
//---------------------------------------------------------------------------------------------------------
	/*public static  Vector<String> getFriendsToShow() {
		Vector<String> List = new Vector<String>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("Frends");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Reciever").toString().equals(CurrentUser.user1.getName())&& entity.getProperty("Status").toString().equals("unaccepted")) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"Sender").toString(), entity.getProperty("Reciever")
						.toString(), entity.getProperty("Status").toString());
				returnedUser.setId(entity.getKey().getId());
				List.add(returnedUser.getName());
			}
			return List;
		}
		return null;
	}*/
//---------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * the name of the user who want to accept 
	 * This method will search for friend in datastore
	 * 
	 * @param name
	 *            user sender
	  * @param Reciever
	 *            user Reciever    
	 * @return boolean if user is Accepted correctly or not
	 */
	public static boolean getAndAcceptFriend(String name,  String Reciever) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		boolean result = false ;
		Query gaeQuery = new Query("Friends");
		Transaction txn = datastore.beginTransaction();
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Sender").toString().equals(name)
					&&entity.getProperty("Reciever").toString().equals(Reciever)) {
			/*	UserEntity returnedUser = new UserEntity(entity.getProperty(
						"Reciever").toString(), entity.getProperty("Sender")
						.toString(), entity.getProperty("Status").toString());
				returnedUser.setId(entity.getKey().getId());*/
				try {
					Entity employee = new Entity("Friends",entity.getKey().getId()) ;

					employee.setProperty("Reciever", Reciever);
					employee.setProperty("Sender", name);
					employee.setProperty("Status", "accepted");
					
					datastore.put(employee);
					txn.commit();
					result = true;
					}finally{
						if (txn.isActive()) {
					        txn.rollback();
					        result = true;
					    }
					}
				result = true;
			}
		}

		
	return result;
	}
//-------------------------------------------------
	/**
	 * 
	 * @param uname
	 * @return
	 */
	public static Vector <UserEntity> searchUser(String uname){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query gae = new Query("users");
		PreparedQuery pq = dataStore.prepare(gae);
		Vector <UserEntity> returnedUsers = new Vector<UserEntity>();
		for( Entity entity : pq.asIterable() ){
			String currentName = entity.getProperty("name").toString();
			if(currentName.contains(uname)){
				UserEntity user = new UserEntity(entity.getProperty("name").toString()
												,entity.getProperty("email").toString()
												,entity.getProperty("password").toString());
					user.setId(entity.getKey().getId());
					returnedUsers.add(user);
			}
		}
		return returnedUsers;
	}
//-------------------------------------------------
	/**
	 * get all users from database
	 * @return list of users
	 */
	public static Vector <UserEntity> getUsers(String chatMaker){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query gae = new Query("users");
		PreparedQuery pq = dataStore.prepare(gae);
		Vector <UserEntity> returnedUsers = new Vector<UserEntity>();
		for( Entity entity : pq.asIterable() ){
			String name = entity.getProperty("name").toString();
			if(!name.equals(chatMaker)){
				UserEntity user = new UserEntity(entity.getProperty("name").toString()
												,entity.getProperty("email").toString()
												,entity.getProperty("password").toString());
					user.setId(entity.getKey().getId());
					returnedUsers.add(user);
			}
		}
		return returnedUsers;
	}
//-----------------------------------------------------------------------------------------------------
	/**
	 * This method will be used to save conversation in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean makeConv() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("conv");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		boolean Mwgood = false; 
		//System.out.println("Size = " + list.size());
		
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("cname").toString().equals(this.name)) {
				UserEntity user = new UserEntity(entity.getProperty("cname").toString()
						,entity.getProperty("message").toString(),entity.getProperty("members").toString());
										
					user.email += "|" + this.email;
					
					try {
						Entity employee = new Entity("conv",entity.getKey().getId());
						employee.setProperty("cname", user.name);
						employee.setProperty("message", user.email);
						employee.setProperty("members", user.password);
						
						datastore.put(employee);
						txn.commit();
						
						}finally{
							if (txn.isActive()) {
						        txn.rollback();
						    }
						}
					Mwgood = true;
			}
		}
		
		if(! Mwgood){
		try {
		Entity employee = new Entity("conv", list.size() + 2);

		employee.setProperty("cname", this.name);
		employee.setProperty("message", this.email);
		employee.setProperty("members", this.password);
		
		datastore.put(employee);
		txn.commit();
		
		String delimiter = ";";
		String[] temp;
		temp = this.password.split(delimiter);
		
		for(int i =0; i < temp.length ; i++){
		makeRelation(list.size() + 2,temp[i]);
		System.out.println(temp[i]);
		}
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
	
		}
		return true;
	}			
//--------------------------------------------------------------------------------------------------
	/**
	 * This method will be used to save conversation in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean makeRelation(int cID,String uname) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("relation");
		Transaction txn = datastore.beginTransaction();
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list1 = pq.asList(FetchOptions.Builder.withDefaults());
		//System.out.println("Size = " + list.size());			
			try {
			
				Entity employee = new Entity("relation", list1.size() + 2);
		
				employee.setProperty("cID", cID);
				employee.setProperty("cname", this.name);
				employee.setProperty("uname", uname);
				System.out.println(cID);
				datastore.put(employee);
				txn.commit();
			
			}finally{
				if (txn.isActive()) {
			        txn.rollback();
			    }
			}
		
		return true;
	}
//----------------------------------------------------------------------------------------------------
	public Boolean savemessage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Message");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		Entity employee = new Entity("Message", list.size() + 2);

		employee.setProperty("Sender", this.name);
		employee.setProperty("Reciever", this.email);
		employee.setProperty("Status", this.password);
		
		datastore.put(employee);
		txn.commit();
		
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
}
