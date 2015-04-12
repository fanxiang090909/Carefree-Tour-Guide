package dao.hibernateImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.GardenDAO;

import SessionFactoryMyPackage.HibernateUtil;
import entity.Garden;

public class GardenDAOHibernateImpl implements GardenDAO {

	public Garden findById(Integer gardenid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Garden  entity = (Garden) session.get(Garden.class, gardenid);
		tx.commit();
		return entity;
	}
	
	public void delete(Garden entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public Garden save(Garden entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	public void update(Garden entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		return;
	}
}
