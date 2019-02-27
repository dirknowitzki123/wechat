package com.by.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
	
	/**
	 * 得到当前时间	
	 * @return	Date
	 */
	public static Date getCurrentTimestamp(){
		return new Date();
	}
	
	
	public static String format(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 
	 * @methodName: getCurrentXsdDateTime
	 * @Description:获取当前时间的字符串，格式为yyyy-MM-ddTHH:mm:ss
	 *              
	 * @return  String 
	 * @author  zhangmin
	 * @date 2015-1-5 下午1:48:39
	 */
	public static String getCurrentXsdDateTime(){
		return getCurrentPrettyDate() + "T" + getCurrentTime();
	}

	/**
	 * 
	 * @methodName: getHourMinuteSecond
	 * @Description:获取时间固定格式HHmmss
	 *              
	 * @param date
	 * @return  String 
	 * @author  zhangmin
	 * @date 2015-1-5 下午1:55:16
	 */
	public static String getHourMinuteSecond(Date date) {
		return new SimpleDateFormat("HHmmss").format(date);
	}
	
	/**
	 * 
	 * @methodName: getYearMonthDay
	 * @Description:获取时间固定格式HHmmss
	 *              
	 * @param date
	 * @return  String 
	 * @author  zhangmin 
	 * @date 2015-1-5 下午1:54:52
	 */
	public static String getYearMonthDay(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	/**
	 * 
	 * @methodName: getUpDayByCurrentDay
	 * @Description:获取当前日期的上一天日期
	 *              
	 * @return  String 
	 * @author  zhangmin 
	 * @date 2015-1-5 下午1:48:45
	 */
	public static String getUpDayByCurrentDay(){
		Calendar c =Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -1);
		String upDay = format(c.getTime(), "yyyyMMdd");
		return upDay;
	}
	
	/**
	 * 
	 * @methodName: getBeforDay
	 * @Description:获取当前日期的前几天
	 *              
	 * @param beforeDay
	 * @return  String 
	 * @author  zhangmin 
	 * @date 2015-1-5 下午1:48:57
	 */
	public static String getBeforDay(int beforeDay){
		Calendar c =Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -beforeDay);
		String upDay = format(c.getTime(), "yyyyMMdd");
		return upDay;
	}
	
	/**
	 * 
	 * @methodName: g
	 * @Description:获取当前时间的字符串，格式为yyyy-MM-ddTHH:mm:ss
	 *              
	 * @return  String 
	 * @author  zhangmin 
	 * @date 2015-1-5 下午1:49:06
	 */
	public static String g(){
		return getCurrentPrettyDate() + "T" + getCurrentTime();
	}
	
	/**
	 * 获取当前时间字符串，格式为yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentPrettyDate(){
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	/**
	 * 获取当前时间字符串，格式为HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime(){
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	/**
	 * 获取当前时间字符串，格式为yyyyMMddHHmmss
	 * @return
	 */
	public static String getCurDateTime() {
		return getCurDate("yyyyMMddHHmmss");
	}
	
	/**
	 * 获取当前时间
	 * @param pattern 格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurDate(String pattern){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);
		return df.format(date);
	}
	
	/**
	 * 字符串转换为日期
	 * @param date 日期字符串
	 * @param patten 格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date parse(String date, String patten){
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
		}
		return null;
	}
	
	/**
	 * 校验字符串格式的日期是否合法
	 * @param date
	 * @param pattern
	 * @return 合法返回true，否则返回false
	 */
	public static boolean validDate(String date,String patten){
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		try {
			sdf.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * 格林威治时间转换为自定义格式的时间
	 * @param gmt	
	 * @param patten
	 * @return    
	 * @return 	String
	 * @throws
	 */
	public static Date gmtToDate(String gmt){
		return new Date(Long.parseLong(gmt));
	}
	
	/**
	 * 格林威治时间转换为自定义格式的时间
	 * @param gmt	
	 * @param patten
	 * @return    
	 * @return 	String
	 * @throws
	 */
	public static String gmtToStr(String gmt,String patten){
		String res = null;
		if(StringUtils.isEmpty(gmt)){
			return res;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(patten);  
		res = sdf.format(gmtToDate(gmt));
		return res;
	}
	
	/**
	 * 转换格林威治时间
	 * @param gmt	
	 * @param patten
	 * @return    
	 * @return 	String
	 * @throws
	 */
	public static String strToGmt(String date,String patten){
		String res = null;
		if(StringUtils.isEmpty(date)){
			return res;
		}
		if(!validDate(date, patten)){
			return res;
		}
		Date parse = parse(date, patten);
		return String.valueOf(parse.getTime());
	}
	
	
	/**
	 * 返回两个string类型日期相差的小时数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long daysBetween(String startTime, String endTime){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Calendar cal = Calendar.getInstance();    
		long start = 0;
		long end = 0;
		try{
			cal.setTime(sdf.parse(startTime));
			start = cal.getTimeInMillis();    
			cal.setTime(sdf.parse(endTime));
			end = cal.getTimeInMillis();
		}catch(Exception e){
		 e.printStackTrace();
		}
		long between_days=(end-start)/(1000*3600);  
		return between_days;
	}
	
	public static void main(String[] args) {
		System.out.println(daysBetween("2017-05-20 14:58:14", "2017-05-22 14:58:14"));
	}
}
