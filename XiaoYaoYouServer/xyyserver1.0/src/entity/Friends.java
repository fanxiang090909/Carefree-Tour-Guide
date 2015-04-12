package entity;

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
 * 为保存好友关系的confirm属性，不得已使用此类，替换原有的Many2Many好友关联策略
 * @author fan
 * @version 1.0.0
 */
@Entity
@Table(name = "friends")
public class Friends {
	
	@Id
	@Column(name = "friends_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name= "confirm")
	private Boolean confirm;

	/*************************注意这两个属性为及时加载，不为懒加载**********************************/
	
	/* 双向多对一，多个tourist是一个tourist的好友 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "friend1_id", nullable = false)
	private Tourist friend1;
	
	/* 双向多对一，多个tourist是一个tourist的好友 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "friend2_id", nullable = false)
	private Tourist friend2;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	public Tourist getFriend1() {
		return friend1;
	}

	public void setFriend1(Tourist friend1) {
		this.friend1 = friend1;
	}

	public Tourist getFriend2() {
		return friend2;
	}

	public void setFriend2(Tourist friend2) {
		this.friend2 = friend2;
	}
}
