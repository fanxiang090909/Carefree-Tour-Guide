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
import javax.persistence.Table;

/**
 * 景点信息实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
@Table(name = "info")
public class Information {
	
	@Id
	@Column(name = "info_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 2048)
	private String context;
	
	@Column(length = 40)
	private String voice;
	
	@Column(length = 40)
	private String video;
	
	@Column(name = "grade_level")
	private float gradeLevel;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id")
	private Spot spot;
	
	/* 单向一对多，一个景点信息对应多张图片  */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "info_id", nullable = false)
	private List<Picture> pics;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public float getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(float gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public Spot getSpot() {
		return spot;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
	}

	public List<Picture> getPics() {
		return pics;
	}

	public void setPics(List<Picture> pics) {
		this.pics = pics;
	}

	public boolean equals(Object other) {
		if (this == other) 
			return true;
		if ( !(other instanceof Information) )
			return false;
		final Information information = (Information) other;
		if ( !information.getId().equals( getId() ) )
			return false;
		return true;
	}
	
	public int hashCode() {
		return getId().hashCode() + getId();
	}
}
