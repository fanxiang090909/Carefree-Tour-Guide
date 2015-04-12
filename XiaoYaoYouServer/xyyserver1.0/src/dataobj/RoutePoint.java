package dataobj;

import java.util.Date;

/**
 * 管理员的，游客路线追踪，需要显示名称
 * @author fan
 * @version 1.0.0
 */
public class RoutePoint {
	
	private Date time;
	
	private Long spot_id;

	private String spot_name;

	public RoutePoint() {
		super();
	}

	public RoutePoint(Date time, Long spot_id, String spot_name) {
		super();
		this.time = time;
		this.spot_id = spot_id;
		this.spot_name = spot_name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getSpot_id() {
		return spot_id;
	}

	public void setSpot_id(Long spot_id) {
		this.spot_id = spot_id;
	}

	public String getSpot_name() {
		return spot_name;
	}

	public void setSpot_name(String spot_name) {
		this.spot_name = spot_name;
	}
}
