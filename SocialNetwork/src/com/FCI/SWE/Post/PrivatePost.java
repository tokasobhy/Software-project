package com.FCI.SWE.Post;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class PrivatePost extends PrivacyPost{

	public PrivatePost(String postOwner, String postContent, int numOfLikes,
						int membersShares,String hashTag,String membersToShow, Post myPost) {
		super(postOwner, postContent, numOfLikes,membersShares,hashTag, membersToShow, myPost);
		this.privacy ="privatePost";
		this.membersToShow = "Friends";
	}

	@Override
	/**
	 * This method will be used to save post object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public boolean writePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		Entity employee = new Entity("post", list.size() + 2);
		
	/*	System.out.println("postOwner= "+postOwner);
		System.out.println("postContent="+postContent);
		System.out.println("hashTag= "+hashTag);
		System.out.println("membersToShow = "+membersToShow);
		System.out.println("privacy = "+privacy);
		System.out.println("timeLineOwner"+myPost.timeLineOwner);*/
		
		employee.setProperty("postOwner", this.postOwner);
		employee.setProperty("postContent", this.postContent);
		employee.setProperty("numOfLikes", this.numOfLikes);
		employee.setProperty("hashTag", this.hashTag);
		employee.setProperty("membersToShow", this.membersToShow);
		employee.setProperty("timeLineOwner", this.myPost.timeLineOwner);
		employee.setProperty("feeling", this.myPost.feeling);
		employee.setProperty("postType", this.myPost.postType);
		employee.setProperty("pageOwner", this.myPost.pageOwner);
		employee.setProperty("numOfSeen", this.myPost.numOfSeen);
		employee.setProperty("privacy", this.privacy);
		employee.setProperty("membersToShow", this.membersToShow);
		employee.setProperty("membersShares", this.membersShares);
		
		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		       // System.out.println("3");
		    }
			//System.out.println("4");
		}
		//System.out.println("5");
		return true;
	}
	@Override
	public void setAllowedMwmbers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void privacyPost(Post p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPostOwner() {
		return this.postOwner;
	}

	@Override
	public void setPostOwner(String postOwner) {
		this.postOwner = postOwner;
		
	}

	@Override
	public String getPostContent() {
		return this.postContent;
	}

	@Override
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	@Override
	public int getNumOfLikes() {
		return this.numOfLikes;
	}

	@Override
	public void setNumOfLikes(int numOfLikes) {
		this.numOfLikes = numOfLikes;
	}

	@Override
	public String getPrivacy() {
		return this.privacy;
	}

	@Override
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	@Override
	public int getMembersShares() {
		return this.membersShares;
	}

	@Override
	public void setMembersShares(int membersShares) {
	   this.membersShares = membersShares;
	}
	
	@Override
	public Post getMyPost() {
		
		return this.myPost;
	}

	@Override
	public void setMyPost(Post myPost) {
		this.myPost = myPost;
		
	}

	@Override
	public String getpostType() {
		return this.postType;
	}

	@Override
	public void setpostType(String postType) {
		this.postType = postType;
		
	}
	
	@Override
	public String getMembersToShow() {
		return this.membersToShow;
		
	}

	@Override
	public void setMembersToShow(String membersToShow) {
		this.membersToShow = membersToShow;
	}
	

	@Override
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	@Override
	public String getHashTag() {
		return this.hashTag;
	}
}
