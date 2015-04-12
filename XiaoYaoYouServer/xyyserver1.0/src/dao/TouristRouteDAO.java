package dao;

import java.util.Date;
import java.util.List;

import entity.TouristRoutePoint;

public interface TouristRouteDAO {

	/**
	 * 得到最近所有游客的位置
	 * @return
	 */
	public List<TouristRoutePoint> findLatestTouristsPoints(Integer gardenid);

	/**
	 * 得到某一游客当前位置
	 * @param tourname
	 * @return
	 */
	public TouristRoutePoint findTouristRouteCurrentPoint(String tourname);
	
	/**
	 * 得到某一游客的一天内路线
	 * @param tourname
	 * @return
	 */
	public List<TouristRoutePoint> findTouristRoutePoints_OneDay(String tourname);
	
	/**
	 * 删除某游客的当某天的路线记录点
	 * @param username
	 * @param date
	 */
	public void deleteTouristPoints_OneDay(String username, Date day);
	
	/**
	 * 删除某游客的所有路线记录点
	 * @param username
	 */
	public void deleteTouristPoints_All(String username);
	
	/**
	 * 删除实体路线记录点
	 * @param entity
	 */
	public void deletePoint(TouristRoutePoint entity);
	
	/**
	 * 添加实体路线记录点
	 * @param entity
	 * @return 实体路线记录点
	 */
	public TouristRoutePoint savePoint(TouristRoutePoint entity);
	
	/**
	 * 修改实体路线记录点
	 * @param entity
	 */
	public void updatePoint(TouristRoutePoint entity);
}
