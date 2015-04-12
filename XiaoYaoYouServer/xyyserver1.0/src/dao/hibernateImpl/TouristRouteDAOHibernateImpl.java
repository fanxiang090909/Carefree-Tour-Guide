package dao.hibernateImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import SessionFactoryMyPackage.HibernateUtil;

import dao.TouristRouteDAO;
import entity.TouristRoutePoint;

public class TouristRouteDAOHibernateImpl 
			implements TouristRouteDAO {
	
	/**
	 * 查找客流密度时使用，最近各个游客的位置坐标集合
	 */
	public List<TouristRoutePoint> findLatestTouristsPoints(Integer gardenid) {
		System.out.println("hib---findLatestTouristsPoints");

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("SELECT tr.* FROM tourists_route tr " + 
					"GROUP BY tour_id " + 
					"ORDER BY tr.route_time DESC").addEntity(TouristRoutePoint.class);
		@SuppressWarnings("unchecked")
		List<TouristRoutePoint> list = query.list();
		tx.commit();
		System.out.println("end---findLatestTouristsPoints");

		if (list != null && list.size() > 0) {
			return list;
		}
		else 
			return null;
	}
	
	/**
	 * 得到当前位置
	 */
	public TouristRoutePoint findTouristRouteCurrentPoint(String tourname) {
		System.out.println("hib---findTouristRouteCurrentPoint");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("SELECT tr.* FROM tourists_route tr " +
					"WHERE tr.tour_id = :username " +
					"ORDER BY tr.route_time DESC ").addEntity(TouristRoutePoint.class);
		query.setParameter("username", tourname);
		@SuppressWarnings("unchecked")
		List<TouristRoutePoint> list = query.list();
		tx.commit();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		else 
			return null;
	}
	
	/**
	 * 得到某一游客的一天内路线
	 * @param tourname
	 * @return
	 */
	public List<TouristRoutePoint> findTouristRoutePoints_OneDay(String tourname) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("SELECT tr.* FROM tourists_route tr " +
					"WHERE tr.tour_id = :username " +
					"ORDER BY tr.route_time DESC " +
					"LIMIT 0, 50").addEntity(TouristRoutePoint.class);
		query.setParameter("username", tourname);
		@SuppressWarnings("unchecked")
		List<TouristRoutePoint> list = query.list();
		tx.commit();
		if (list != null && list.size() > 0) {
			return list;
		}
		else 
			return null;
	}
	
	/**
	 * 删除某游客的当某天的路线记录点
	 * @param username
	 * @param day
	 */
	public void deleteTouristPoints_OneDay(String username, Date day) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("DELETE FROM tourists_route WHERE tour_id = :username");
		query.setParameter("username", username);
		query.executeUpdate();
		tx.commit();
		return;
	}
	
	/**
	 * 删除某游客的所有路线记录点
	 * @param username
	 */
	public void deleteTouristPoints_All(String username) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("DELETE FROM tourists_route WHERE tour_id = :username");
		query.setParameter("username", username);
		System.out.println("清空 " + username);
		query.executeUpdate();
		tx.commit();
		return;
	}
	
	/**
	 * 删除实体路线记录点
	 * @param entity
	 */
	public void deletePoint(TouristRoutePoint entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;		
	}
	
	/**
	 * 添加实体路线记录点
	 * @param entity
	 * @return 实体路线记录点
	 */
	public TouristRoutePoint savePoint(TouristRoutePoint entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	/**
	 * 修改实体路线记录点
	 * @param entity
	 */
	public void updatePoint(TouristRoutePoint entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		return;
	}


}
