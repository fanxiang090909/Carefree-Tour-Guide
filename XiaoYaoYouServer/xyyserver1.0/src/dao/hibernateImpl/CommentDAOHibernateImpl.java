package dao.hibernateImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import SessionFactoryMyPackage.HibernateUtil;

import dao.CommentDAO;
import entity.Comment;

public class CommentDAOHibernateImpl implements CommentDAO {

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
	public List<Comment> findSpotLastComments(int indexOfPage, long spot_id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("SELECT c.* FROM comment c " +
					"JOIN info i ON i.info_id = c.info_id " +
					"WHERE i.spot_id = :spotid " +
					"ORDER BY c.com_time DESC " +
					"LIMIT :startItem, :pageCapacity").addEntity(Comment.class);
		query.setParameter("spotid", spot_id);
		query.setParameter("startItem", (indexOfPage - 1) * CommentDAO.pageCapacity);
		query.setParameter("pageCapacity", CommentDAO.pageCapacity);
		@SuppressWarnings("unchecked")
		List<Comment> list = query.list();
		tx.commit();
		if (list != null && list.size() > 0) {
			return list;
		}
		else 
			return null;
	}

	public void delete(Comment entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public Comment save(Comment entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	public void update(Comment entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		return;
	}
}
