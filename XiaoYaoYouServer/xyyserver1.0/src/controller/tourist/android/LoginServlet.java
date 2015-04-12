package controller.tourist.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TouristRouteDAO;
import dao.hibernateImpl.TouristRouteDAOHibernateImpl;

import service.ValidateLogin;

/**
 * The Login Servlet
 * @author fan
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	/**
	 * 验证用户登陆服务属性，在service，业务逻辑层定义
	 */
	private static ValidateLogin validateLogin;
	
	
	private static TouristRouteDAO touristRouteDao;
	
	/**
	 * 构造方法
	 */
	public LoginServlet() {
		super();
		LoginServlet.validateLogin = new ValidateLogin();
		touristRouteDao = new TouristRouteDAOHibernateImpl();

	}
	
	/** 
	 * Login Servlet
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("LoginServlet.post::username:" + username + ", password：" + password);
		
		/* 验证用户名密码 */
		int result = validateLogin.hasRegistered(username, password);
		
		/* 处理错误 */
	    String msg = "success:true,message:login servlet successfully"; 
	    if (result == 2) {
			msg = "success:false,message:密码错误";
		} else if (result == 1) {
			msg = "success:false,message:用户名不存在";
		}
		
	    /* 登陆正确 */
		/* 清空该游客已走路线 */
		touristRouteDao.deleteTouristPoints_All(username);
		System.out.println("清空");
	    
		/* 存入session作用域中 */
		HttpSession session = request.getSession(true);
		session.setAttribute("meusername", username);
		session.setAttribute("clientIpaddr", request.getRemoteAddr());
		session.setAttribute("clientPort", request.getRemotePort());

		session.setMaxInactiveInterval(3600 * 3);
		
		PrintWriter out = response.getWriter();
 
		out.print(msg);
	}

}