package dao;

import java.util.List;

import entity.Comment;

public interface CommentDAO {

	/**
	 * pageCapacity 每页的评论数量
	 */
	public static int pageCapacity = 10;
	
	/**
	 * 查找某一景点的某页评论列表
	 * 按从最近到最早顺序排序
	 * @param indexOfPage 第几页，从1-？
	 * @param spot_id 景点号
	 * @return 查找spot_id景点indexOfPage评论列表
	 */
	public List<Comment> findSpotLastComments(int indexOfPage, long spot_id);
	
	public void delete(Comment entity);
	
	public Comment save(Comment entity);
	
	public void update(Comment entity);
	
}