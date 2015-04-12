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
 * 餐饮住宿的商品实体类
 * @author fan
 * @version 1.0.0
 */
@Entity
@Table(name = "product_list")
public class Product {
	
	@Id
	@Column(name = "product_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "pro_description", length = 200)
	private String description;
	
	@Column(name = "pro_price")
	private float priceValue;

	@Column(name = "pro_pricestr", length = 20)
	private String priceString;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "info_id", nullable = false)
	private Information relatedInfo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDecription(String description) {
		this.description = description;
	}

	public Float getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(Float price) {
		this.priceValue = price;
	}

	public String getPrice() {
		return priceString;
	}

	public void setPriceString(String priceString) {
		this.priceString = priceString;
	}

	public Information getRelatedInfo() {
		return relatedInfo;
	}

	public void setRelatedInfo(Information relatedInfo) {
		this.relatedInfo = relatedInfo;
	}
}
