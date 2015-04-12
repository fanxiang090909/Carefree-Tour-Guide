package service;

import java.util.Date;

import org.hibernate.HibernateException;

import dao.FeedbackDAO;
import dao.GardenDAO;
import dao.TouristDAO;
import dao.hibernateImpl.FeedbackDAOHibernateImpl;
import dao.hibernateImpl.GardenDAOHibernateImpl;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import entity.Feedback;
import entity.Garden;
import entity.Tourist;

public class FeedbackService {

	/**
	 * 评论数据访问接口对象, 无需set get方法
	 */
	private static FeedbackDAO feedbackDao;
	private static TouristDAO tourDao;
	private static GardenDAO gardenDao;
		
	public FeedbackService() {
		FeedbackService.feedbackDao = new FeedbackDAOHibernateImpl();
		FeedbackService.tourDao = new TouristDAOHibernateImpl();
		FeedbackService.gardenDao = new GardenDAOHibernateImpl();
	}
	
	public int sendFeedback(String username, String feedback, int gardenid) {
		Tourist tourist = tourDao.findByTouristId(username);
		Garden garden = gardenDao.findById(gardenid);
		if (garden != null && tourist != null) {
			try {
				/* 消息还未发送，boolean值为false */
				Feedback newFeedback = new Feedback(feedback, new Date(), tourist, garden);
				feedbackDao.save(newFeedback);
			} catch (HibernateException he) {
				System.out.println("sendFeedback--HibernateException");
				he.printStackTrace();
				return 1;
			}
			/* 发送成功！ */
			return 0;
		} else {
			System.out.println("sendFeedback--未知错误");
			return 2;
		}
	}
	
	
}
