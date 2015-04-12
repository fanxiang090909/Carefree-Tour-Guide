package controller.tourist.android;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import dao.SpotDAO;
import dao.hibernateImpl.SpotDAOHibernateImpl;

import service.CalculateRouteService;

import entity.Spot;
import entity.TouristRoutePoint;

public class RouteCalculateAction implements ServletRequestAware {

	private ServletRequest servletRequest;
	
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	
	private List<TouristRoutePoint> destRoute;
	
	/**
	 * 获得经过的点的信息
	 * @return
	 */
	public List<TouristRoutePoint> getRoutePointList() {
		if (destRoute != null)
	    	return destRoute;
	    else 
	    	return new ArrayList<TouristRoutePoint>();
	}

	private String toastString;
	
	/**
	 * 计算到达目的地所需的时间,和距离
	 * @return
	 */	
	public String getToast() {
		
		return toastString;
	}
	
	private static CalculateRouteService calculateRouteService;
	
	/**
	 * 景点控制访问对象, 无需set get方法
	 */
	private static SpotDAO spotDao;
	
	public RouteCalculateAction() {
		RouteCalculateAction.calculateRouteService = new CalculateRouteService();
		RouteCalculateAction.spotDao = new SpotDAOHibernateImpl();
		destRoute = new ArrayList<TouristRoutePoint>();

	}
	
	public String load_route() {
		Long idString = null;
		Double latitude = null;
		Double longtitude = null;
		try {
			String username = servletRequest.getParameter("username");
			latitude = Double.parseDouble(servletRequest.getParameter("currentlat")) / 1000000;
			longtitude = Double.parseDouble(servletRequest.getParameter("currentlng")) / 1000000;
			idString = Long.parseLong(servletRequest.getParameter("des_spot_id"));

		    System.out.println("RouteCaculateAction::username:" + username
		    		 + ",currentlat:" + latitude + ",currentlng:" + longtitude 
		    		 + ", destinate:" + idString);
		    
		    Spot destspot = spotDao.findSpotById(idString);
		    System.out.println(destspot.getName());
		    
		    destRoute = calculateRouteService.getRoutePointsToDest(
		    		latitude, longtitude,
		    		destspot.getCentLat(),
		    		destspot.getCentLng());
		  			
		    String spotNameString = destspot.getName();
		    
		    toastString = "距离" + spotNameString + "还有" + 
		    			calculateRouteService.getMinLength() + "米，还需要约" + 
		    			calculateRouteService.getMinDuration() + "分钟"; 
		
		} catch (NumberFormatException nfe) {
			System.out.println("格式出错");
		}
		return "success";
	}
}
