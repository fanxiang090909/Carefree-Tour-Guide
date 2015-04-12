package dao.hibernateImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.WarningDAO;

import SessionFactoryMyPackage.HibernateUtil;
import entity.Tourist;
import entity.TouristCall;
import entity.Warning;
import entity.Warning;

public class WarningDAOHibernateImpl implements WarningDAO {
	
	public void delete(Warning entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public Warning save(Warning entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	public void update(Warning entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		return;
	}


	public List<Warning> findGardenWarning(int garden_id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
		("SELECT w.* FROM warning w WHERE w.warn_is_send = 0 " +
				"AND w.garden_id = :garden_id").addEntity(Warning.class);
		query.setParameter("garden_id", garden_id);
		List<Warning> list = query.list();
		tx.commit(); 
		if (list!= null && list.size() != 0) {
			return list;
		}
		return null;
	}

	public Warning findById(Integer warningid) {
		//System.out.println("WarningDAOHibernateImpl:findbyid:");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Warning  entity = (Warning) session.get(Warning.class, warningid);
		//System.out.println(entity.getMessage());
		tx.commit();
		return entity;
	}

	/**
	 * 只返回最新的那个
	 */
	public Warning findGardenLatestWarning(int garden_id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
		("SELECT w.* FROM warning w " +
				"WHERE w.garden_id = :garden_id " + 
				"ORDER BY w.warn_time DESC " + 
				"LIMIT 1").addEntity(Warning.class);
		query.setParameter("garden_id", garden_id);
		List<Warning> list = query.list();
		tx.commit(); 
		if (list!= null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

}
