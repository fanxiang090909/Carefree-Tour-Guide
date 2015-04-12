package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 管理员实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
public class Admin {
	
	@Id
	@Column(name= "admin_id", length = 20, nullable = false)
	private String id;
	
	@Column(name= "admin_name", length = 20)
	private String name;
	
	@Column(name= "admin_passwd", length = 15, nullable = false)
	private String passwd;
	
	/* 双向多对一，多个admin管理一个景区 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "garden_id", nullable = false)
	private Garden garden;
	
	/* 双向一对多，一个administrator发布多个warning */
	@OneToMany(mappedBy = "admin", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id", nullable = false)
	private List<Warning> warnings;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public Garden getGarden() {
		return garden;
	}

	public void setGarden(Garden garden) {
		this.garden = garden;
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
		if ( !(other instanceof Admin) )
			return false;
		final Admin admin = (Admin) other;
		if ( !admin.getId().equals( getId() ) )
			return false;
		return true;
	}
	
	public int hashCode() {
		return getId().hashCode() + 24 * getId().charAt(0);
	}
}
