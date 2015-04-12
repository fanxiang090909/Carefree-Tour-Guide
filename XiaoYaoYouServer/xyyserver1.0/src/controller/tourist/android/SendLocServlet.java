package controller.tourist.android;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import dao.TouristDAO;
import dao.WarningDAO;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import dao.hibernateImpl.WarningDAOHibernateImpl;

import service.CalculateRouteService;
import service.WarningService;

import entity.Tourist;
import entity.TouristRoutePoint;
import entity.Warning;

/**
 * android手机端发送位置信息
 * @author fan
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SendLocServlet extends HttpServlet {

	private static CalculateRouteService calculateRouteService;
	
	private static WarningDAO warningDao;
	
	private static TouristDAO tourDao;
	
	public SendLocServlet() {
		super();
		calculateRouteService = new CalculateRouteService();
		warningDao = new WarningDAOHibernateImpl();
		tourDao = new TouristDAOHibernateImpl();
	}
	
	/** 
	 * Fist Servlet
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String username = request.getParameter("username");
		String currentlatString = request.getParameter("currentlat");
		String currentlngString = request.getParameter("currentlng");

		String ipaddress = request.getRemoteAddr();
		String getIpaddr = getIpAddr(request);
		/* 登录才发送位置。检查是否在http的session回话作用域中存入属性meTourist */
		
		String returnJson = "";
		
		try {
			double currentlat = Double.parseDouble(currentlatString) / 1000000;
			double currentlng = Double.parseDouble(currentlngString) / 1000000;
		    			
		    System.out.println("SendLocServlet.post::username:" + username + ", currentlat:" + currentlat + ", currentlng:" + currentlng
		    		+ ", ipaddress:" + ipaddress + ", getIpAddr" + getIpaddr + ", port:" + request.getRemotePort());

		    /* 存入数据库 */
			int result = calculateRouteService.saveRoute(currentlat, currentlng, username);
			

			if  (result == 0)
				returnJson = "{\"success\":true";
			else if (result == 1)
				returnJson = "{\"success\":false";
			else if (result == 2)
				returnJson = "{\"success\":false";
			
			
		} catch (NumberFormatException nfe) {
			/* 如果格式有问题则出现未知错误 */
			response.getWriter().print("{\"success\":false");
		}
		
		Tourist me = tourDao.findByTouristId(username);
		String msg = "";

		if (me != null) {
			int i = me.getReceiveWarningID();
			//System.out.println("id:" + i);
			Warning warning = warningDao.findById(i + 1);
			if (warning != null) {
				 msg = warning.getMessage();
				 if (i < warning.getId()) {
					//warnings.get(0).setSend(true);
					//warningDao.update(warnings.get(0));
					 me.setReceiveWarningID(++i);
					 tourDao.update(me);
				 }
			}
		}
		
		returnJson += ",\"message\":\"" + msg + "\"}";

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out = response.getWriter();

		out.print(returnJson);
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
	
	/**
	 * 获得客户端真实IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) { 
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("Proxy-Client-IP");
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("WL-Proxy-Client-IP");
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getRemoteAddr(); 
		} 
		return ip; 
	}
}
