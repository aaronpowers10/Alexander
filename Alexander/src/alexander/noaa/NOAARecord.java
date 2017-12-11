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

package alexander.noaa;

import alexander.core.CelsiusToFahrenheitConverter;
import alexander.core.HectoPascalToPSIConverter;
import alexander.core.TimeStamped;
import alexander.core.WeatherCalendar;
import alexander.core.WeatherDataType;
import alexander.core.WeatherObservation;

public class NOAARecord implements TimeStamped{
	
	private NOAAItem year;
	private NOAAItem month;
	private NOAAItem day;
	private NOAAItem hour;
	private NOAAItem minute;
	private NOAAObservation latitude;
	private NOAAObservation longitude;
	private NOAAObservation windDirection;
	private NOAAObservation windSpeed;
	private NOAAObservation drybulb;
	private NOAAObservation dewPoint;
	private NOAAObservation pressure;
	private WeatherCalendar calendar;
	
	public NOAARecord(String line){
		read(line);
	}
	
	private void read(String line){
		year = new NOAAItem(line,15,19);
		month = new NOAAItem(line,19,21);
		day = new NOAAItem(line,21,23);
		hour = new NOAAItem(line,23,25);
		minute = new NOAAItem(line,25,27);
		calendar = new WeatherCalendar(year.get(),month.get()-1,day.get(),hour.get(),minute.get());
		
		latitude = new NOAAObservation(line,28,34,1000,99999,calendar);
		longitude = new NOAAObservation(line,34,41,1000,999999,calendar);
		windDirection = new NOAAObservation(line,61,62,1,999,calendar);
		windSpeed = new NOAAObservation(line,65,69,10,9999,calendar);
		drybulb = new NOAAObservation(line,87,92,10, 9999,new CelsiusToFahrenheitConverter(),calendar);
		dewPoint = new NOAAObservation(line,93,98,10,9999,new CelsiusToFahrenheitConverter(),calendar);
		pressure = new NOAAObservation(line,99,104,10, 99999,new HectoPascalToPSIConverter(),calendar);
	}
	
	public String formattedString(){
		return String.format("%5d%3d%3d%3d%3d%7.2f%7.2f%7.1f%7.1f"
				+ "%7.1f%7.1f%10.3f", 
				year.get(),month.get(),day.get(),hour.get(),minute.get(),latitude.getDouble(),longitude.getDouble(),
				windDirection.getDouble(),windSpeed.getDouble(),drybulb.getDouble(),dewPoint.getDouble(),
				pressure.getDouble());
	}
	
	public WeatherObservation get(WeatherDataType type){
		if(type == WeatherDataType.WIND_DIRECTION){
			return windDirection;
		}else if(type == WeatherDataType.WIND_SPEED){
				return windSpeed;
		}else if(type == WeatherDataType.DRYBULB){
			return drybulb;
		} else if(type == WeatherDataType.DEWPOINT){
			return dewPoint;
		} else if(type == WeatherDataType.PRESSURE){
			return pressure;
		}
		return null;
	}
	
	public NOAAObservation drybulb(){
		return drybulb;
	}
	
	public NOAAObservation dewPoint(){
		return dewPoint;
	}
	
	public NOAAObservation pressure(){
		return pressure;
	}
	
	@Override
	public int year() {
		return calendar.month();
	}

	@Override
	public int month() {
		return calendar.month();
	}

	@Override
	public int day() {
		return calendar.day();
	}

	@Override
	public int hour() {
		return calendar.hour();
	}

	@Override
	public int minute() {
		return calendar.minute();
	}
	
	@Override
	public int hourOfYear(){
		return calendar.hourOfYear();
	}
	
	@Override
	public int minuteOfYear(){
		return calendar.minuteOfYear();
	}
	
	public void setPrevious(NOAARecord previous){
		windDirection.setPrevious(previous.get(WeatherDataType.WIND_DIRECTION));
		windSpeed.setPrevious(previous.get(WeatherDataType.WIND_SPEED));
		drybulb.setPrevious(previous.get(WeatherDataType.DRYBULB));
		dewPoint.setPrevious(previous.get(WeatherDataType.DEWPOINT));
		pressure.setPrevious(previous.get(WeatherDataType.PRESSURE));
	}
	
	public void setNext(NOAARecord next){
		windDirection.setNext(next.get(WeatherDataType.WIND_DIRECTION));
		windSpeed.setNext(next.get(WeatherDataType.WIND_SPEED));
		drybulb.setNext(next.get(WeatherDataType.DRYBULB));
		dewPoint.setNext(next.get(WeatherDataType.DEWPOINT));
		pressure.setNext(next.get(WeatherDataType.PRESSURE));
	}

}
