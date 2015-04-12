package service.readMapLine;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RoadLinesReader {


	private final static String DELIM = "\t";
	private static PrintWriter  stdErr = new  PrintWriter(System.err, true);
	
	private static int currentArrayNum = 0;
	
	/* 每条路线点数组长度 */
	private final static int POINTARRAY_LENGTH = 30;
	
	private static RoadLinesReader instance = new RoadLinesReader();
	
	/**
	 * 单件设计模式
	 */
	private RoadLinesReader() {}
	
	public static RoadLinesReader getSingleton() {
		return instance;
	}
	
	private LinePoint readLinePointItems (String line) {

		StringTokenizer tokenizer = new StringTokenizer(line,DELIM);
		int numberOfTokens = tokenizer.countTokens();
		if (numberOfTokens > 7 || numberOfTokens < 4) {
			throw new NumberFormatException("numberFormatException");
		} else {
			//System.out.println("currentArrayNum:" + currentArrayNum);

			
			String tokenOne = "";
			String tokenFour = "";
			String tokenFive = "";
			String tokenSix = "";
			String tokenSeven = "";
			if (numberOfTokens == 7) {
				tokenOne = tokenizer.nextToken();
				tokenizer.nextToken();
				tokenizer.nextToken();
				tokenFour = tokenizer.nextToken();
				tokenFive = tokenizer.nextToken();
				tokenSix = tokenizer.nextToken();
				tokenSeven = tokenizer.nextToken();
			}
			
			if (numberOfTokens == 6) {
				tokenizer.nextToken();
				tokenizer.nextToken();
				tokenFour = tokenizer.nextToken();
				tokenFive = tokenizer.nextToken();
				tokenSix = tokenizer.nextToken();
				tokenSeven = tokenizer.nextToken();				
			}
			
			if (numberOfTokens == 4) {
				tokenFour = tokenizer.nextToken();
				tokenFive = tokenizer.nextToken();
				tokenSix = tokenizer.nextToken();
				tokenSeven = tokenizer.nextToken();
			}
 				
			try {
				if (!(tokenOne.equals(""))) {
					//System.out.println("tokenOne:" + tokenOne);
					currentArrayNum++;
				}
				//if (!(tokenFour.equals("") && !(tokenFive.equals("")))) {
				//	System.out.println(currentArrayNum + "tokenFour:" + tokenFour);
				//	System.out.println(currentArrayNum + "tokenFive:" + tokenFive);
				//	System.out.println(currentArrayNum + "tokenSix:" + tokenSix);
				//	System.out.println(currentArrayNum + "tokenSeven:" + tokenSeven);
				//}
				
				return new LinePoint(Double.parseDouble(tokenFour), 
									Double.parseDouble(tokenFive), 
									Integer.parseInt(tokenSix), 
									Integer.parseInt(tokenSeven));
				
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				stdErr.println("NumberFormatException in method readLinePointItems" + nfe);
			}
		}
		return null;
	}
	
	  public List<LinePoint[]> loadLinePoints(String filename) throws FileNotFoundException, 
		IOException, NumberFormatException {

			BufferedReader fileIn = new BufferedReader(new FileReader(filename));
			String line = fileIn.readLine();

			List<LinePoint[]> list = new ArrayList<LinePoint[]>();
			LinePoint[] linePoints =  new LinePoint[POINTARRAY_LENGTH];
			
			int j = 0;
			int i = 0;
			LinePoint point = null;
			while (line != null) {
				if (j != currentArrayNum - 1 && currentArrayNum != 0) {
					list.add(linePoints);
					linePoints = new LinePoint[POINTARRAY_LENGTH];
					i = 0;
					j++;
				}
				if (point != null) {
					linePoints[i] = point;
					i++;
				}
				point = readLinePointItems(line);
				line = fileIn.readLine();

			}
			
			/* 最后剩一个数组 */
			list.add(linePoints);

			fileIn.close();
			return list;
		}

}