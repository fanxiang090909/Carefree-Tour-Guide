package dao.hibernateImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Admin;
import dao.AdminDAO;
import SessionFactoryMyPackage.HibernateUtil;

public class AdminDAOHibernateImpl implements AdminDAO {

	public void delete(Admin entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;		
	}

	public Admin findByUsername(String username) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Admin  entity = (Admin) session.get(Admin.class, username);
		tx.commit();
		return entity;
	}

	public Admin save(Admin entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}

	public void update(Admin entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();		
	}
}
