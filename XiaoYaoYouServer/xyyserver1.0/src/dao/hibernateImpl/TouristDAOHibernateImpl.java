package dao.hibernateImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.TouristDAO;
import dataobj.FriendListItem;

import SessionFactoryMyPackage.*;
import entity.Tourist;

public class TouristDAOHibernateImpl implements TouristDAO {

	public Tourist findByPhone(Long phone) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
		("SELECT t.* FROM tourist t WHERE t.phone = :phone").addEntity(Tourist.class);
		query.setParameter("phone", phone);
		Tourist usertype = null;
		@SuppressWarnings("unchecked")
		List<Tourist> list = query.list();
		if (list.size() == 1) {
			usertype = list.get(0);
		}
		tx.commit(); 
		return usertype;
	}

	public Tourist findByTouristId(String touristid) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Tourist  entity = (Tourist) session.get(Tourist.class, touristid);
		tx.commit();
		return entity;
	}

	/**
	 * 			SELECT t2.* FROM tourist t2
	 *				JOIN friends f ON t2.username = f.friend2_id 
	 * 				JOIN tourist t1 ON t1.username = f.friend1_id 
	 *				WHERE f.friend1_id = 'fx'
	 *			UNION 
	 *				SELECT t4.* FROM tourist t3
	 *				JOIN friends f ON t3.username = f.friend2_id  
	 *				JOIN tourist t4 ON t4.username = f.friend1_id 
	 *				WHERE f.friend2_id = 'fx'
	 */
	public List<Tourist> findFriends(String touristid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		/* 两次查询求并集，由于friend1和friend2位置可能互换 */
		/* 以下方法有错误不知道为什么
		 public List<Tourist> findFriends(String touristid)
		Query query = session.createSQLQuery
			("SELECT t2.* FROM tourist t2 " +
					"JOIN friends f ON t2.username = f.friend2_id " + 
					"JOIN tourist t1 ON t1.username = f.friend1_id " + 
					"WHERE f.friend1_id = :touristid " +
				"UNION " +
					"SELECT t4.* FROM tourist t3 " +
					"JOIN friends f ON t3.username = f.friend2_id " + 
					"JOIN tourist t4 ON t4.username = f.friend1_id " + 
					"WHERE f.friend2_id = :touristid").addEntity(Tourist.class).addEntity(Tourist.class);
		query.setParameter("touristid", touristid);
		@SuppressWarnings("unchecked")
		List<Tourist> list = query.list();
		
		tx.commit();
		System.out.println("zenmehuishi");

		if (list != null && list.size() > 0) {
			System.out.println(list.get(0).getName());
			return list;
		}
		else 
			return null;
		*/
		tx.commit();

		return null;
	}
	
	/*  有问题？？？？？？？？？？？？？？？？？？？？？？？ */
	public List<FriendListItem> findFriendNames(String touristid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
				("SELECT f.friend2_id " +
						"FROM friends f " +
						"WHERE f.friend1_id = :tourid " + 
						"UNION " + 
						"SELECT f.friend1_id " +
						"FROM friends f " +
						"WHERE f.friend2_id = :tourid "
						);
		query.setParameter("tourid", touristid);
		List<String> list0 = query.list();
		//System.out.println(list0.get(0).toString());
		List<FriendListItem> list = new ArrayList<FriendListItem>();
		/*list.add(new FriendListItem("tour102", 1));
		list.add(new FriendListItem("tour103", 2));
		list.add(new FriendListItem("tour104", 3));
		list.add(new FriendListItem("tour105", 4));
		list.add(new FriendListItem("tour106", 5));
		 */
		/* ????????????????? */
		
		tx.commit();		
		
		if (list != null && list.size() > 0)
			return list;
		else 
			return null;
	}
	
	public Tourist save(Tourist entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}

	public void delete(Tourist entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public void update(Tourist entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		/* 务必使用merge方法  Illegal attempt to associate a collection with two open sessions */
		session.merge(entity);
		tx.commit();
		return;
	}

}
