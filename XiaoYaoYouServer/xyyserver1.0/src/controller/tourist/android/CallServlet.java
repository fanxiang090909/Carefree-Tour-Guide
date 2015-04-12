package controller.tourist.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.TouristCallService;

public class CallServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static TouristCallService touristCallService;
	
	public CallServlet() {
		super();
		touristCallService = new TouristCallService();
	}
	
	/**
	 * Call servlet
	 * 
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String username = request.getParameter("username");
		//获得请求用户的位置： 纬度，经度
		double latitude = Double.parseDouble(request.getParameter("lat"));
		double longitude = Double.parseDouble(request.getParameter("lng"));
		
		System.out.println("CallServlet.post::username:" + username + ", Lat:" + latitude + ",Lng:"
				+ longitude);
		
	    //验证用户名密码 
		int result = touristCallService.sendTouristCall(username, latitude, longitude);
				
		PrintWriter out = response.getWriter();
		String msg = "";
		if (result == 0)
			msg = "success:true";  

		else 
			msg = "success:false";
		
		out.print(msg);
		out.flush();
	}

}
