package controller.tourist.android;

import java.util.ArrayList;
import java.util.List;

import service.TouristDensityService;

import entity.TouristRoutePoint;

public class DensityAction {

	private List<TouristRoutePoint> touristsPoints;
	
	public List<TouristRoutePoint> getTouristsPoints() {
		if (touristsPoints != null)
	    	return touristsPoints;
	    else 
	    	return new ArrayList<TouristRoutePoint>();
	}
	
	private static TouristDensityService touristDensityService;
	
	/**
	 * 无参构造方法
	 */
	public DensityAction() {
		touristsPoints = new ArrayList<TouristRoutePoint>();
		
		DensityAction.touristDensityService = new TouristDensityService();
	}
	
	/**
	 * struts2 动作方法，加载客流密度
	 * @return
	 */
	public String load_density() {
		/* 不要参数  */
		//String username = servletRequest.getParameter("username");
	    System.out.println("DensityAciton");
	    
	    touristsPoints = touristDensityService.getCurrentDensityPoints(29101);
	    
	    
		return "success";
	}
}
