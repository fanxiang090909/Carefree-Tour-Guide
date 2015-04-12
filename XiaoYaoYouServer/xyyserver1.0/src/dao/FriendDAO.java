package dao;

import java.util.List;

import entity.Friends;

public interface FriendDAO {
	
	/**
	 * 找我的好友
	 * @param myusername
	 * @return Friend的类list
	 */
	public List<Friends> findMyFriends(String myusername);
	
	public void delete(Friends entity);
	
	public Friends save(Friends entity);
	
	/**
	 * 更新景点
	 * @param entity
	 */
	public void update(Friends entity);
}
