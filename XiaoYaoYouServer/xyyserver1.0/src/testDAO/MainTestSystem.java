package testDAO;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;

import service.CalculateRouteService;
import service.FriendService;
import service.TouristDensityService;
import service.readMapLine.LinePoint;
import service.readMapLine.RoadLinesReader;

import dao.SpotDAO;
import dao.hibernateImpl.SpotDAOHibernateImpl;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import entity.Spot;
import entity.TouristRoutePoint;

public class MainTestSystem {
	private static TouristDAOHibernateImpl dao = new TouristDAOHibernateImpl();
	private static SpotDAOHibernateImpl spotDao = new SpotDAOHibernateImpl();
	private static TouristDensityService touristDensityService = new TouristDensityService();
	
	private static RoadLinesReader roadLinesReader;
	
	private static CalculateRouteService calculateRouteService;
	
	private static FriendService friendService;
	
	public static void main(String[] args) {
			
		try {
			//dao.findByPhone((long) 123);
			//System.out.println(dao.findByTouristId("tour103").getName());
			//Spot spot = spotDao.findSpotById((long) 5);
		    //List <TouristRoutePoint> touristsPoints = touristDensityService.getCurrentDensityPoints(29101);
		    // System.out.println(touristsPoints.get(0).getLat());
			//System.out.println(spot.getName() + spot.getInfo().getContext());
			
			/******************读文件******************/
			//RoadLinesReader reader = RoadLinesReader.getSingleton();
			calculateRouteService = new CalculateRouteService();
			//calculateRouteService.initialRoadLines();
			//calculateRouteService.initialLineDirection();
			
			/**********计算一个点最近的点********/
			//calculateRouteService.findOnePointNearest(34.47647204775599, 110.08137640441898);
			
			/**********计算起点终点最近的点********/
			//calculateRouteService.findStartEndPointsNearest(34.48793361503166, 110.08815702880862, 
			//												34.47647204775599, 110.08137640441898);
			
			/**********计算路线************/
			//calculateRouteService.getRoutePointStackToDest(34.47647204775599,110.08137640441898,
			//		34.501710173772544, 110.07536825622562);

			
			//calculateRouteService.getRoutePointsToDest(34.47647204775599,110.08137640441898,
			//										34.501710173772544, 110.07536825622562);
			
			/*************ok************/
			//calculateRouteService.getRoutePointsToDest(
			//		34.47691426705584, 110.08313593353274,
			//		34.48646563185032, 110.08785662139896);
			
			/*************ok************/
			//calculateRouteService.getRoutePointsToDest(
			//		34.482468341872995, 110.08669790710452,
			//		34.47691426705584, 110.08313593353274);
			
			calculateRouteService.getRoutePointsToDest(
					34.49583905607486, 110.07558283294681,
					34.530529240631374, 110.07631239379886);
			
			friendService = new FriendService();
			friendService.getFriendsOfOne("sss");
			
			List<TouristRoutePoint> touristRoutePoints = calculateRouteService.getTouristsRoutePoints("sss", 1);
			System.out.println(touristRoutePoints.size());
			
		} catch (HibernateException he) {
			he.printStackTrace();
		} catch (NumberFormatException e) {
		    System.out.println("NumberFormatException");

			e.printStackTrace();
		} 
	}
}
