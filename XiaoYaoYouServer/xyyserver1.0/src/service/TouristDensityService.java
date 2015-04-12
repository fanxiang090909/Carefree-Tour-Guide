package service;

import java.util.List;

import dao.TouristRouteDAO;
import dao.hibernateImpl.TouristRouteDAOHibernateImpl;
import entity.TouristRoutePoint;

public class TouristDensityService {
	
	private static TouristRouteDAO touristRouteDAO;
	
	public TouristDensityService() {
		TouristDensityService.touristRouteDAO = new TouristRouteDAOHibernateImpl();
	}
	
	public List<TouristRoutePoint> getCurrentDensityPoints(int gardenid) {
		
		//System.out.println("TouristDensityService--getCurrentDensityPoints");
		
		return touristRouteDAO.findLatestTouristsPoints(gardenid);
	} 
}
