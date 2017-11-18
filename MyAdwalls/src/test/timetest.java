package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timetest {
	public static void main(String[] args) {
	//	String currentTime = System.currentTimeMillis() + "";
	//	System.out.print(currentTime);
	
		   Calendar  calendar = Calendar.getInstance();
		   calendar.setTime(new Date());
		  String str = (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
		  System.out.println(str);
		   
	}

}
