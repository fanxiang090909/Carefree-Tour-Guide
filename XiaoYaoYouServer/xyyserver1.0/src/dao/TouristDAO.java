package dao;

import java.util.List;

import dataobj.FriendListItem;

import entity.Tourist;

public interface TouristDAO {
	
	/**
	 * 通过手机号查找游客
	 * @param phone
	 * @return
	 */
	public Tourist findByPhone(Long phone);
	
	/**
	 * 通过游客用户名查找游客
	 * @param touristid
	 * @return
	 */
	public Tourist findByTouristId(String touristid);
	
	/**
	 * 查找好友
	 * @param touristid 自己的账户名
	 * @return
	 */
	public List<Tourist> findFriends(String touristid); 

	/**
	 * 查找好友用户名列表
	 * @param touristid 自己的账户名
	 * @return
	 */
	public List<FriendListItem> findFriendNames(String touristid);
	
	/**
	 * 保存游客
	 * @param entity
	 * @return
	 */
	public Tourist save(Tourist entity);
	
	/**
	 * 删除游客
	 * @param entity
	 */
	public void delete(Tourist entity);
	
	/**
	 * 更新游客
	 * @param entity
	 */
	public void update(Tourist entity);
}