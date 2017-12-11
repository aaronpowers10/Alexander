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

public class GenericWeatherData implements WeatherObservation{
	
	private double value;
	private WeatherCalendar calendar;
	
	public GenericWeatherData(double value, WeatherCalendar calendar){
		this.value = value;
		this.calendar = calendar;
	}

	@Override
	public double getDouble() {
		return value;
	}

	@Override
	public int getInteger() {
		return (int)value;
	}

	@Override
	public void set(double value) {
		this.value = value;
		
	}

	@Override
	public void set(int value) {
		this.value = value;
		
	}

	@Override
	public boolean isMissing() {
		return false;
	}
	
	@Override
	public int year() {
		return calendar.month();
	}

	@Override
	public int month(){
		return calendar.month();
	}
	
	@Override
	public int day(){
		return calendar.day();
	}
	
	@Override
	public int hour(){
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

}
