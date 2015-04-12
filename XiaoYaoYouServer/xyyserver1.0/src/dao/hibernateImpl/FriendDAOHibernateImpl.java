package dao.hibernateImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import SessionFactoryMyPackage.HibernateUtil;
import dao.FriendDAO;
import entity.Friends;

public class FriendDAOHibernateImpl implements FriendDAO {

	public List<Friends> findMyFriends(String myusername) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
				("SELECT f.* " +
						"FROM friends f " +
						"WHERE f.friend1_id = :tourid " + 
						"AND f.confirm = 1 " + 
						"UNION " + 
						"SELECT f.* " +
						"FROM friends f " +
						"WHERE f.friend2_id = :tourid " +
						"AND f.confirm = 1 "
						).addEntity(Friends.class);		
		query.setParameter("tourid", myusername);  
		@SuppressWarnings("unchecked")
		List<Friends> list = query.list();
		tx.commit();
		if (list != null && list.size() > 0) {
			return list;
		}
		else 
			return null;
	}
	
	public void delete(Friends entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public Friends save(Friends entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	public void update(Friends entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		return;
	}
}
