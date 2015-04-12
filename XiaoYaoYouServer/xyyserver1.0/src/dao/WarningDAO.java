package dao;

import java.util.List;

import entity.Warning;

public interface WarningDAO {
	
	/**
	 * 查找最近一条预警
	 * @param garden_id
	 * @return
	 */
	public Warning findGardenLatestWarning(int garden_id);
	
	/**
	 * 找iD为warningid的预警信息
	 * @param warningid
	 * @return
	 */
	public Warning findById(Integer warningid);
	
	/**
	 * 查找某一园区的未反馈给管理员的报警列表
	 * @return 
	 */
	public List<Warning> findGardenWarning(int garden_id);
	
	public void delete(Warning entity);
	
	public Warning save(Warning entity);
	
	public void update(Warning entity);
}
