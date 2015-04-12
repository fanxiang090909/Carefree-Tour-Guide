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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 园区实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
public class Garden {

	@Id
	@Column(name = "garden_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name= "garden_name", length = 30, nullable = false, unique = true)
	private String name;
	
	@Column(length = 80)
	private String address;
	
	@Column(name= "garden_level")
	private Integer level;
	
	@Column(name = "emerg_phone")
	private Long emergencyPhone;
	
	@Column(name = "cent_lat")
	private Double centLat;
	
	@Column(name = "cent_lng")
	private Double centLng;

	@Column(name= "province", length = 20)
	private String province;

	@Column(name= "district", length = 20)
	private String district;

	/* 单向一对一， 一个园区一张地图  */
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "garden_map_id")
	private GardenMap gardenMap;

	/* 双向一对多，一个garden由多个admin管理 */
	@OneToMany(mappedBy = "garden", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "garden_id", nullable = false)
	private List<Admin> admins;

	/* 双向一对多，一个garden园区有多个warning */
	@OneToMany(mappedBy = "garden", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "garden_id", nullable = false)
	private List<Warning> warnings;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(Long emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
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
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public GardenMap getGardenMap() {
		return gardenMap;
	}

	public void setGardenMap(GardenMap gardenMap) {
		this.gardenMap = gardenMap;
	}

	public List<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	public List<Warning> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<Warning> warnings) {
		this.warnings = warnings;
	}

	public boolean equals(Object other) {
		if (this == other) 
			return true;
		if ( !(other instanceof Garden) )
			return false;
		final Garden garden = (Garden) other;
		if ( !garden.getId().equals( getId() ) )
			return false;
		return true;
	}
	
	public int hashCode() {
		return getId().hashCode() + getId();
	}
}
