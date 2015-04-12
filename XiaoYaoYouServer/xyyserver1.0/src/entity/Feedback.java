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
 * 用户反馈实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
@Table(name = "feedback")
public class Feedback {
	
	@Id
	@Column(name = "fb_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "fb_content", length = 1024)
	private String content;
	
	@Column(name = "fb_time")
	private Date feedbackTime;
	
	/* 单向多对一，多个comment对应一个游客 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "tour_id", nullable = false)
	private Tourist tourist;
	
	/* 单向多对一，多个comment对应一个园区 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "garden_id", nullable = false)
	private Garden relatedGarden;
	
	public Feedback() {
		
	}
	
	/**
	 * 新的反馈消息构造函数
	 * 
	 * @param content
	 * @param feedbackTime
	 * @param isSend 消息是否发送给管理员
	 * @param tourist
	 * @param relatedGarden
	 */
	public Feedback(String content, Date feedbackTime, Tourist tourist,
			Garden relatedGarden) {
		super();
		this.content = content;
		this.feedbackTime = feedbackTime;
		this.tourist = tourist;
		this.relatedGarden = relatedGarden;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return feedbackTime;
	}

	public void setCommentTime(Date commentTime) {
		this.feedbackTime = commentTime;
	}

	public Tourist getTourist() {
		return tourist;
	}

	public void setTourist(Tourist tourist) {
		this.tourist = tourist;
	}

	public Garden getRelatedGarden() {
		return relatedGarden;
	}

	public void setRelatedGarden(Garden relatedGarden) {
		this.relatedGarden = relatedGarden;
	}
	
	/**
	 * struts 返回参数时使用
	 * @return
	 */
	public String getUsername() {
		if (tourist != null)
			return tourist.getId();
		else {
			return "";
		}
	}
}
