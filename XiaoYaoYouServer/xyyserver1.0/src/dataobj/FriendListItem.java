package dataobj;

/**
 * 好友列表项
 * @author fan
 * @version 1.0
 */
public class FriendListItem {
	
	private String username;

	private Integer picture;

	//private Boolean isConfirm;
	
	public FriendListItem() {
		super();
	}

	public FriendListItem(String username, Integer picture) {
		super();
		this.username = username;
		this.picture = picture;
		//this.isConfirm = isConfirm;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPicture() {
		return picture;
	}

	public void setPicture(Integer picture) {
		this.picture = picture;
	}

/*	public Boolean getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Boolean isConfirm) {
		this.isConfirm = isConfirm;
	}*/
}
