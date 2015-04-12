package dao.hibernateImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import SessionFactoryMyPackage.HibernateUtil;
import dao.FeedbackDAO;
import dao.TouristCallDAO;
import entity.Feedback;
import entity.TouristCall;

public class TouristCallDAOHibernateImpl 
	implements TouristCallDAO {
	

	public List<TouristCall> findGardenTouristCall(int garden_id) {
		/* garden_id暂时不考虑 */
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("SELECT tc.* FROM tour_call tc " +
					"WHERE tc.call_is_send = 0").addEntity(TouristCall.class);
		//query.setParameter("gardenid", garden_id);
		@SuppressWarnings("unchecked")
		List<TouristCall> list = query.list();
		tx.commit();
		if (list != null && list.size() > 0) {
			return list;
		}
		else 
			return null;
	}
	
	
	public void delete(TouristCall entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public TouristCall save(TouristCall entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	public void update(TouristCall entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
		return;
	}

	
}
