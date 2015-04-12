package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 游玩主题实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
@Table(name = "theme")
public class Theme {
	
	@Id
	@Column(name = "theme_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "theme_pic", length = 40, unique = true)
	private String picture;
	
	@Column(name = "theme_description", length = 50, unique = true)
	private String description;

	/* 双向多对多,一个主题对应多个景点 */
    @ManyToMany(targetEntity=entity.Spot.class,  
            cascade={CascadeType.MERGE,CascadeType.PERSIST}       
    		)  
    @JoinTable(name="theme_spot",  
           joinColumns={@JoinColumn(name="theme_id")},  
           inverseJoinColumns={@JoinColumn(name="spot_id")}
    		)
    private List<Spot> themeSpots;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Spot> getThemeSpots() {
		return themeSpots;
	}

	public void setThemeSpots(List<Spot> themeSpots) {
		this.themeSpots = themeSpots;
	}
}
