package dao;

import entity.Admin;

/**
 * 管理员admin数据库访问接�?
 * @author fan
 * @version 1.0.0
 */
public interface AdminDAO {

	/**
	 * 删除管理�?
	 * @param entity
	 */
	public void delete(Admin entity);
	
	/**
	 * 添加管理�?
	 * @param entity
	 * @return
	 */
	public Admin save(Admin entity);
	
	/**
	 * 更新管理�?
	 * @param entity
	 */
	public void update(Admin entity);
	
	/**
	 * 通过用户名获得管理员
	 * @param username
	 * @return
	 */
	public Admin findByUsername(String username);
	
}
