package controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.SpotDAO;
import dao.hibernateImpl.SpotDAOHibernateImpl;
import dataobj.AlarmMsgItem;
import dataobj.TouristNumItem;
import entity.Spot;

/**
 * 浏览器定期刷新处理，返回景点客流人数列表和报警信息列表
 * @author fan
 * @version 1.0.0
 */
public class RefreshSpotTouristNumAction {

	/**
	 * 需要返回景点信息的数据访问接口，不需要set get方法
	 */
	private static SpotDAO spotDao;
	
	/**
	 * 景点游客数量列表
	 */
	private List<TouristNumItem> touristNumList;
	
	public List<TouristNumItem> getTouristnum_list() {
		if (touristNumList != null && touristNumList.size() > 0)
			return touristNumList;
		else 
			return new ArrayList<TouristNumItem>();
	}
	
	public int getTotalProperty() {
		if (touristNumList != null && touristNumList.size() > 0)
			return touristNumList.size();
		else
			return 0;
	}

	public boolean getSuccess() {
		return true;
	}
	
	/**
	 * 无参构造方法
	 */
	public RefreshSpotTouristNumAction () {
		
		/* 初始化返回的数组 */
		touristNumList = new ArrayList<TouristNumItem>();
		
		/* 初始化数据访问对象 */
		spotDao = new SpotDAOHibernateImpl();
	}
	
	public String refresh() {
		System.out.println("AdminRefreshSpotTouristNumAction:" + "touristNumList & alarmMsgList");
		
		/* 访问数据库得到当前景点人数 */
		List<Spot> spots = spotDao.findTypeSpotsByGardenId(29101, SpotDAO.SpotType.ScenicSpot);
		if (spots != null)
			/* 填充touristNumList属性 */
			for (int i = 0; i < spots.size(); i++) {
				Spot spoti = spots.get(i);
				touristNumList.add(new TouristNumItem(spoti.getId(), spoti.getName(), 
						(int) (spoti.getMaxPeoNum() * 1.1 * Math.random()) ,spoti.getMaxPeoNum()));
			}
		
		return "success";
	}
}
