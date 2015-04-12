package service;

import java.util.ArrayList;
import java.util.List;

import dao.FriendDAO;
import dao.hibernateImpl.FriendDAOHibernateImpl;
import dataobj.FriendListItem;
import entity.Friends;

public class FriendService {

	private static FriendDAO friendDAO;
	
	public FriendService() {
		FriendService.friendDAO = new FriendDAOHibernateImpl();
	}
	
	public List<FriendListItem> getFriendsOfOne(String username) {
		List<FriendListItem> myFriends = new ArrayList<FriendListItem>();
		
		/* 得到DAO查询返回结果 */
		List<Friends> classfriends = friendDAO.findMyFriends(username);
		if (classfriends == null) {
			return myFriends;
		}
		/* 好友数量不为空才能进行遍历 */
		for (Friends friends : classfriends) {
			if (username.equals(friends.getFriend1().getId())) {
				myFriends.add(new FriendListItem(
						friends.getFriend2().getId(), (int)(Math.random() * 5) + 1));
			} else {
				myFriends.add(new FriendListItem(
						friends.getFriend1().getId(), (int)(Math.random() * 5) + 1));
			}
			System.out.println(friends.getFriend2().getId());
		}
		return myFriends;
	}
}
