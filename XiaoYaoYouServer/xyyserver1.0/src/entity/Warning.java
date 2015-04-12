package entity;

import java.sql.Time;
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

/**
 * 预警信息实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
public class Warning {

	@Id
	@Column(name = "warn_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "warn_time")
	private Date time;
	
	@Column(name = "warn_msg", length = 200)
	private String message;
	
	@Column(name= "warn_level")
	private Integer level;
		
	//弃用这个方法
	/* 刷新推送消息采取的策略, 管理员端发的消息，从1到5变换 */
	//@Column(name = "warn_num")
	//private Integer warnNum;
	
	/* 双向多对一，多个warning属于一个garden园区 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "garden_id", nullable = false)
	private Garden garden;

	/* 双向多对一，多个warning由一个admin发布 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id", nullable = true)
	private Admin admin;
	
	public Warning() {
		
	}
	
	/**
	 * 添加时刷新推送消息采取的策略
	 * @param date
	 * @param message
	 * @param level
	 * @param garden
	 * @param admin
	 */
	public Warning(Date date, String message, Integer level,
			Garden garden, Admin admin) {
		super();
		this.time = date;
		this.message = message;
		this.level = level;
		this.garden = garden;
		this.admin = admin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Garden getGarden() {
		return garden;
	}

	public void setGarden(Garden garden) {
		this.garden = garden;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public boolean equals(Object other) {
		if (this == other) 
			return true;
		if ( !(other instanceof Warning) )
			return false;
		final Warning warning = (Warning) other;
		if ( !warning.getId().equals( getId() ) )
			return false;
		return true;
	}
	
	public int hashCode() {
		return getId().hashCode() + getId();
	}
}
