package dao;

import java.util.List;

import entity.Feedback;;

public interface FeedbackDAO {
	
	/**
	 * pageCapacity 每页的反馈数量
	 */
	public static int pageCapacity = 10;
	
	/**
	 * 查找某一园区的某页反馈列表
	 * 按从最近到最早顺序排序
	 * @param indexOfPage 第几页，从1-？
	 * @param garden_id 园区号号
	 * @return 查找garden_id园区indexOfPage反馈列表
	 */
	public List<Feedback> findGardenLastFeedback(int indexOfPage, int garden_id);
	
	public void delete(Feedback entity);
	
	public Feedback save(Feedback entity);
	
	public void update(Feedback entity);
}
