package controller.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import service.CalculateRouteService;

import dao.TouristDAO;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import dataobj.AlarmMsgItem;
import dataobj.RoutePoint;
import entity.Tourist;

@SuppressWarnings("serial")
public class TraceRouteAction implements Serializable {

	/**
	 * 游客电话，每个游客电话唯一且不为空，传入的参数
	 */
	private String phone;

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 返回的参数，游客路线信息
	 */
	private AlarmMsgItem touristRouteData;
	
	public AlarmMsgItem getTouristRouteData() {
		return touristRouteData;
	}
	
	private static CalculateRouteService calculateRouteService;

	private static TouristDAO touristDAO;
	
	/**
	 * 无参构造方法
	 */
	public TraceRouteAction () {
		TraceRouteAction.calculateRouteService = new CalculateRouteService();
		touristDAO = new TouristDAOHibernateImpl();

	}

	/**
	 * 动作方法，游客路线追踪
	 */
	public String trace_route() {
		System.out.println("AdminTraceRouteAction:" + "Ajax,get:phone" + phone);
		
		/* 数据库中查找游客，查找其位置  */
		
		/* 填充touristRouteData属性 */
		long phonelong = 0;
		if (phone != null)
			phonelong = Long.parseLong(phone);
		Tourist tourist = null;
		if (phonelong != 0)
			tourist = touristDAO.findByPhone(phonelong);
		if (tourist == null)
			return "success";
		touristRouteData = new AlarmMsgItem(tourist.getId(), phone, "男", "陕西");
		
		/* 假 */
		List<RoutePoint> route = calculateRouteService.getTouristSpotRoutePoint(tourist.getId());
		/*				new ArrayList<RoutePoint>();
		for (int i = 0; i < 4; i++)
			route.add(new RoutePoint(new Date(), (long)i, "景点" + i)); 
		*/
		System.out.println("怎么回事？");
		if (route != null)
			touristRouteData.setRoute(route);
		else
			touristRouteData.setRoute(new ArrayList<RoutePoint>());
		
		return "success";
	}
}
