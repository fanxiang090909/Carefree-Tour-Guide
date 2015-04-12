package entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import javax.persistence.OneToMany;

/**
 * 游客实体类
 * @author fan
 * @version 0.1
 */
@Entity
public class Tourist {

	@Id
	@Column(name= "username", length = 15, unique = true, nullable = false)
	private String id;
	
	@Column(unique = true, nullable = false)
	private Long phone;
		
	@Column(name= "tour_name", length = 20)
	private String name;

	@Column(name= "tour_passwd", length = 15, nullable = false)
	private String passwd;
	
	@Column(name= "tour_sex")
	private Boolean sex;
	
	@Column(name= "tour_level")
	private Integer level;
	
	@Column(name= "nickname", length = 20)
	private String nickname;
	
	@Column(name= "receive_warn_id")
	private Integer receiveWarningID;

	/* 好友关系多对多,由于不能添加关联表中的confrim属性，放弃使用，已去掉setter和getter方法 */
    /*@ManyToMany(targetEntity=entity.Tourist.class,  
            cascade={CascadeType.MERGE,CascadeType.PERSIST}       
    		)  
    @JoinTable(name="friends",  
           joinColumns={@JoinColumn(name="friend1_id")},  
           inverseJoinColumns={@JoinColumn(name="friend2_id")}
    		)  
    private List<Tourist> friends;*/
	
	/* 添加好友类Friends实体之后，好友关系双向一对多，一个Tourist多个Friends */
	@OneToMany(mappedBy = "id", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private Set<Friends> friends;
	
	public Tourist() {
	}
	
	public Tourist(String username, String passwd, Long phone) {
		this.id = username;
		this.passwd = passwd;
		this.phone = phone;
		this.nickname = username;
	}
	
	public Tourist(String username, String passwd, Long phone, Integer receivedWarnID) {
		this.id = username;
		this.passwd = passwd;
		this.phone = phone;
		this.nickname = username;
		this.receiveWarningID = receivedWarnID;
	}
	
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

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

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getReceiveWarningID() {
		return receiveWarningID;
	}

	public void setReceiveWarningID(Integer receiveWarningID) {
		this.receiveWarningID = receiveWarningID;
	}

	public Set<Friends> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friends> friends) {
		this.friends = friends;
	}

	public boolean equals(Object other) {
		if (this == other) 
			return true;
		if ( !(other instanceof Tourist) )
			return false;
		final Tourist tourist = (Tourist) other;
		if ( !tourist.getId().equals( getId() ) )
			return false;
		return true;
	}
	
	public int hashCode() {
		return getId().hashCode() + 24 * getId().charAt(0);
	}
	
}
