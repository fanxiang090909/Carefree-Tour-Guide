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
 * 景点评论实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
@Table(name = "comment")
public class Comment {
	
	@Id
	@Column(name = "comment_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "com_content", length = 1024)
	private String content;
	
	@Column(name = "com_grade")
	private Float commentGrade;
	
	@Column(name = "com_time")
	private Date commentTime;
	
	/* 单向多对一，多个comment对应一个游客 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "tour_id", nullable = false)
	private Tourist tourist;
	
	/* tourist当时评论的昵称 */
	@Column(name = "nickname", length = 20)
	private String nickname;
	
	/* 单向多对一，多个comment对应一个景点信息 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "info_id", nullable = false)
	private Information relatedSpot;
	
	public Comment() {
		super();
	}

	public Comment(String content, Float commentGrade, Date commentTime,
			Tourist tourist, String nickname, Information relatedSpot) {
		super();
		this.content = content;
		this.commentGrade = commentGrade;
		this.commentTime = commentTime;
		this.tourist = tourist;
		this.nickname = nickname;
		this.relatedSpot = relatedSpot;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Float getCommentGrade() {
		return commentGrade;
	}

	public void setCommentGrade(Float commentGrade) {
		this.commentGrade = commentGrade;
	}

	public Date getTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Tourist getTourist() {
		return tourist;
	}

	public void setTourist(Tourist tourist) {
		this.tourist = tourist;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Information getRelatedSpot() {
		return relatedSpot;
	}

	public void setRelatedSpot(Information relatedSpot) {
		this.relatedSpot = relatedSpot;
	}
}
