package controller.admin;

import java.util.ArrayList;
import java.util.List;

import dao.FeedbackDAO;
import dao.hibernateImpl.FeedbackDAOHibernateImpl;
import entity.Feedback;

/**
 * 浏览器用户反馈
 * @author fan
 * @version 1.0.0
 */
public class FeedbackAction {
		
	/**
	 * 反馈页面，一页显示十个，在FeedbackDAO中定义的static变量中
	 */
	private int page;
	
	public int getPage() {
		return page;
	}
	
    /**
     * 反馈列表，要返回信息时用，只返回十条评论
     */
    private List<Feedback> feedbacks;
    
    public List<Feedback> getFeedbacks() {
    	   	
    	return feedbacks;
    }
	
	/**
	 * 评论数据访问接口对象, 无需set get方法
	 */
	private static FeedbackDAO feedbackDao;
	
	public FeedbackAction() {
		FeedbackAction.feedbackDao = new FeedbackDAOHibernateImpl();
		feedbacks = new ArrayList<Feedback>();
		page = 1;
	}
	
	public String load_feedbacks() {
		System.out.println("AdminFeedbackAction");
		
		/* 此处29101是华山园区的id，存储在garden表中，应该从前台获得 */
		feedbacks = feedbackDao.findGardenLastFeedback(page, 29101);
		if (feedbacks != null)
			return "success";
		else {
			feedbacks = new ArrayList<Feedback>();
			return "success";
		}
	}
}
