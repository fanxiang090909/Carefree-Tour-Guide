package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.jws.WebParam.Mode;

import org.hibernate.HibernateException;

import service.readMapLine.LinePoint;
import service.readMapLine.RoadLinesReader;

import dao.SpotDAO;
import dao.TouristDAO;
import dao.TouristRouteDAO;
import dao.hibernateImpl.SpotDAOHibernateImpl;
import dao.hibernateImpl.TouristDAOHibernateImpl;
import dao.hibernateImpl.TouristRouteDAOHibernateImpl;
import dataobj.RoutePoint;
import entity.Spot;
import entity.Tourist;
import entity.TouristRoutePoint;

public class CalculateRouteService {
	
	private static SpotDAO spotDao;
	
	private static TouristDAO tourDao;
	
	private static TouristRouteDAO tourRouteDao;

	private static RoadLinesReader roadLinesReader;
	
	/****************路线计算静态初始化数据************************/
	
	/**
	 * 景区路线文件读取到的静态路线，有几条路，每条路上有许多点
	 */
	private static List<LinePoint[]> list;
	
	/**
	 * 每条路线中点的个数
	 */
	private static int[] listLength;
	
	/**
	 * 线路数组下标增长方向是否与通向中心交汇点的方向一致
	 * int[i]为第i条路线，值为1为方向一致，2为方向相反
	 */
	private static int[] lineArrayDirection;
	
	/**
	 * 赤道半径长度
	 * 在findOnePointNearest
	 * 和findStartEndPointsNearest方法使用
	 */
	private final static double EQUATOR_RADIUS = 6371.004;
	
	/****************路线计算中间过程数据************************/
	/**
	 * 中间过程路线点栈
	 */
	private static Stack<LinePoint> stack;
	
	/****************路线计算结果数据************************/
	/**
	 * 长度
	 */
	private static int minLength = 0;
	/**
	 * 时间
	 */
	private static int minDuration = 0;
	
	public CalculateRouteService() {
		CalculateRouteService.stack = new Stack<LinePoint>();
		CalculateRouteService.tourDao = new TouristDAOHibernateImpl();
		CalculateRouteService.spotDao = new SpotDAOHibernateImpl();
		CalculateRouteService.tourRouteDao = new TouristRouteDAOHibernateImpl();
		CalculateRouteService.roadLinesReader = RoadLinesReader.getSingleton();
	}
	
	public int saveRoute(double currentlat, double currentlng, String username) {
		try {
			Tourist meTourist = tourDao.findByTouristId(username);
			if  (meTourist != null) {
				/* 记录游客位置时间 */
				java.util.Date touristRouteTime = new java.util.Date();
				
				TouristRoutePoint newPoint = new TouristRoutePoint();
				newPoint.setLat(currentlat);
				newPoint.setLng(currentlng);
				newPoint.setTime(touristRouteTime);
				
				newPoint.setTourist(meTourist);
				tourRouteDao.savePoint(newPoint);
			} else {
				return 1;
			}
		} catch (HibernateException he) {
			he.printStackTrace();
			return 2;
		}
		return 0;
	}
	
	/**
	 * 得到某一游客当前的位置。好友定位时使用
	 * @param username
	 * @return
	 */
	public TouristRoutePoint getTouristRouteCurrentPoint(String username) {
		return tourRouteDao.findTouristRouteCurrentPoint(username);
	}
	
	
	/*  *************未完成   *************/
	/**
	 * 得到某一游客的已走路线列表
	 * @param inputstring
	 * @param mode 为1或2
	 * 				1为使用用户名查找已走路线列表
	 * 				2为使用手机号查找已走路线列表
	 * @return
	 */
	public List<TouristRoutePoint> getTouristsRoutePoints(String inputString, int mode) {
		if (mode == 1)
			return tourRouteDao.findTouristRoutePoints_OneDay(inputString);
		else if (mode == 2) {
			Long phone = Long.parseLong(inputString);
			Tourist tourist = tourDao.findByPhone(phone);
			return tourRouteDao.findTouristRoutePoints_OneDay(tourist.getId());
		}
		return null;
		/* 以他暂代 */
		//return tourRouteDao.findLatestTouristsPoints(29101);
	}
	
	/**
	 * 方便管理员查看，得到路线之中的景点附近点
	 * @param username
	 * @return
	 */
	public List<RoutePoint> getTouristSpotRoutePoint(String username) {
		System.out.println("方法CalculateRouteService");
		
		/* 初始化结果点 */
		List<RoutePoint> resultList = new ArrayList<RoutePoint>();
		
		List<TouristRoutePoint> touristRoutePoint = tourRouteDao.findTouristRoutePoints_OneDay(username);
		TouristRoutePoint currentPoint;
		List<Spot> spots = spotDao.findTypeSpotsByGardenId(29101, SpotDAO.SpotType.ScenicSpot);
		
		for (int i = 0; i < touristRoutePoint.size(); i++) {
			
			currentPoint = touristRoutePoint.get(i);
			
			for (int j = 0; j < spots.size(); j++) {
				Spot spot = spots.get(j);
				
				double MLatA = 90 - spot.getCentLat();
				double MLatB = 90 - currentPoint.getLat();
				double MLonDev = spot.getCentLng() - currentPoint.getLng();
				double C = Math.sin(MLatA) * Math.sin(MLatB) * Math.cos(MLonDev)
						+ Math.cos(MLatA) * Math.cos(MLatB);
				double distance = EQUATOR_RADIUS * Math.acos(C) * Math.PI / 180 * 1000;
				if (distance < 300) {
					/* 避免重复 */
					boolean canadd = true;
					for (int m = 0; m < resultList.size(); m++)
						if (resultList.get(m).getSpot_id().equals(spot.getId()))
							canadd = false;
					if (canadd) {
						resultList.add(new RoutePoint(currentPoint.getTime(), spot.getId(),spot.getName()));
						System.out.println(distance + spot.getName() + canadd);
					}
				}
				//System.out.println("distance=" + distance);

			}
		}
		return resultList;
	}
	
	/**
	 * 初始化读文件luxian.txt，存入List<LinePoint[]> list中
	 * @throws NumberFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void initialRoadLines() 
			throws NumberFormatException, FileNotFoundException, IOException {
		if (list != null && !list.isEmpty())
			CalculateRouteService.list.clear();
		/******************读文件******************/
		CalculateRouteService.list = roadLinesReader.loadLinePoints("d:\\luxian29101.txt");
		
		/******************初始化路线点数组长度******************/
		listLength = new int[list.size()];
		for (int j = 0; j < list.size(); j++) {
			LinePoint[] points = list.get(j);
			//System.out.println("size:" + list.size() + "length:" + points.length + "第" + j + "数组");
			for (int i = 0; i < points.length && points[i] != null; i++) {
				listLength[j] = i;
				//System.out.println(points[i].getLat());
			}
		}
	}
	
	/**
	 * 初始化路线通向景区中心汇聚点的方向
	 * @throws NumberFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void initialLineDirection()
			throws NumberFormatException, FileNotFoundException, IOException {
		CalculateRouteService.lineArrayDirection = new int[list.size()]; 
		lineArrayDirection[0] = 1; //正向
		lineArrayDirection[1] = 0; //反向
		lineArrayDirection[2] = 0; //反向
		lineArrayDirection[3] = 0; //反向
		lineArrayDirection[4] = 0; //反向
	}
	
	/**
	 * 查找某一经纬度最近路线点，路线点为首次运行时通过initialRoadLines方法静态属性中加载
	 * @param currentLat
	 * @param currentLng
	 * @return 返回长度为2的int型数组，下标为0表示在哪个数组，下标1在数组的哪个元素位置
	 * 			如果是华山，则是0-4范围，下标为1表示元素位置，一般在0-29
	 */
	public int[] findOnePointNearest(Double currentLat, Double currentLng) {
		/* 返回结果 */
		int[] result = new int[2];
		double minLength = 0;

		/**
		 * 地球是一个近乎标准的椭球体，
		 * 它的赤道半径为6378.140千米，极半径为 6356.755千米，平均半径6371.004千米。
		 * 如果我们假设地球是一个完美的球体，那么它的半径就是地球的平均半径，记为R。
		 * 如果以0度经线为基 准，那么根据地球表面任意两点的经纬度就可以计算出这两点间的地表距离
		 * （这里忽略地球表面地形对计算带来的误差，仅仅是理论上的估算值）。
		 * 设第一点A的经 纬度为(LonA, LatA)，第二点B的经纬度为(LonB, LatB)，
		 * 按照0度经线的基准，东经取经度的正值(Longitude)，西经取经度负值(-Longitude)，
		 * 北纬取90-纬度值(90- Latitude)，南纬取90+纬度值(90+Latitude)，
		 * 则经过上述处理过后的两点被计为(MLonA, MLatA)和(MLonB, MLatB)。
		 * 
		 * 那么根据三角推导，可以得到计算两点距离的如下公式：
		 * C = sin(MLatA)*sin(MLatB)*cos(MLonA-MLonB) + cos(MLatA)*cos(MLatB)
		 * Distance = R*Arccos(C)*Pi/180
		 * 这里，R和Distance单位是相同，如果是采用6371.004千米作为半径，那么Distance就是千米为单位，
		 * 如果要使用其他单位，比如mile，还需要做单位换算，1千米=0.621371192mile
		 */
		
		for (int j = 0; j < listLength.length; j++) {
			LinePoint[] points = list.get(j);
			for (int i = 0; i < points.length && points[i] != null; i++) {
				double MLatA = 90 - points[i].getLat();
				double MLatB = 90 - currentLat;
				double MLonDev = points[i].getLng() - currentLng;
				double C = Math.sin(MLatA) * Math.sin(MLatB) * Math.cos(MLonDev)
						+ Math.cos(MLatA) * Math.cos(MLatB);
				double distance = EQUATOR_RADIUS * Math.acos(C) * Math.PI / 180 * 1000;
				
				if (i == 0 && j == 0) {
					minLength = distance;
					result[0] = 0;
					result[1] = 0;
				} else {
					if (distance < minLength) {
						minLength = distance;
						result[0] = j;
						result[1] = i;
					}
				}
				
				System.out.println(points[i].getLat());
			}
		}
		
		/* debug使用 */
		LinePoint[] nearistpoints = list.get(result[0]);
		
		System.out.println("附近坐标点:" + nearistpoints[result[1]].getLat() +
				", " + nearistpoints[result[1]].getLng());
		System.out.println("所在路线和点:" + "Array:" + result[0] + ", point:" + result[1]);

		System.out.println("最短距离:" + minLength);

		return result;
	}
	
	/**
	 * 查找起始和终止经纬度最近路线点，
	 * 该方法比findStartEndPointsNearest方法查找速度快
	 * 路线点为首次运行时通过initialRoadLines方法静态属性中加载
	 * @param startLat
	 * @param startLng
	 * @param endLat
	 * @param endLng
	 * @return 返回长度为4的int型数组，
	 * 			下标为0表示start位置在哪个数组，下标1表示start位置在数组的哪个元素位置
 	 * 			下标为1表示end位置在哪个数组，下标3表示end位置在数组的哪个元素位置
	 * 			如果是华山，则是0-4范围，下标为1表示元素位置，一般在0-29
	 */
	public int[] findStartEndPointsNearest(Double startLat, Double startLng,
											Double endLat, Double endLng) {
		/* 返回结果 */
		int[] result = new int[4];
		double minLengthToStart = 0;
		double minLengthToEnd = 0;

		/**
		 * 地球是一个近乎标准的椭球体，
		 * 它的赤道半径为6378.140千米，极半径为 6356.755千米，平均半径6371.004千米。
		 * 如果我们假设地球是一个完美的球体，那么它的半径就是地球的平均半径，记为R。
		 * 如果以0度经线为基 准，那么根据地球表面任意两点的经纬度就可以计算出这两点间的地表距离
		 * （这里忽略地球表面地形对计算带来的误差，仅仅是理论上的估算值）。
		 * 设第一点A的经 纬度为(LonA, LatA)，第二点B的经纬度为(LonB, LatB)，
		 * 按照0度经线的基准，东经取经度的正值(Longitude)，西经取经度负值(-Longitude)，
		 * 北纬取90-纬度值(90- Latitude)，南纬取90+纬度值(90+Latitude)，
		 * 则经过上述处理过后的两点被计为(MLonA, MLatA)和(MLonB, MLatB)。
		 * 
		 * 那么根据三角推导，可以得到计算两点距离的如下公式：
		 * C = sin(MLatA)*sin(MLatB)*cos(MLonA-MLonB) + cos(MLatA)*cos(MLatB)
		 * Distance = R*Arccos(C)*Pi/180
		 * 这里，R和Distance单位是相同，如果是采用6371.004千米作为半径，那么Distance就是千米为单位，
		 * 如果要使用其他单位，比如mile，还需要做单位换算，1千米=0.621371192mile
		 */
		
		for (int j = 0; j < listLength.length; j++) {
			LinePoint[] points = list.get(j);
			for (int i = 0; i < points.length && points[i] != null; i++) {
				double MLatA = 90 - points[i].getLat();
				double MLatBStart = 90 - startLat;
				double MLonDevStart = points[i].getLng() - startLng;
				double CToStart = Math.sin(MLatA) * Math.sin(MLatBStart) * Math.cos(MLonDevStart)
						+ Math.cos(MLatA) * Math.cos(MLatBStart);
				double distanceToStart = EQUATOR_RADIUS * Math.acos(CToStart) * Math.PI / 180 * 1000;
				
				double MLatBEnd = 90 - endLat;
				double MLonDevEnd = points[i].getLng() - endLng;
				double CToEnd = Math.sin(MLatA) * Math.sin(MLatBEnd) * Math.cos(MLonDevEnd)
						+ Math.cos(MLatA) * Math.cos(MLatBEnd);
				double distanceToEnd = EQUATOR_RADIUS * Math.acos(CToEnd) * Math.PI / 180 * 1000;
				
				if (i == 0 && j == 0) {
					minLengthToStart = distanceToStart;
					minLengthToEnd = distanceToEnd;
					result[0] = 0;
					result[1] = 0;
					result[2] = 0;
					result[3] = 0;
				} else {
					if (distanceToStart < minLengthToStart) {
						minLengthToStart = distanceToStart;
						result[0] = j;
						result[1] = i;
					}
					if (distanceToEnd < minLengthToEnd){
						minLengthToEnd = distanceToEnd;
						result[2] = j;
						result[3] = i;
					}
				}
				
				//System.out.println(points[i].getLat());
			}
		}
		
		/* debug使用 */
		//LinePoint[] startpoints = list.get(result[0]);
		//LinePoint[] endpoints = list.get(result[2]);
		
		//System.out.println("起点附近坐标点:" + startpoints[result[1]].getLat() +
		//		", " + startpoints[result[1]].getLng());
		System.out.println("起点所在路线和点:" + "Array:" + result[0] + ", point:" + result[1]);
		System.out.println("起点最短距离:" + minLengthToStart);

		//System.out.println("终点附近坐标点:" + endpoints[result[3]].getLat() +
		//		", " + endpoints[result[3]].getLng());
		System.out.println("终点所在路线和点:" + "Array:" + result[2] + ", point:" + result[3]);
		System.out.println("终点最短距离:" + minLengthToEnd);

		return result;
	}
	
	/**
	 * 得到去往目的地的路线沿途点的栈
	 * 该栈栈顶为目的地
	 * 栈底为出发地点
	 * 
	 * @param currentlat
	 * @param currentlng
	 * @param destlat
	 * @param destlng
	 * @return
	 */
	public Stack<LinePoint> getRoutePointStackToDest(double currentlat, double currentlng, 
			Double destlat, Double destlng) {
		/* 每次计算前把栈清空 */
		stack.empty();
		
		int[] result = findStartEndPointsNearest(currentlat, currentlng, destlat, destlng);
			
		int direction = 0;
		/* 如果起始点按数组正向，方向一致，即按正向开始走 */
		//System.out.println(result[0] + " " + result[1] + " " + result[2] + " " + result[3]);
		//System.out.println(lineArrayDirection.length);
		
		if (lineArrayDirection[result[0]] == 1) {
			direction = +1;
		} else { /* 如果起始点按数组反向，方向一致，即按反向开始走 */
			direction = -1;
		}

		int currentLine = result[0];
		int currentPoint = result[1];
		
		System.out.println("all:currentLine:" + currentLine
				+ ", currentPoint:" + currentPoint);
		
		//for (int i = 0; i < listLength.length; i++)
		//System.out.println("各数组长度:线路:" + i + "长度:" + listLength[i]);
		
		while (currentLine != result[2] || currentPoint != result[3]) {
			if (currentPoint > listLength[currentLine] || 
					currentPoint < 0) {
				currentLine = result[2];
				/* 如果终止点按数组正向，方向相反，即按反向开始走 */
				if (lineArrayDirection[result[2]] == 1) {
					currentPoint = listLength[currentLine];
					direction = -1;
				} else { /* 如果终止点按数组反向，方向为正，即按正向开始走 */
					currentPoint = 0;
					direction = +1;
				}
			}
			
			/* 查看栈顶路线点是否和新点相等，若不等，新点压栈 */
			if (stack.isEmpty() || ! (list.get(currentLine)[currentPoint]).equals(stack.peek())) {
				stack.push(list.get(currentLine)[currentPoint]);
				System.out.println("push:" + "currentLine:" + currentLine
						+ ", currentPoint:" + currentPoint
						+ ", direction:" + direction);
			} else { /* 若相等，旧点出栈 */
				stack.pop();
				System.out.println("pop:" + "currentLine:" + currentLine
										+ ", currentPoint:" + currentPoint
										+ ", direction:" + direction);
			}
			
			currentPoint += direction;
			
			//System.out.println(stack.peek().getLat() + " vs " + list.get(currentLine)[currentPoint].getLat());
		}
		return stack;
	}
	
	/**
	 * 得到去往目的地的路线沿途点
	 * @param currentlat
	 * @param currentlng
	 * @param destlat
	 * @param destlng
	 * @return
	 */
	public List<TouristRoutePoint> getRoutePointsToDest(double currentlat, double currentlng, 
			Double destlat, Double destlng) {
		
		try {
			if (list == null || list.isEmpty()) {
				this.initialRoadLines();
				this.initialLineDirection();
			}
			this.getRoutePointStackToDest(currentlat, currentlng,
							destlat, destlng);

		} catch (HibernateException he) {
			he.printStackTrace();
		} catch (NumberFormatException e) {
		    System.out.println("CalculateRouteService:getRoutePointsToDest--NumberFormatException");

			e.printStackTrace();
		} catch (FileNotFoundException e) {
		    System.out.println("CalculateRouteService:getRoutePointsToDest--FileNotFoundException");

			e.printStackTrace();
		} catch (IOException e) {
		    System.out.println("CalculateRouteService:getRoutePointsToDest--IOException");

			e.printStackTrace();
		}
		
		/* 初始化返回结果数组 */
		List<TouristRoutePoint> shortestRoutePoints = 
								new	ArrayList<TouristRoutePoint>();

		/* 初始化最短长度和最短用时 */
		minLength = 0;
		minDuration = 0;
				
		/* 如果结果路线计算点栈中不为空 */
		while (!stack.isEmpty()) {
			LinePoint linePoint = stack.pop();
			TouristRoutePoint touristRoutePoint = 
					new TouristRoutePoint(linePoint.getLat(), linePoint.getLng());
			
			minLength += linePoint.getLength();
			minDuration += linePoint.getDuration();
			
			/* 加入到路线返回结果点中 */
			shortestRoutePoints.add(touristRoutePoint);
		}
		
		/* 返回结果路线点 */
		return shortestRoutePoints;
	}
	
	public int getMinLength() {
		return minLength;
	}
	
	public int getMinDuration() {
		return minDuration;
	}
}
