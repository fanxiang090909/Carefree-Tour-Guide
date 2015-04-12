package service;

import dao.TouristDAO;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import entity.Tourist;

public class ValidateLogin {
	private static TouristDAO tourDao;
	
	public ValidateLogin() {
		tourDao = new TouristDAOHibernateImpl();
	}

	/**
	 * 验证登陆
	 * @param username 用户名
	 * @param passwd 密码
	 * @return 0 成功， 1没有这个用户名， 2密码错误
	 */
	public int hasRegistered(String username, String passwd) {
		Tourist currentTourist = tourDao.findByTouristId(username);
		/* 如果不为空，继续验证密码 */ 
		if (currentTourist != null) {
			if (passwd.equals(currentTourist.getPasswd()))
				return 0;
			else 
				return 2;
		} else {
			return 1;
		}
	}
}
