package controller.tourist.android;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import dao.CommentDAO;
import dao.ProductDAO;
import dao.SpotDAO;
import dao.hibernateImpl.CommentDAOHibernateImpl;
import dao.hibernateImpl.ProductDAOHIbernateImpl;
import dao.hibernateImpl.SpotDAOHibernateImpl;
import dataobj.RespSuccessMsg;

import entity.Comment;
import entity.Picture;
import entity.Product;
import entity.Spot;

/**
 * 景点介绍
 * @author fan
 * @version 1.0.0
 */
public class SpotInfoAction implements ServletRequestAware {

	/* 实现servletRequestAware接口需要  */
	private ServletRequest servletRequest;

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	
	/* 获得属性 该属性可以在配置文件中动态指定该属性值  */
	private String inputPath;

	/** 
     * 依赖注入该属性值的setter方法 
     * @param inputPath 
     */ 
    public void setInputPath(String inputPath) { 
        this.inputPath = inputPath; 
    } 
	
    /** 
     * 定义一个返回InputStream的方法；该方法将作为被下载文件的入口 
     * 且需要配置stream类型结果时指定inputName参数 
     * InputName参数的值就是方法去掉个体前缀，首字母小写的字符串 
     * @return 
     */ 
    public InputStream getTargetFile() { 
        //ServeltContext提供getResourceAsStream（）方法返回指定文件对应的输入流 
        return ServletActionContext.getServletContext().getResourceAsStream(inputPath); 
    }

    /**
     * 当前景点，要返回信息时用
     */
    public Spot spot;
    
    public String getContext() {
    	if (spot != null && spot.getInfo() != null)
    		return spot.getInfo().getContext();
    	else 
    		return "";
    }
    
    public float getGrade_level() {
    	if (spot != null && spot.getInfo() != null)
    		return spot.getInfo().getGradeLevel();
    	else 
    		return 0;
    }
    
    public String getSpot_name() {
    	if (spot != null)
    		return spot.getName();
    	else
    		return "";
    }
    
    /**
     * 评论列表，要返回信息时用，只返回十条评论
     */
    private List<Comment> comments;
    
    public List<Comment> getComments() {
    	if  (comments != null)
    		return comments;
    	else 
    		return new ArrayList<Comment>();
    }
    
    /**
     * 图片路径列表，要返回信息时用
     */
    private List<Picture> pics;
    
    public List<Picture> getPics() {
    	if (pics != null)
    		return pics;
    	else 
    		return new ArrayList<Picture>();
    }
    
    /**
     * 餐饮住宿价格列表，要返回信息时用
     */
    private List<Product> products;
    
    public List<Product> getProducts() {
    	if (products != null)
    		return products;
    	else 
    		return new ArrayList<Product>();
    }
    
    public RespSuccessMsg getSuccessMsg() {
    	return new RespSuccessMsg(false, "景点信息查找出错");
    }
    
	/**
	 * 景点控制访问对象, 无需set get方法
	 */
	private static SpotDAO spotDao;
	
	private static CommentDAO commentDao;
	
	private static ProductDAO productDao;
    
	/* 无参构造方法 */
	public SpotInfoAction () {
		SpotInfoAction.spotDao = new SpotDAOHibernateImpl();
		SpotInfoAction.commentDao = new CommentDAOHibernateImpl();
		SpotInfoAction.productDao = new ProductDAOHIbernateImpl();
		/* 返回值初始化 */
		comments = new ArrayList<Comment>();
		pics = new ArrayList<Picture>();
		products = new ArrayList<Product>();
	}
		
	/* 处理请求返回景点详细信息 */
	public String loadspotinfo() {
		Long spot_id = null;
		try {
			
			spot_id = Long.parseLong(servletRequest.getParameter("spot_id"));
			System.out.println("getSpotInfo&Comments:loadspotinfo::spot_id:" + spot_id);

		} catch (NumberFormatException nfe) {
			System.out.println("格式出错");
		}
	    
	    try {
	    	/* 若spot_id不为空, 返回信息 */
	    	if (spot_id != null) {
	    		/* 基本景点信息 */
	    		this.spot = spotDao.findSpotById((long) spot_id);
	    		/* 评论列表 */
	    		comments = commentDao.findSpotLastComments(1, spot_id);
	    		/* 图片路径列表 */
	    		pics = spot.getInfo().getPics();
	    		
	    		/* 如果是餐饮或是住宿 */
	    		System.out.println(this.spot.getType());
	    		if (this.spot.getType() == 3 || this.spot.getType() == 4) {
	    			products = productDao.findSpotLastProducts(1, spot_id);
	    			return "success_resthotel";
	    		}
	    	} else 
	    		return "failed";
	    } catch (Exception he) {
	    	he.printStackTrace();
	    	System.out.println("failed");
	    	return "failed";
	    }
	    return "success_spot";
	}
	
	public String download_spotpics() {
		String spot_id = servletRequest.getParameter("spot_id");
	    System.out.println("getSpotPics::spot_id:" + spot_id);
		return "success";
	}

	public String download_spotvideo() {
		String spot_id = servletRequest.getParameter("spot_id");
	    System.out.println("getSpotVideo::spot_id:" + spot_id);
		return "success";
	}
	
	public String download_spotvoice() {
		String spot_id = servletRequest.getParameter("spot_id");
	    System.out.println("getSpotVoice::spot_id:" + spot_id);
		return "success";
	}
}
