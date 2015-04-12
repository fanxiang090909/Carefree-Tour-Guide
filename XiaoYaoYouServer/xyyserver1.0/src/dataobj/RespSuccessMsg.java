package dataobj;

import java.io.Serializable;

public class RespSuccessMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 247643653224393135L;

	private Boolean su1ccess;
	
	private String msg;
	
	public RespSuccessMsg() {
		
	}
	
	public RespSuccessMsg(Boolean su1ccess, String msg) {
		super();
		this.su1ccess = su1ccess;
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return su1ccess;
	}

	public void setSuccess(Boolean b) {
		this.su1ccess = b;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
