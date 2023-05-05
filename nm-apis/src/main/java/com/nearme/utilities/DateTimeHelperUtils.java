package com.nearme.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.springframework.format.annotation.DateTimeFormat;

public final class DateTimeHelperUtils {
	
	
	public static String convertDateToString(Date date) {

		SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String formattedDate = currentDateFormat.format(date.getTime());
		return formattedDate;
	}
	
	public static String formatDate(Date date) {
		SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = currentDateFormat.format(date.getTime());
		return formattedDate;
	}
	
	public static String formatDateWithTime(Date date) {
		SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String formattedDate = currentDateFormat.format(date.getTime());
		return formattedDate;
	}

	public static String convertDateToString2(Date date) {

		SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = currentDateFormat.format(date.getTime());
		return formattedDate;
	}
	
	
	public static String dateToStringYYYYMMdd(Date date) {
		SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = currentDateFormat.format(date.getTime());
		return formattedDate;
	}
	
	
	public static Date addDaysToDate(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 12);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static Date addDaysToDate2(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 11);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static Date addDaysToDate3(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	
	
	
	
	public static Date addDaysToCurrentDate(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	
	
	
	public static Date addMinutesToDate(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.MINUTE, days);
		
		//cal.set(Calendar.HOUR_OF_DAY, 0);
	    //cal.set(Calendar.MINUTE, days);
	  //  cal.set(Calendar.SECOND, 0);
		//cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	
	public static Date addHoursToDate(Date date,int hours) {
		
		DateTime dateTime = new DateTime(date).withMillisOfSecond(0);
		DateTime newDateTime=	dateTime.plusHours(hours);
		return newDateTime.toDate();
	}
	
	
	
	//public static Date addHoursToDateUTC(Date date,int hours) {
		
		
	//	DateTime dateTime = new DateTime(date).withMillisOfSecond(0).withZone(DateTimeZone.UTC);
		
	//	DateTime newDateTime=	dateTime.plusHours(hours);
		
	//	String pattern = "yyyy-MM-dd HH:mm:ss";
	//	DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
	//	String formatted = formatter.print(newDateTime);
		//System.out.println(formatted+"Z");
		
	//	return newDateTime.toDate();
	//}
	
	

	public static Date convertStringToDate(String strDate) {
		SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date formattedDate=null;
		try {
			formattedDate = currentDateFormat.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	public static Date convertStringToDate2(String strDate) {
		SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date formattedDate=null;
		try {
			formattedDate = currentDateFormat.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	public static String convertStringToDateYYYYMMdd(String strDate,int addDays) {

		SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date formattedDate=null;
		try {
			formattedDate = currentDateFormat.parse(strDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(formattedDate);
			cal.add(Calendar.DATE, addDays);
			return dateToStringYYYYMMdd(cal.getTime());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	public static Long convertStringToSecodns(String strDate) { // 23:07:07
		Long duration=0L,hour=0L,minutes=0L,seconds=0L,minutesSec=0L,hourSec=0L;
		try {
			
			String [] str=strDate.split(":");
			
			String hourStr=str[0];
			String minutesStr=str[1];
			String secondsStr=str[2];
			
			if(!"00".equalsIgnoreCase(hourStr)) {
				hour=Long.parseLong(hourStr);
				hourSec=hour*60*60;
				
			}
			if(!"00".equalsIgnoreCase(minutesStr)) {
				minutes=Long.parseLong(minutesStr);
				minutesSec=minutes*60;
			}
			if(!"00".equalsIgnoreCase(secondsStr)) {
				seconds=Long.parseLong(secondsStr);
			}
			
			duration=seconds+minutesSec+hourSec;
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return duration;
	}
	
	
	public static int getDaysInCurrentMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int monthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return monthMaxDays;

	}
	
	public static String getMonthName(Date d) {
		String monthName="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		monthName=new SimpleDateFormat("MMM").format(cal.getTime());
		return monthName;
		
	}
	
	
	public static String getDateNumber(Date d) {
		String monthName="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		monthName=new SimpleDateFormat("dd").format(cal.getTime());
		return monthName;
		
	}
	
	public static String getDayName(Date d) {
		String monthName="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		monthName=new SimpleDateFormat("EEE").format(cal.getTime());
		return monthName;
		
	}
	
	
	public static String monthNumber() {
		Date date = new Date();
		java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month= localDate.getMonthValue();
		if(month<=9) {
			return "0"+month;
		}else {
			return ""+month;
		}
	
	}
	
	public static String yearNumber() {
		Date date = new Date();
		java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String year= ""+localDate.getYear();
		return  year.substring(2);
	}
	 
	public static List<String> getStartEndMonthDate(int monthNumber,int year)
	{
		List<String> monthDateList=new ArrayList<String>();
		String month=null;
		int endDay;
		if(monthNumber<10){
			 month="0"+monthNumber;
		}
		else{
			month=""+monthNumber;
		}
		String startDate=year+"-"+month+"-01 00:00:00";
		Date d=convertStringToDate(startDate);
		endDay=getDaysInCurrentMonth(d);
		String endDate=year+"-"+month+"-"+endDay+" 23:59:59";
		monthDateList.add(startDate);
		monthDateList.add(endDate);
		return monthDateList;
	}
	
	public static List<String> getStartEndYearDate(int year)
	{
		List<String> yearDateList=new ArrayList<String>();
		int endDay;
		
		String startDate=year+"-01-01 00:00:00";
		Date d=convertStringToDate(startDate);
		endDay=getDaysInCurrentMonth(d);
		String endDate=year+"-12-31 23:59:59";
		yearDateList.add(startDate);
		yearDateList.add(endDate);
		return yearDateList;
	}
	
	private Date getFormattedFromDateTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    return cal.getTime();
	}
	 
	private Date getFormattedToDateTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 23);
	    cal.set(Calendar.MINUTE, 59);
	    cal.set(Calendar.SECOND, 59);
	    return cal.getTime();
	}
	
	
	
	public static int calculateDaysDifferenceForRefund(Date checkInDate,Date currentDate,int minhours) {
		int days=0;
		try {
			
			DateTime dt1 = new DateTime(currentDate);
			DateTime dt2 = new DateTime(checkInDate);
			///DateTime dtCheckOut = new DateTime(checkOutDate);
			int hoursDiff=	 Hours.hoursBetween(dt1, dt2).getHours();
			
			//int hoursDiff2= Hours.hoursBetween(dt1, dtCheckOut).getHours();
			//System.out.println("hoursDiff2:"+hoursDiff2);
		//	System.out.println("111hoursDiff: "+hoursDiff);
			hoursDiff=minhours-hoursDiff;
			if(hoursDiff<24 && hoursDiff>0) {
				days=1;
			}else {
				 days=hoursDiff/24;
			}
			// System.out.println("222hoursDiff: "+hoursDiff);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return days;
	}
	
	
	
	public static Date calculateExpiryDate(Date checkInDate,Date currentDate,int minhours) {
		 Date expiryDate=null;
		try {
			
			DateTime dt1 = new DateTime(currentDate);
			DateTime dt2 = new DateTime(checkInDate);
			///DateTime dtCheckOut = new DateTime(checkOutDate);
			int hoursDiff=	 Hours.hoursBetween(dt1, dt2).getHours();
			
			if(hoursDiff>=minhours) {
				expiryDate=addMinutesToDate(new Date(), minhours);
			}else {
				expiryDate=checkInDate;
			}
			
			// System.out.println("222hoursDiff: "+hoursDiff);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return expiryDate;
	}
	
	public static Date addDaysToDate(Date date,int hour,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	public static int calculateDaysDifference(String startDate,String endDate) {
		int days=0;
		try {
			Date checkInDate=convertStringToDate(startDate);
			Date checkOutDate=convertStringToDate(endDate);
			DateTime dt1 = new DateTime(checkInDate);
			DateTime dt2 = new DateTime(checkOutDate);
			///DateTime dtCheckOut = new DateTime(checkOutDate);
			int hoursDiff=	 Hours.hoursBetween(dt1, dt2).getHours();
			
			//int hoursDiff2= Hours.hoursBetween(dt1, dtCheckOut).getHours();
			//System.out.println("hoursDiff2:"+hoursDiff2);
		//	System.out.println("111hoursDiff: "+hoursDiff);
			
				 days=hoursDiff/24;
			
			//System.out.println("hoursDiff : "+hoursDiff);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return days+1;
	}
	
	
	public  static  boolean validateCheckInOutDates(Date checkInDate,Date checkOutDate) {
		boolean isvalid=true;
		try {
			
			if(checkInDate==null || checkOutDate==null) {
				isvalid=false;
			}else {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date cuDate = dateFormat.parse(dateFormat.format(new Date()));
				
				Calendar currentcal = Calendar.getInstance();
				currentcal.setTime(cuDate);
				
				Calendar currentcalLastDay = Calendar.getInstance();
				currentcalLastDay.setTime(cuDate);
				currentcalLastDay.add(Calendar.DATE, -1);
				
				Calendar checkInCal = Calendar.getInstance();
				Date formattedCheckinDate = dateFormat.parse(dateFormat.format(checkInDate));
				checkInCal.setTime(formattedCheckinDate);
				//checkInCal.add(Calendar.DATE, -1);
				
				Calendar checkOutCal = Calendar.getInstance();
				Date formattedCheckOutDate = dateFormat.parse(dateFormat.format(checkOutDate));
				checkOutCal.setTime(formattedCheckOutDate);
				
				if(checkInCal.equals(checkOutCal)) {
					isvalid=false;
				}
				
				if (checkInCal.before(currentcal))
					isvalid=false;
				
				if (checkOutCal.before(currentcal))
					isvalid=false;
				
				/*if(checkInCal.equals(currentcalLastDay)) {
					isvalid=true;
				}
				*/
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isvalid;
	}
	
	
	
	public  static  boolean validateCheckInOutDatesBedFolio(Date checkInDate,Date checkOutDate) {
		boolean isvalid=true;
		try {
			
			
			if(checkInDate==null || checkOutDate==null) {
				isvalid=false;
			}else {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			/*	Date cuDate = dateFormat.parse(dateFormat.format(new Date()));
				
				
				Calendar currentcal = Calendar.getInstance();
				currentcal.setTime(cuDate);*/
		
				
				Calendar checkInCal = Calendar.getInstance();
				Date formattedCheckinDate = dateFormat.parse(dateFormat.format(checkInDate));
				checkInCal.setTime(formattedCheckinDate);
				//checkInCal.add(Calendar.DATE, -1);
				
				Calendar checkOutCal = Calendar.getInstance();
				Date formattedCheckOutDate = dateFormat.parse(dateFormat.format(checkOutDate));
				checkOutCal.setTime(formattedCheckOutDate);
				
				if(checkInCal.equals(checkOutCal)) {
					isvalid=false;
				}
				
				if (checkInCal.after(checkOutCal))
					isvalid=false;
				
				if (checkInCal.before(checkOutCal))
					isvalid=true;
				
				/*if(checkInCal.equals(currentcalLastDay)) {
					isvalid=true;
				}
				*/
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isvalid;
	}
	
	
	
	
	
	
	public static int getCurrentHour() {
		DateTime dt = new DateTime(); 
		return dt.getHourOfDay(); 
	}
	
	
	public static void main(String [] ar) {
		/*
		String strCheckInDate="2019-12-01 12:00:00";
		System.out.println(strCheckInDate.substring(0, 11));
	
	
		System.out.println(addHoursToDateUTC(checkInDate,72));*/
		

		
		/*	/*String strcurrentDate="2019-10-05 11:00:0";
		//Date currentDate=addDaysToCurrentDate(new Date(), -1);
		
		Date checkInDate=convertStringToDate(strCheckInDate);
		Date currentDate=convertStringToDate(strcurrentDate);
		Date checkOutDate=convertStringToDate(strCheckOutDate);
		currentDate=addDaysToCurrentDate(new Date(), 0);
		
	int days=	calculateDaysDifferenceForRefund(currentDate,checkOutDate,72);
//	int days=	calculateDaysDifferenceForRefund(checkOutDate,currentDate,72);
 * 
 * 
 * 
*//*	
		String strCheckInDate="2019-09-23 12:00:00";
		String strCheckOutDate="2019-09-30 12:00:00";
		String strcurrentDate="2019-10-05 11:00:0";
		//Date currentDate=addDaysToCurrentDate(new Date(), -1);
		
		Date checkInDate=convertStringToDate(strCheckInDate);
		Date currentDate=convertStringToDate(strcurrentDate);
		Date checkOutDate=convertStringToDate(strCheckOutDate);
		currentDate=addDaysToCurrentDate(new Date(), -1);
		System.out.println("currentDate: "+currentDate +" IntimiDate: "+ checkInDate);
		int days=	calculateDaysDifferenceForRefund(currentDate,checkInDate,72);
		System.out.println("Days: "+days);*/
		
	}

}
