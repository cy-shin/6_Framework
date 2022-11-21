package edu.kh.project.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) throws ParseException {
		
		// Date : 날짜용 객체
		
		// Calender : Date 업그레이드 객체
		
		// SimpleDateFormat : 날짜를 원하는 형태의 문자열로 변환
		
		Date a = new Date(); // 현재 시간
		Date b = new Date(1669125257439L); 
		
		// new Date(0)  자바 기준 시간(1970.01.01 09:00:00)
		// new Date(ms) 기준 시간 + ms만큼 지난 시간
		
		Calendar cal = Calendar.getInstance();
		// cal.add(단위, 추가할 값);
		
		cal.add(cal.DATE, 1); // 날짜에 1 추가해서 내일 값을 얻어냄
		
		System.out.println(a);
		System.out.println(b);
		
		System.out.println(cal);
		
		// simpleDateFormat을 이용해서 cal 날짜 중 시, 분, 초를 0:0:0으로 바꿈
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date temp = new Date( cal.getTimeInMillis() );
		
		// TimeInMillis() : ms값을 얻어냄
		Date c = sdf.parse(sdf.format(temp));
		// parse : 여기서는 날자 형식 String을 Date로 변환
		System.out.println(sdf.format(temp));
		System.out.println(c);
		
		// 내일 자정 시간 - 현재 시간 = 
		long diff = c.getTime() - a.getTime();
		System.out.println(diff); // 자정 - 현재
		
		System.out.println(diff / 1000 - 1); // 자정 - 1초 - 현재시각
	}
}
