package controller.tourist.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CommentService;

public class SpotCommentServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6467052130329393554L;

	private static CommentService commentService;
	
	public SpotCommentServlet () {
		super();
		commentService = new CommentService();
	}
	
	/** 
	 * Fist Servlet
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String commentContent = request.getParameter("content");
		String spot_id = request.getParameter("spot_id");

		/* 登录才发送位置。检查是否在http的session回话作用域中存入属性meTourist */
		
		try {
		    			
		    System.out.println("SpotCommentServlet.post::username:" + username + ", nickname:" + nickname + ", content:" + commentContent + ",spot" + spot_id);

			/* 存入数据库 */
		    int result = commentService.sendComment(username, commentContent, 
		    		(float) 0, Long.parseLong(spot_id), nickname);
			
		    PrintWriter out = response.getWriter();
			
			String msg = "success:true";  

			if (result == 1 || result == 2)
				msg = "success:false";
			
			out.print(msg);
		} catch (NumberFormatException nfe) {
			/* 如果格式有问题则出现未知错误 */
			response.getWriter().print("success:false");
		}
	}
	
	/** 
	 * Fist Servlet
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		this.doGet(request, response);
	}
}
