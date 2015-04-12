package service;

import java.util.Date;

import org.hibernate.HibernateException;

import dao.AdminDAO;
import dao.GardenDAO;
import dao.WarningDAO;
import dao.hibernateImpl.AdminDAOHibernateImpl;
import dao.hibernateImpl.GardenDAOHibernateImpl;
import dao.hibernateImpl.WarningDAOHibernateImpl;
import entity.Admin;
import entity.Garden;
import entity.Warning;

public class WarningService {
	
	private static AdminDAO adminDao;
	private static GardenDAO gardenDao;
	private static WarningDAO warningDao;
	
	public WarningService () {
		adminDao = new AdminDAOHibernateImpl();
		gardenDao = new GardenDAOHibernateImpl();
		warningDao = new WarningDAOHibernateImpl();
	}
	
	public int sendWarningAction(String adminid, String warning, int level, Integer gardenid) {
		Admin admin = adminDao.findByUsername(adminid);
		Garden garden = gardenDao.findById(gardenid);
		
		if (garden != null && admin != null) {
			try {
				/* 消息还未发送，boolean值为false */
				Warning newWarning = new Warning(new Date(), warning, 1, garden, admin);
				
				warningDao.save(newWarning);
			} catch (HibernateException he) {
				System.out.println("sendWarning--HibernateException");
				he.printStackTrace();
				return 1;
			}
			/* 发送成功！ */
			return 0;
		} else {
			System.out.println("sendWarning--未知错误");
			return 2;
		}
	}
}
