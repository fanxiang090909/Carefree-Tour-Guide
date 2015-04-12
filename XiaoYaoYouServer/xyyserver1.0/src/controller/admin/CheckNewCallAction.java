package controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.TouristCallDAO;
import dao.hibernateImpl.TouristCallDAOHibernateImpl;
import dataobj.AlarmMsgItem;
import entity.TouristCall;
import entity.Warning;

public class CheckNewCallAction {
	
	/**
	 * 报警提示列表
	 */
	private List<AlarmMsgItem> alarmMsgList;
	
	public List<AlarmMsgItem> getAlarm_msgs() {
		if (alarmMsgList != null && alarmMsgList.size() > 0)
			return alarmMsgList;
		else 
			return new ArrayList<AlarmMsgItem>();
	}
	
	private static TouristCallDAO touristCallDao;
	
	public CheckNewCallAction() {
		alarmMsgList = new ArrayList<AlarmMsgItem>();
		touristCallDao = new TouristCallDAOHibernateImpl();
	}
	
	public String check_call() {
		
		System.out.println("AdminCheckNewCallAction:" + "touristNumList & alarmMsgList");

		/* 得到报警信息，填充报警列表 */
		List<TouristCall> calls = touristCallDao.findGardenTouristCall(29101);
		if (calls != null)
		for (int i = 0 ; i < calls.size(); i++) {
			TouristCall touristCall = calls.get(i);
				AlarmMsgItem item = new AlarmMsgItem(touristCall.getTourist().getId(),
					touristCall.getTourist().getPhone().toString(), "男", "陕西");
				item.setTime(touristCall.getTime());
				alarmMsgList.add(item);
				touristCall.setSend(true);
				touristCallDao.update(touristCall);
		}
		
		/*if (Math.random() < 0.3)
		{
			AlarmMsgItem item = new AlarmMsgItem("fanxiang", "1231232133", "男", "陕西");
			item.setTime(new Date());
			AlarmMsgItem item2 = new AlarmMsgItem("afsd", "233232322", "男", "陕西");
			item2.setTime(new Date());
			alarmMsgList.add(item);
			alarmMsgList.add(item2);
		}*/
		return "success";
	}
}
