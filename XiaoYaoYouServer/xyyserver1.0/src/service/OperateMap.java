package service;

import dao.GardenDAO;
import dao.hibernateImpl.GardenDAOHibernateImpl;
import entity.TouristRoutePoint;

public class OperateMap {
	
	private static GardenDAO gardenDao;
	
	public OperateMap() {
		gardenDao = new GardenDAOHibernateImpl();
	}
	
	public Boolean ValidateInGarden(TouristRoutePoint point, Integer gardenid) {
		return true;
	}
		
}
