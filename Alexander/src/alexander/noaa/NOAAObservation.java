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

import alexander.core.UnitConverter;
import alexander.core.WeatherCalendar;
import alexander.core.WeatherObservation;

public class NOAAObservation extends NOAAItem implements WeatherObservation{
	
	private int scaleFactor;
	private int missingFlag;
	private UnitConverter converter;
	private WeatherCalendar calendar;
	private NOAAObservation previous;
	private NOAAObservation next;
	
	public NOAAObservation(String line, int startPosition, int endPosition, int scaleFactor, int missingFlag,
			WeatherCalendar calendar){
		super(line,startPosition,endPosition);
		this.scaleFactor = scaleFactor;
		this.missingFlag = missingFlag;
		converter = new NullConverter();
		this.calendar = calendar;
	}
	
	public NOAAObservation(String line, int startPosition, int endPosition, int scaleFactor,int missingFlag,
			UnitConverter converter, WeatherCalendar calendar){
		super(line,startPosition,endPosition);
		this.scaleFactor = scaleFactor;
		this.converter = converter;
		this.missingFlag = missingFlag;
		this.calendar = calendar;
	}
	
	@Override
	public double getDouble(){
		return converter.convert((double)value / (double)scaleFactor);
	}
	
	@Override
	public int getInteger(){
		return (int)(converter.convert((double)value / (double)scaleFactor));
	}
	
	@Override
	public void set(double value){
		this.value = (int)(converter.inverse(value) * scaleFactor);
	}
	
	@Override
	public void set(int value){
		this.value = (int)(converter.inverse(value) * scaleFactor);
	}
	
	class NullConverter implements UnitConverter{
		@Override
		public double convert(double value) {
			return value;
		}
	}

	@Override
	public boolean isMissing() {
		if(value == missingFlag){
			return true;
		} else {
			return false;
		}
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
	
	@Override
	public NOAAObservation previous(){
		return previous;
	}
	
	@Override
	public NOAAObservation next(){
		return next;
	}
	
	@Override
	public void setPrevious(WeatherObservation previous){
		this.previous = (NOAAObservation)previous;
	}
	
	@Override
	public void setNext(WeatherObservation next){
		this.next = (NOAAObservation)next;
	}

}
