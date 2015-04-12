package dao.hibernateImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import SessionFactoryMyPackage.HibernateUtil;

import dao.FeedbackDAO;
import entity.Feedback;

public class FeedbackDAOHibernateImpl implements FeedbackDAO {

	/**
	 * 参考：
	 *	SQLServer中:
	 *		SELECT TOP 页大小 *
	 *  	FROM Table1 a WHERE not exists
	 *  	(select * from (select top (页大小*页数) * from Table1 order by id) b where b.id=a.id )
	 *  	order by a.id
	 *  MySQL中:
	 *  	select c.* from comment c
	 *		WHERE c.info_id = 5
	 *		ORDER BY com_time DESC
	 *		limit 20,10
	 * @param indexOfPage
	 * @param spot_id
	 * @return
	 */
	public List<Feedback> findGardenLastFeedback(int indexOfPage, int garden_id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("SELECT f.* FROM feedback f " +
					"WHERE f.garden_id = :gardenid " +
					"ORDER BY f.fb_time DESC " +
					"LIMIT :startItem, :pageCapacity").addEntity(Feedback.class);
		query.setParameter("gardenid", garden_id);
		query.setParameter("startItem", (indexOfPage - 1) * FeedbackDAO.pageCapacity);
		query.setParameter("pageCapacity", FeedbackDAO.pageCapacity);
		@SuppressWarnings("unchecked")
		List<Feedback> list = query.list();
		tx.commit();
		if (list != null && list.size() > 0) {
			return list;
		}
		else 
			return null;
	}

	public void delete(Feedback entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public Feedback save(Feedback entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	public void update(Feedback entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		return;
	}

}
