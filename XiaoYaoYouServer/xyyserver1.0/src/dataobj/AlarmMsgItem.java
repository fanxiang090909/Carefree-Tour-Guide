package dataobj;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 报警信息
 * @author fan
 * @version 1.0.0
 */
public class AlarmMsgItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -208067052499008124L;

	private String username;
	
	private String phone;
	
	private String sex;
	
	private String province;
	
	private Date time;

	private List<RoutePoint> route;
	
	
	public AlarmMsgItem() {
		
	}

	/**
	 * 
	 * @param username 游客用户名
	 * @param phone 电话
	 * @param sex 游客性别
	 * @param province 游客籍贯
	 */
	public AlarmMsgItem(String username, String phone, String sex,
			String province) {
		super();
		this.username = username;
		this.phone = phone;
		this.sex = sex;
		this.province = province;
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public List<RoutePoint> getRoute() {
		return route;
	}

	public void setRoute(List<RoutePoint> route) {
		this.route = route;
	}
	
	/**
	 * 返回路线点数，不需要特别属性
	 * @return 游客路线记录点数
	 */
	public int getRoute_point_num() {
		if (route != null && route.size() > 0)
			return 5;//route.size();
		else 
			return 0;
	}

	public boolean getSuccess() {
		return true;
	}
	
}
