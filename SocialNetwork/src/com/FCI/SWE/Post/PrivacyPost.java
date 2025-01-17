package com.FCI.SWE.Post;

public abstract class PrivacyPost extends Post {
	
	public Post myPost;
	public String privacy;
	public String membersToShow;
	
	public PrivacyPost(String postOwner, String postContent, int numOfLikes,
						int membersShares,String hashTag,String membersToShow, Post myPost) {
		super(postOwner, postContent, numOfLikes, membersShares,hashTag);
		this.myPost = myPost;
		this.membersToShow = membersToShow;
	}


	public abstract Post getMyPost();
	public abstract void setMyPost(Post myPost);
	public abstract String getPrivacy();
	public abstract void setPrivacy(String privacy);
	public abstract String getMembersToShow();
	public abstract void setMembersToShow(String membersToShow);
	public abstract boolean writePost();
	public abstract void setAllowedMwmbers();
	public abstract void privacyPost(Post p);

}
