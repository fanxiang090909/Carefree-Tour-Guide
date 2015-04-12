package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 景点实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
public class Spot {

	@Id
	@Column(name= "spot_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "spot_name", length = 40, nullable = false)
	private String name;

	@Column(length = 200)
	private String description;

	@Column(name = "max_peonum")
	private Integer maxPeoNum;
	
	/**
	 * spot分类
	 * type=1为景点，2为主题点，3为餐饮，4为住宿，5为休息点，6为卫生间，7为大门
	 */
	@Column(nullable = false)
	private Integer type;
	
	/**
	 * 景点中心坐标之纬度
	 */
	@Column(name = "spot_lat")
	private Double centLat;

	/**
	 * 景点中心坐标之经度
	 */
	@Column(name = "spot_lng")
	private Double centLng;

	/**
	 * 纬度范围差值
	 */
	@Column(name = "region_lat_dev")
	private Double regionLatDeviation;
	
	/**
	 * 经度范围差值
	 */
	@Column(name = "region_lng_dev")
	private Double regionLngDeviation;
	
	/**
	 * 双向一对一，景点对应信息，一个景点对应一个信息
	 */
    @OneToOne(mappedBy="spot")  
	private Information info;
	
    /**
     * 单向多对一，多个spot对应一个garden
     */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "garden_id")
    private Garden garden;
    
	/**
	 * 双向多对多, 一个景点，对应多个主题，中间有theme_spot关联表 
	 */
	@ManyToMany(mappedBy="themeSpots", cascade={CascadeType.PERSIST})  
	private List<Theme> spotThemes;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMaxPeoNum() {
		return maxPeoNum;
	}

	public void setMaxPeoNum(Integer maxPeoNum) {
		this.maxPeoNum = maxPeoNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getCentLat() {
		return centLat;
	}

	public void setCentLat(Double centLat) {
		this.centLat = centLat;
	}

	public Double getCentLng() {
		return centLng;
	}

	public void setCentLng(Double centLng) {
		this.centLng = centLng;
	}
	
	public Double getRegionLatDeviation() {
		return regionLatDeviation;
	}

	public void setRegionLatDeviation(Double regionLatDeviation) {
		this.regionLatDeviation = regionLatDeviation;
	}

	public Double getRegionLngDeviation() {
		return regionLngDeviation;
	}

	public void setRegionLngDeviation(Double regionLngDeviation) {
		this.regionLngDeviation = regionLngDeviation;
	}

	public Information getInfo() {
		return info;
	}

	public void setInfo(Information info) {
		this.info = info;
	}

	public List<Theme> getSpotThemes() {
		return spotThemes;
	}

	public void setSpotThemes(List<Theme> spotThemes) {
		this.spotThemes = spotThemes;
	}

	public Garden getGarden() {
		return garden;
	}

	public void setGarden(Garden garden) {
		this.garden = garden;
	}

	public boolean equals(Object other) {
		if (this == other) 
			return true;
		if ( !(other instanceof Spot) )
			return false;
		final Spot spot = (Spot) other;
		if ( !spot.getId().equals( getId() ) )
			return false;
		return true;
	}
	
	public int hashCode() {
		return (int) (getId().hashCode() + getId());
	}

}
