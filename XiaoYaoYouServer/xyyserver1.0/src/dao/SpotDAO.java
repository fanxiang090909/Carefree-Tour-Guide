package dao;

import java.util.List;

import entity.Spot;

/**
 * 景点数据访问接口
 * @author fan
 * @version 1.0.0
 */
public interface SpotDAO {
	
	/**
	 * 景点类型，枚举类型
	 * @author fan
	 * @version 1.0.0
	 */
	enum SpotType{
		ScenicSpot, 
		ThemeSpot, 
		EatingSpot, 
		HotelSpot, 
		RestSpot, 
		Toilet, 
		Gate
	};
	
	/**
	 * 得到某一景区的所有类型为Type的景点
	 * @param gardenId
	 * @param type 景点类型枚举有ScenicSpot,ThemeSpot,EatingSpot,HotelSpot,RestSpot,Toilet,Gate
	 * @return 景点
	 */
	public List<Spot> findTypeSpotsByGardenId(Integer gardenId, SpotType type);

	/**
	 * 得到景点
	 * @param id
	 * @return 景点
	 */
	public Spot findSpotById(Long id);
		
	public void delete(Spot entity);
	
	public Spot save(Spot entity);
	
	/**
	 * 更新景点
	 * @param entity
	 */
	public void update(Spot entity);
}
