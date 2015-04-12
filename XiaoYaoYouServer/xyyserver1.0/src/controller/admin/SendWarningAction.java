package controller.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import service.WarningService;

import net.sf.json.JSONObject;

public class SendWarningAction implements ServletResponseAware, SessionAware {
	
	/**
	 *  APNSAPI 7天需要更改一次
	 *  Change the chId and apiKey to yourself
	 *  修改 APNS 初始参数: ChId, apiKey 为你自己的
	 */
	/*private final static String SERVICE_ChId = "2630";
	private final static String SERVICE_APIKEY = "thyrliw41b";
	private final static String SERVICE_NAMESTRING = "nwpuxyy";*/
	
	private String warnInfo;

	public void setWarnInfo(String warnInfo) {
		this.warnInfo = warnInfo;
	}
	
	private ServletResponse servletResponse;
	
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}
	
	/**
	 * 操作Http 会话session作用域时使用
	 */
	private Map<String, Object> sessionMap;
	
	public void setSession(Map<String, Object> map) {
		this.sessionMap = map;
	}
	
	private static WarningService warningService;
	
	public SendWarningAction() {
		warningService = new WarningService();
	}
	
	public String send_warning() {
		System.out.println("AdminSendWarningAction2:" + warnInfo);

		JSONObject jsonObject = JSONObject.fromObject(warnInfo);
		//String username = (String) jsonObject.get("username");
		String content = (String) jsonObject.get("content");
		String listString = (String)jsonObject.get("region_list");

		/* 解析预警区域数组 */
		List<Integer> list = new ArrayList<Integer>();
		StringTokenizer tokenizer = new StringTokenizer(listString, ",");
		while (tokenizer.hasMoreTokens()) {
			list.add(Integer.parseInt(tokenizer.nextToken().toString()));
		}
		
		/* session中取用户名 */
		String username = (String) sessionMap.get("myUsername");
		if (username != null)
		
		//System.out.print("AdminSendWarningAction:" + username + ",content:" + content);
		for (Integer i : list)
			System.out.print(",list--" + i.toString());
		
		int serviceResultInt = 0;
		String serviceResultMsg = "";
		
		/* 弃用
		// APNSAPI 7天需要更改一次 
		// Change the chId and apiKey to yourself
		// 修改 APNS 初始参数: ChId, apiKey 为你自己的
		APNS apns = new APNS(SERVICE_ChId, SERVICE_APIKEY);
		//send msg
		String response = apns.push(SERVICE_NAMESTRING, warnInfo);
		//print response, the response is XML format string
		System.out.println("send service response" + response);
		
		switch (serviceResultInt) {
			case  -1: serviceResultMsg = "can’t connect to the cloud"; break;	
			case  0:  serviceResultMsg = "send seccessfully"; break; 
			case  1:  serviceResultMsg = "access failed"; break;  
			case  2:  serviceResultMsg = "permission failed"; break; 
			case  3:  serviceResultMsg = "device not onling"; break; 
			case  12: serviceResultMsg = "chanel  expired"; break; 
			case  13: serviceResultMsg = "hash code not match"; break; 
			case  14: serviceResultMsg = "illege para"; break;  
			case  15: serviceResultMsg = "unkown err"; break; 
			default:
				serviceResultMsg = "unkown err";
				break;
		}*/
		
		/*try {
			String clientIPAddr = "10.128.51.78";
			int clientPort = 9988;
			Socket socket = new Socket(clientIPAddr, clientPort);
			//由系统标准输入设备构造BufferedReader对象
			PrintWriter os=new PrintWriter(socket.getOutputStream());
			//由Socket对象得到输出流，并构造PrintWriter对象
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//由Socket对象得到输入流，并构造相应的BufferedReader对象
			//String readline;
			//readline=sin.readLine(); //从系统标准输入读入一字符串
			
			//while(!readline.equals("bye")){
			//若从标准输入读入的字符串为 "bye"则停止循环
			os.println(content);
			//将从系统标准输入读入的字符串输出到Server
			os.flush();
			//刷新输出流，使Server马上收到该字符串
			//System.out.println("Client:"+readline);
			//在系统标准输出上打印读入的字符串
			System.out.println("Server:" + is.readLine());
			//从Server读入一字符串，并打印到标准输出上
			//readline=sin.readLine(); //从系统标准输入读入一字符串
			//} //继续循环
			os.close(); //关闭Socket输出流
			is.close(); //关闭Socket输入流
			socket.close(); //关闭Socket
		} catch (IOException e) {
			System.out.println("SendWarningAction--socket错误");
			e.printStackTrace();
			serviceResultInt = 1;
			serviceResultMsg = "socket连接错误";
		}*/
		
		/* 方案二：存入数据库中 */
		int result = warningService.sendWarningAction("fanxiang", content, 0, 29101);
		
		String msg = "";
		if (result == 0)
			msg = "{\"success\":true,\"msg\":\"" + serviceResultMsg + "\"}";
		else {
			msg = "{\"success\":false,\"msg\":\"" + serviceResultMsg + "\"}";
		}
		try {
			servletResponse.getWriter().print(msg);
			servletResponse.getWriter().flush();
		} catch (IOException ioe) {
			System.out.print("IO异常"); 
			ioe.printStackTrace();
		}
		
		/* 没有result配置， 返回null */
		return null;
	}


}
