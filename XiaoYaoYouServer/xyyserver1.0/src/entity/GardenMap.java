package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 园区地图实体类，每个园区一张图
 * @author fan
 * @version 1.0.0
 */
@Entity
@Table(name = "garden_map")
public class GardenMap {

	@Id
	@Column(name = "garden_map_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "map_path", length = 40)
	private String path;
	
	/**
	 * ��ͼ���Ͻ�γ��
	 */
	@Column(name = "lt_lat")
	private Double leftTopLat;

	/**
	 * ��ͼ���ϽǾ���
	 */
	@Column(name = "lt_lng")
	private Double leftTopLng;
	
	/**
	 * ��ͼ���½�γ��
	 */
	@Column(name = "rb_lat")
	private Double rightButtomLat;

	/**
	 * ��ͼ���½Ǿ���
	 */
	@Column(name = "rb_lng")
	private Double rightButtomLng;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Double getLeftTopLat() {
		return leftTopLat;
	}

	public void setLeftTopLat(Double leftTopLat) {
		this.leftTopLat = leftTopLat;
	}

	public Double getLeftTopLng() {
		return leftTopLng;
	}

	public void setLeftTopLng(Double leftTopLng) {
		this.leftTopLng = leftTopLng;
	}

	public Double getRightButtomLat() {
		return rightButtomLat;
	}

	public void setRightButtomLat(Double rightButtomLat) {
		this.rightButtomLat = rightButtomLat;
	}

	public Double getRightButtomLng() {
		return rightButtomLng;
	}

	public void setRightButtomLng(Double rightButtomLng) {
		this.rightButtomLng = rightButtomLng;
	}
}
