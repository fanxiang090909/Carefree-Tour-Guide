package controller.tourist.android;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import entity.TouristRoutePoint;

import service.CalculateRouteService;

public class PassedRoadAction implements ServletRequestAware {

	/* 管理员也要使用这个类 */
	private String phone;

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/* 实现servletRequestAware接口需要  */
	private ServletRequest servletRequest;

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	private List<TouristRoutePoint> touristRoutePoints;
	
	public List<TouristRoutePoint> getRoutePointList() {
	 	if (touristRoutePoints != null)
	    	return touristRoutePoints;
	    else 
	    	return new ArrayList<TouristRoutePoint>();
	}
	
	private static CalculateRouteService calculateRouteService;
	
	public PassedRoadAction() {
		PassedRoadAction.calculateRouteService = new CalculateRouteService();
		//touristRoutePoints = new ArrayList<TouristRoutePoint>();
	}
	
	public String load_route() {
		
		String username = servletRequest.getParameter("username");
	    if (username != null) {
	    	System.out.println("PassedRoadAction.post::username:" + username);
	    	touristRoutePoints = calculateRouteService.getTouristsRoutePoints(username, 1);

		} else if (phone != null) {
			
			
	    	System.out.println("AdminSearchRoute.::phone" + phone);
	    	touristRoutePoints = calculateRouteService.getTouristsRoutePoints(phone, 2);
	    }
	    
		return "success";
	}
	
}
