package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.lang.Object;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Controller.CurrentUser;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
//import com.google.appengine.repackaged.com.google.common.base.Flag.Boolean;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/
	
	/*@POST
	@Path("/SearchService")
	public String searchFriend(@FormParam("uname") String uname){
		
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}
//----------------------------------------
	/**
	 * Add Rest Service, this service will be called to make add request process
	 * also will check user data and returns String
	 * @param Sender provided Sender name
	 * @param Reciever provided Reciever name
	 * @return Status provided Status
	 */
	@POST
	@Path("/AddService")
	public String AddService(@FormParam("Sender") String Sender,
			@FormParam("Reciever") String Reciever, @FormParam("Status") String Status) {
		UserEntity user2 = new UserEntity(Sender, Reciever, Status);
		user2.saveRequest();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}	
//--------------------------------------------------
	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();

	}
//---------------------------------------------------------------------------------------------------------
	/**
	 * search Rest Service, this service will be called to make search process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 */
	@POST
	@Path("/searchService")
	public String searchService(@FormParam("uname") String uname) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();
	}
//---------------------------------------------------------------------------------------------------------------
	/**
	 * accept Rest Service, this service will be called to make acceptance process
	 * @param uname provided Sender name
	 * @param Reciever provided Reciever name
	 * @return String
	 */
	@Path("/acceptService")
	public String acceptService(@FormParam("Sender") String uname,@FormParam("Reciever") String Reciever) {
		JSONObject object = new JSONObject();
		boolean user = UserEntity.getAndAcceptFriend(uname,Reciever);
		if (user == true) {
			object.put("Status", "OK");
			System.out.println("1");
		} else {
			object.put("Status", "Failed");
				}
		return object.toString();
	}
//----------------------------------------------------------------------------------------------------------
	/**
	 * search Rest Service, this service will be called to make search process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 */
	@POST
	@Path("/searchService2")
	public String searchService2(@FormParam("uname") String uname) {
		Vector<UserEntity> users = UserEntity.searchUser(uname);
		org.json.simple.JSONArray returnedJson = new org.json.simple.JSONArray();
		for (UserEntity user : users){
			JSONObject object = new JSONObject();
			object.put("id", user.getId());
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			returnedJson.add(object);
		}
		return returnedJson.toString();
	   }
//---------------------------------------------------------------------------------------------------------
	/**
	 * get All Users Rest Service, this service will be called to make load process
	 * also will check friends data and returns friends from datastore
	 * @param 
	 * returns jison object
	 */
	@POST
	@Path("/getAllUsersService")
	public String getAllFriendsService(@FormParam("chatMaker") String chatMaker) {
		Vector<UserEntity> users = UserEntity.getUsers(chatMaker);
		org.json.simple.JSONArray returnedJson = new org.json.simple.JSONArray();
		for (UserEntity user : users){
			JSONObject object = new JSONObject();
			object.put("id", user.getId());
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			returnedJson.add(object);
		}
		return returnedJson.toString();
	}
//--------------------------------------------------------------------------------------------------
	/**
	 * makeConversation Rest Service, this service will be called to make makeConversation process
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/makeConversationService")
	public String makeConversationService(@FormParam("cname") String cname,
			@FormParam("message") String message,@FormParam("members") String members) {
		JSONObject object = new JSONObject();
		UserEntity user2 = new UserEntity(cname, message, members);
		
		//System.out.println("M = "+members);
		
		user2.makeConv();
		object.put("Status", "OK");
		return object.toString();
	}
//-----------------------------------------------------------------------------------------------------
	/**
	 * Add Rest Service, this service will be called to make add request process
	 * also will check user data and returns String
	 * @param Sender provided Sender name
	 * @param Reciever provided Reciever name
	 * @return Status provided Status
	 */
	@POST
	@Path("/Messageservice")
	public String Messageservice(@FormParam("Sender") String Sender,
			@FormParam("Reciever") String Reciever, @FormParam("message") String message) {
		System.out.println(message);
		UserEntity user2 = new UserEntity(Sender, Reciever, message);
		user2.savemessage();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}
}