package controller.tourist.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import dao.TouristDAO;
import dao.WarningDAO;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import dao.hibernateImpl.WarningDAOHibernateImpl;
import entity.Tourist;
import entity.Warning;

/**
 * android手机端注册
 * @author fan
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
		
	private static TouristDAO touristDao;
	
	private static WarningDAO warningDao;
	
	public RegisterServlet () {
		RegisterServlet.touristDao = new TouristDAOHibernateImpl();
		warningDao = new WarningDAOHibernateImpl();
	}
	
	/* 
	 * Fist Servlet
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		Long phone = Long.parseLong(request.getParameter("phonenumber"));
		String password = request.getParameter("password");
		
		System.out.println("RegisterServlet.post::username:" + username + ", phone:" + phone + ", password:" + password);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		
		String msg = "success:false";
		
		/* 两次密码相同已在客户端验证 */
		/* 1.判断有无该用户名 */
		Tourist currentTourist = touristDao.findByTouristId(username);
		
		if (currentTourist == null) {
			/* 2.如果无用户，注册一个，插入数据库 */
			Warning currentwarn = warningDao.findGardenLatestWarning(29101);
			int currentWarnID = currentwarn.getId();
			
			Tourist entity = new Tourist(username, password, phone, currentWarnID);

			try {
				touristDao.save(entity);
			} catch (HibernateException he) {
				/* 不成功，返回 */
				msg = java.net.URLEncoder.encode("success:true, msg:\"注册失败，不能存储信息!\"", "UTF-8"); 
			}
			/* 返回消息 */
		    msg = java.net.URLEncoder.encode("success:true, msg:\"注册成功!\"", "UTF-8"); 
		}

	    // json格式
	    //String msg = "{\"success\":true,\"message\":\"login servlet successfully!\"}";  
		PrintWriter out = response.getWriter();
		out.print(msg);
	}
}
