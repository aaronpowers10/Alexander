/*
 *
 *  Copyright (C) 2017 Aaron Powers
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package alexander.core;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeatherCalendar {
	
	private GregorianCalendar calendar;
	
	public WeatherCalendar(int year, int month, int day, int hour, int minute){
		calendar = new GregorianCalendar(year,month-1,day,hour,minute);
	}
	
	public WeatherCalendar(int year, int month, int day, int hour){
		calendar = new GregorianCalendar(year,month-1,day,hour,30);
	}
	
	public int year(){
		return calendar.get(Calendar.YEAR);
	}
	
	public int month(){
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public int day(){
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public int hour(){
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public int minute() {
		return calendar.get(Calendar.MINUTE);
	}
	
	public int hourOfYear(){
		return (calendar.get(Calendar.DAY_OF_YEAR)-1)*24 + calendar.get(Calendar.HOUR_OF_DAY) + 1;
	}
	
	public int minuteOfYear(){
		return (calendar.get(Calendar.DAY_OF_YEAR)-1)*24*60 + calendar.get(Calendar.HOUR_OF_DAY)*60 + 
				calendar.get(Calendar.MINUTE)+ 1;
	}
	
	public int daysInMonth(){
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	@Override
	public WeatherCalendar clone(){
		return new WeatherCalendar(year(),month()-1,day(),hour(),minute());
	}
	
	public void incrementOneHour(){
		calendar.add(Calendar.HOUR, 1);
	}
	
	public void setYear(int year){
		calendar.set(Calendar.YEAR, year);
	}
	
	public static void main(String[] args){
		WeatherCalendar calendar = new WeatherCalendar(1985,1,1,0,0);
		System.out.println(calendar.hourOfYear());
		System.out.println(calendar.hour());
		System.out.println(calendar.minuteOfYear());
		WeatherCalendar calendar2 = new WeatherCalendar(1985,12,31,23,59);
		System.out.println(calendar2.hourOfYear());
		System.out.println(calendar2.hour());
		System.out.println(calendar2.minuteOfYear());
	}

}
