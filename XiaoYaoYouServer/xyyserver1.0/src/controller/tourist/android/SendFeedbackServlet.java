package controller.tourist.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.FeedbackService;

public class SendFeedbackServlet  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4595426532285453270L;

	/**
	 * 评论数据访问接口对象, 无需set get方法
	 */
	private static FeedbackService feedbackService;
	
	public SendFeedbackServlet() {
		super();
		SendFeedbackServlet.feedbackService = new FeedbackService();
	}
	
	/**
	 * Call servlet
	 * 
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String username = request.getParameter("username");
		String feedback = request.getParameter("feedback");
		
		System.out.println("SendFeedbackServlet.post::username:" + username + ", feedback:" + feedback);
		
		PrintWriter out = response.getWriter();
		
	    String msg = "success:true";
		
	    int result = feedbackService.sendFeedback(username, feedback, 29101);
		
	    if (result == 1 || result == 2)
	    	msg = "success:false";
		out.print(msg);
		out.flush();
		return;
	}
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
}
