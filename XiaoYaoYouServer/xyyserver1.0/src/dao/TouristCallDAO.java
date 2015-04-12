package dao;

import java.util.List;

import entity.TouristCall;

public interface TouristCallDAO {
	
	/**
	 * 查找某一园区的未反馈给管理员的报警列表
	 * @return 
	 */
	public List<TouristCall> findGardenTouristCall(int garden_id);
	
	public void delete(TouristCall entity);
	
	public TouristCall save(TouristCall entity);
	
	public void update(TouristCall entity);
}
