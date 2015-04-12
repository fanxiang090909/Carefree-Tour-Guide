package controller.tourist.android;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

public class VideoAction implements ServletResponseAware {
	
	/* 实现servletRequestAware接口需要  */
	private ServletRequest servletRequest;

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	
	private ServletResponse servletResponse;
	
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;		
	}
	
	public VideoAction() {
		
	}
	
	public String download_spotvideo() {
		
		try {
			int i = (int)(Math.random() * 3) + 1;
			servletResponse.getWriter().print("shipin" + i);
			servletResponse.getWriter().flush();
		} catch (IOException ioe) {
			System.out.print("IO异常"); 
			ioe.printStackTrace();
		}
		
		return null;
	}
}
