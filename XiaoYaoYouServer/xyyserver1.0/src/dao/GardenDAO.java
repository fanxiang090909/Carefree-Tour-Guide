package dao;

import entity.Garden;

public interface GardenDAO {
	public Garden findById(Integer gardenid);
	
	public void delete(Garden entity);
	
	public Garden save(Garden entity);

	public void update(Garden entity);
}
