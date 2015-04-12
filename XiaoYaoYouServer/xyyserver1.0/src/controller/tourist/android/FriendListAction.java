package controller.tourist.android;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import dataobj.FriendListItem;

import service.FriendService;

/**
 * 好友列表
 * @author fan
 * @version 1.0.0
 */
public class FriendListAction implements ServletRequestAware {

	/* 实现servletRequestAware接口需要  */
	private ServletRequest servletRequest;

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	
	//private List<Tourist> friends;
	
	/* 返回好友列表， 不能是String类之后改  */
	//public List<Tourist> getFriendList() {
    //	if (friends != null)
    //		return friends;
    //	else 
    //		return new ArrayList<Tourist>();
    //}
	
	private List<FriendListItem> friendsListItems;

	public List<FriendListItem> getFriendList() {
		if (friendsListItems != null)
	    	return friendsListItems;
	    else 
	    	return new ArrayList<FriendListItem>();
	}
	
	
	//private static TouristDAO tourDao;
	
	private static FriendService friendService;
	
	public FriendListAction() {
		//FriendListAction.tourDao = new TouristDAOHibernateImpl();
		
		//friends = new ArrayList<Tourist>();
		friendsListItems = new ArrayList<FriendListItem>();
		
		friendService = new FriendService();
	}
	
	/* struts动作方法之好友列表  */
	public String show_friends() {
		String username = servletRequest.getParameter("username");
	    System.out.println("FriendListAciton.post::username:" + username);
	
	    /* 如果没有好友，返回“success：false” */
	    //friends = tourDao.findFriends(username); 
	    //System.out.println(friends.get(0).getName());
	    
	    /*friendsListItems = tourDao.findFriendNames(username);
	    */
	    
	    friendsListItems = friendService.getFriendsOfOne(username);
	    
		return "success";
	}
}
