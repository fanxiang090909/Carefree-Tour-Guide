package dao.hibernateImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import SessionFactoryMyPackage.HibernateUtil;

import dao.SpotDAO;
import entity.Spot;

public class SpotDAOHibernateImpl implements SpotDAO {

	public List<Spot> findTypeSpotsByGardenId(Integer gardenId, SpotType type) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("SELECT s.* FROM spot s WHERE s.garden_id = :gardenid and s.type = :type").addEntity(Spot.class);
		query.setParameter("gardenid", gardenId);
		/* java枚举类型为string，其ordinal()从0开始  */
		query.setParameter("type", type.ordinal() + 1);
		@SuppressWarnings("unchecked")
		List<Spot> list = query.list();
		tx.commit();
		if (list != null && list.size() > 0) {
			return list;
		}
		else 
			return null;
	}

	public Spot findSpotById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Spot entity = (Spot) session.get(Spot.class, id);
		tx.commit();
		return entity;
	}

	public void delete(Spot entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public Spot save(Spot entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	public void update(Spot entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		return;
	}
}
