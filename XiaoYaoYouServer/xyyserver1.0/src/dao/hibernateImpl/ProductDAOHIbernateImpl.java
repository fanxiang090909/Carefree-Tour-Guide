package dao.hibernateImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import SessionFactoryMyPackage.HibernateUtil;
import dao.ProductDAO;
import entity.Product;

public class ProductDAOHIbernateImpl implements ProductDAO {

	public List<Product> findSpotLastProducts(int indexOfPage, long spot_id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery
			("SELECT pd.* FROM product_list pd " +
					"JOIN info i ON i.info_id = pd.info_id " +
					"WHERE i.spot_id = :spotid " +
					"ORDER BY pd.pro_price DESC " +
					"LIMIT :startItem, :pageCapacity").addEntity(Product.class);
		query.setParameter("spotid", spot_id);
		query.setParameter("startItem", (indexOfPage - 1) * ProductDAO.pageCapacity);
		query.setParameter("pageCapacity", ProductDAO.pageCapacity);
		@SuppressWarnings("unchecked")
		List<Product> list = query.list();
		tx.commit();
		if (list != null && list.size() > 0) {
			return list;
		}
		else 
			return null;
	}

	public void delete(Product entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		return;			
	}

	public Product save(Product entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return entity;
	}
	
	public void update(Product entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		return;
	}
}
