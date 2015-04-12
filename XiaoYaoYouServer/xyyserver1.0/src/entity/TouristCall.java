package entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tour_call")
public class TouristCall {
	@Id
	@Column(name = "call_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 路线记录点坐标之纬度
	 */
	@Column(name = "call_lat")
	private Double lat;

	/**
	 * 路线记录点坐标之经度
	 */
	@Column(name = "call_lng")
	private Double lng;

	@Column(name = "call_time")
	private Date time;

	@Column(name = "call_is_send")
	private boolean isSend;
		
	/* 单向多对一，多个touristRoutePoing对应一个游客 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "tour_id", nullable = false)
	private Tourist tourist;
	
	/* 少加一个garden属性，先不加了 */

	public TouristCall() {
		
	}
	
	/**
	 *  保存报警信息时使用，不需要传id参数
	 * @param lat
	 * @param lng
	 * @param time
	 * @param isSend
	 * @param tourist
	 */
	public TouristCall(Double lat, Double lng, Date time, boolean isSend,
			Tourist tourist) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.time = time;
		this.isSend = isSend;
		this.tourist = tourist;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	public Tourist getTourist() {
		return tourist;
	}

	public void setTourist(Tourist tourist) {
		this.tourist = tourist;
	}
}
