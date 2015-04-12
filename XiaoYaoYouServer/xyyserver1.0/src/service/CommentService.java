package service;

import java.util.Date;

import org.hibernate.HibernateException;

import dao.CommentDAO;
import dao.SpotDAO;
import dao.TouristDAO;
import dao.hibernateImpl.CommentDAOHibernateImpl;
import dao.hibernateImpl.SpotDAOHibernateImpl;
import dao.hibernateImpl.TouristDAOHibernateImpl;

import entity.Comment;
import entity.Spot;
import entity.Tourist;

public class CommentService {

	/**
	 * 评论数据访问接口对象, 无需set get方法
	 */
	private static CommentDAO commentDao;
	private static TouristDAO tourDao;
	private static SpotDAO spotDao;
		
	public CommentService() {
		CommentService.commentDao = new CommentDAOHibernateImpl();
		CommentService.tourDao = new TouristDAOHibernateImpl();
		CommentService.spotDao = new SpotDAOHibernateImpl();
	}
	
	/**
	 * 
	 * @param username
	 * @param comment
	 * @param commentGrade
	 * @param spotid
	 * @param nickname
	 * @return
	 */
	public int sendComment(String username, String comment, Float commentGrade, long spotid, String nickname) {
		Tourist tourist = tourDao.findByTouristId(username);
		//System.out.println(tourist.getPhone());
		Spot spot = spotDao.findSpotById(spotid);
		//System.out.println(spotid);

		//System.out.println(spot.getName());
		//System.out.println(spot.getInfo().getContext());

		if (spot != null && spot.getInfo() != null && tourist != null) {
			try {
				Comment newComment = 
						new Comment(comment, commentGrade, new Date(), 
								tourist, nickname, spot.getInfo());
				commentDao.save(newComment);
			} catch (HibernateException he) {
				System.out.println("sendComment--HibernateException");
				he.printStackTrace();
				return 1;
			}
			/* 发送成功！ */
			return 0;
		} else {
			System.out.println("sendComment--未知错误");
			return 2;
		}
	}
	
	
}
