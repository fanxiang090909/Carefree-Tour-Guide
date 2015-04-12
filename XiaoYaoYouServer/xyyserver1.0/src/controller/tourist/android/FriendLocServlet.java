package controller.tourist.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CalculateRouteService;
import service.OperateMap;

import dao.TouristDAO;
import dao.TouristRouteDAO;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import dao.hibernateImpl.TouristRouteDAOHibernateImpl;
import dataobj.RoutePoint;
import entity.Tourist;
import entity.TouristRoutePoint;

public class FriendLocServlet  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2306531543977575056L;
	
	private TouristDAO tourDao;

	private static CalculateRouteService calculateRouteService;
	
	private static OperateMap operateMap;
	
	public FriendLocServlet () {
		super();
		tourDao = new TouristDAOHibernateImpl();
		FriendLocServlet.calculateRouteService = new CalculateRouteService();
		FriendLocServlet.operateMap = new OperateMap();
	}
	
	/** 
	 * Fist Servlet
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String username = request.getParameter("friendusername");

		/* 登录才发送位置。检查是否在http的session回话作用域中存入属性meTourist */
	    System.out.println("FriendLocServlet.post::friendusername:" + username);
		PrintWriter out = response.getWriter();
	    			
		/* 1.查找好友位置是否存在 */ 
		Tourist tourist = tourDao.findByTouristId(username);

		String msg;
		if (tourist == null) {
			msg = "{\"success\":false,\"msg\":\"no friend: " + username + "!\"}";
		 	out.print(msg);
		 	return;
		}
		System.out.println(tourist.getName());

		double lat = 0;//= 34.490004215897200; 
		double lng = 0;//= 110.082535743713000;
		
		/*  */
		/* 2.取出好友位置 */
		TouristRoutePoint routePoint = calculateRouteService.getTouristRouteCurrentPoint(username);
		/* 3.判断是否在园区 */
		if (routePoint != null && operateMap.ValidateInGarden(routePoint, 29101)) {
			lat = routePoint.getLat();
			lng = routePoint.getLng();
		} else {
			msg = "{\"success\":false,\"msg\":\"can not find " + username + "\"}";  
			out.print(msg);
			return;
		}

		msg = "{\"success\":true,\"lat\":" + lat + ",\"lng\":" + lng + "}";  
		out.print(msg);
		return;
	}
	
	/** 
	 * Fist Servlet
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		this.doGet(request, response);
	}
}
