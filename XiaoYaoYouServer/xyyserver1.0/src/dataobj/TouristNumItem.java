package dataobj;

import java.io.Serializable;

/**
 * 传递游客密度数据使用
 * @author fan
 * @version 1.0.0
 */
public class TouristNumItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4511289961923218801L;

	private Long spot_id;
	
	private String spot_name;
	
	private Integer num;
	
	private Integer maxnum;

	public TouristNumItem() {
		
	}
	
	public TouristNumItem(Long spot_id, String spot_name, Integer num,
			Integer maxnum) {
		super();
		this.spot_id = spot_id;
		this.spot_name = spot_name;
		this.num = num;
		this.maxnum = maxnum;
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

	public void setSpot_nameString(String spot_name) {
		this.spot_name = spot_name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getMaxnum() {
		return maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}
	
}
