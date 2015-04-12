package controller.admin;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import dao.AdminDAO;
import dao.TouristRouteDAO;
import dao.hibernateImpl.AdminDAOHibernateImpl;
import dao.hibernateImpl.TouristRouteDAOHibernateImpl;
import dataobj.RespSuccessMsg;
import entity.Admin;

/**
 * 管理员浏览器登录
 * 实现SessionAware, ServletRequestAware接口 。参考struts深入浅出 3.2.3
 * 
 * @author fan
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class LoginAction extends ActionSupport implements SessionAware {
	
	/**
	 * 管理员类数据访问接口
	 */
	private static AdminDAO adminDao;
	
	/**
	 * 操作Http 会话session作用域时使用
	 */
	private Map<String, Object> sessionMap;
	
	public void setSession(Map<String, Object> map) {
		this.sessionMap = map;
	}

	private String loginData;
	
	public void setLoginData(String loginData) {
		this.loginData = loginData;
	}

	/**
	 * 返回参数json格式{"success":true, "msg":"ok"}
	 */
	private RespSuccessMsg loginResp;
	
	public RespSuccessMsg getLoginResp() {
		return loginResp;
	}
	
	/**
	 *  必须实现无参构造方法
	 */
	public LoginAction() {
		
		LoginAction.adminDao = new AdminDAOHibernateImpl(); 
		//loginResp = new LoginResp();
	}
	
	public String login() {

		JSONObject jsonObject = JSONObject.fromObject(loginData);
		String username = (String) jsonObject.get("username");
		String password = (String) jsonObject.get("password");
		
		System.out.println("AdminLoginAction:loginData:"+ loginData + ",post：username:" + username + ",passwd:" + password);
		
		/* 查找数据库 */ 
		Admin admin = adminDao.findByUsername(username);
		if (admin == null) {
			loginResp = new RespSuccessMsg(false, "用户名不存在！");
			return "success";
		} 
		if (! admin.getPasswd().equals(password)) {
			loginResp = new RespSuccessMsg(false, "密码错误！");
			return "success";
		}
		
		/* 登录成功 */
	
		/* 存入session */
		String sessionUsername = (String) sessionMap.get("myUsername");
		if (sessionUsername == null || sessionUsername.equals(username));
			sessionMap.put("myUsername", username);
			
		loginResp = new RespSuccessMsg(true, "ok");
		
		return "success";
	}
}
