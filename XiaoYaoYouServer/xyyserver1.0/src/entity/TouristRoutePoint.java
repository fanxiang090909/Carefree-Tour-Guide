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

/**
 * 为记录游客已走路线
 * @author fan
 * @version 1.0.0
 */
@Entity
@Table(name = "tourists_route")
public class TouristRoutePoint {
	
	@Id
	@Column(name = "route_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 路线记录点坐标之纬度
	 */
	@Column(name = "route_lat")
	private Double lat;

	/**
	 * 路线记录点坐标之经度
	 */
	@Column(name = "route_lng")
	private Double lng;
	
	@Column(name = "route_time")
	private Date time;

	/* 单向多对一，多个touristRoutePoing对应一个游客 */
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "tour_id", nullable = false)
	private Tourist tourist;
	
	/* 单向多对一，多个touristRoutePoint对应一个景点*/
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id", nullable = true)
	private Spot nearbySpot;

	public TouristRoutePoint() {
		
	}
	
	/**
	 * 只含经纬度坐标，返回结果时才使用
	 * @param lat
	 * @param lng
	 */
	public TouristRoutePoint(Double lat, Double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	/**
	 * 构造方法，不含id经纬度坐标，返回结果时才使用
	 * @param lat
	 * @param lng
	 * @param time
	 * @param tourist
	 * @param nearbySpot
	 */
	public TouristRoutePoint(Double lat, Double lng, Date time,
			Tourist tourist, Spot nearbySpot) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.time = time;
		this.tourist = tourist;
		this.nearbySpot = nearbySpot;
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

	public void setLat(Double routeLat) {
		this.lat = routeLat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double routeLng) {
		this.lng = routeLng;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date routeTime) {
		this.time = routeTime;
	}

	public Tourist getTourist() {
		return tourist;
	}

	public void setTourist(Tourist tourist) {
		this.tourist = tourist;
	}

	public Spot getNearbySpot() {
		return nearbySpot;
	}

	public void setNearbySpot(Spot nearbySpot) {
		this.nearbySpot = nearbySpot;
	}
		
}
