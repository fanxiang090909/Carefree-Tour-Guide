package service;

import java.util.Date;

import org.hibernate.HibernateException;

import dao.TouristCallDAO;
import dao.TouristDAO;
import dao.hibernateImpl.TouristCallDAOHibernateImpl;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import entity.Tourist;
import entity.TouristCall;

public class TouristCallService {

	private static TouristCallDAO touristCallDao;
	
	private static TouristDAO tourDao;
	
	public TouristCallService() {
		touristCallDao = new TouristCallDAOHibernateImpl();
		tourDao = new TouristDAOHibernateImpl();
	}
	
	/**
	 * 发送游客报警消息，没写完
	 * @param username
	 * @param lat
	 * @param lng
	 * @return
	 */
	public int sendTouristCall(String username, double lat, double lng) {
		Tourist tourist = tourDao.findByTouristId(username);
		try {
			if (tourist != null) 
				touristCallDao.save(new TouristCall(lat, lng, new Date(), false, tourist));
			return 0;

		} catch (HibernateException he) {
			he.printStackTrace();
			return 1;
		}
	}
}
