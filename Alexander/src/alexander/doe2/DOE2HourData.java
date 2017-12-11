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

package alexander.doe2;

import java.nio.ByteBuffer;

import alexander.core.TimeStamped;
import alexander.core.WeatherCalendar;
import alexander.core.WeatherDataType;
import alexander.core.WeatherObservation;

public class DOE2HourData implements TimeStamped {
	private DOE2Observation drybulb;
	private DOE2Observation wetbulb;
	private DOE2Observation pressure;
	private DOE2Observation cloudAmount;
	private DOE2Observation snowFlag;
	private DOE2Observation rainFlag;
	private DOE2Observation windDirection;
	private DOE2Observation humidityRatio;
	private DOE2Observation density;
	private DOE2Observation enthalpy;
	private DOE2Observation globalHorizontal;
	private DOE2Observation directNormal;
	private DOE2Observation cloudType;
	private DOE2Observation windSpeed;
	private WeatherCalendar calendar;
	
	public DOE2HourData(int word1, int word2, int word3, int word4, WeatherCalendar calendar){
		this.calendar = calendar.clone();
		calendar.incrementOneHour();
		read(word1,word2,word3,word4);
	}
	
	private void read(int word1, int word2, int word3, int word4){

		drybulb = new DOE2Observation(word1,0,8,-99,1,calendar);
		wetbulb = new DOE2Observation(word1,8,8,-99,1,calendar);
		pressure = new DOE2Observation(word1,16,15,15,0.1,calendar);
		
		windDirection = new DOE2Observation(word2,0,4,0.01,1,calendar);
		rainFlag = new DOE2Observation(word2,4,1,0.01,1,calendar);
		snowFlag = new DOE2Observation(word2,5,1,0.01,1,calendar);
		cloudAmount = new DOE2Observation(word2,6,4,0,1,calendar);
		directNormal = new DOE2Observation(word2,10,10,0,1,calendar);
		globalHorizontal = new DOE2Observation(word2,20,11,0,1,calendar);
		
		density = new DOE2Observation(word3,0,7,0.02,0.001,calendar);
		humidityRatio = new DOE2Observation(word3,7,24,0,0.0001,calendar);
		
		windSpeed = new DOE2Observation(word4,0,7,0,1,calendar);
		cloudType = new DOE2Observation(word4,7,4,0.01,1,calendar);
		enthalpy = new DOE2Observation(word4,11,20,-30,0.5,calendar);
		
	}
	
	public void write(ByteBuffer buffer){
		BinaryString string1 = drybulb.toBits();
		string1.append(wetbulb.toBits());
		string1.append(pressure.toBits());
		int word1 = string1.toDecimal();
		
		BinaryString string2 = windDirection.toBits();
		string2.append(rainFlag.toBits());
		string2.append(snowFlag.toBits());
		string2.append(cloudAmount.toBits());
		string2.append(directNormal.toBits());
		string2.append(globalHorizontal.toBits());
		int word2 = string2.toDecimal();
		
		BinaryString string3 = density.toBits();
		string3.append(humidityRatio.toBits());
		int word3 = string3.toDecimal();
		
		BinaryString string4 = windSpeed.toBits();
		string4.append(cloudType.toBits());
		string4.append(enthalpy.toBits());
		int word4 = string4.toDecimal();
		
		buffer.putInt(word1);
		buffer.putInt(word2);
		buffer.putInt(word3);
		buffer.putInt(word4);
		
	}
	
	/**
	 * Returns the length in bytes.	
	 * @return
	 */
	public static int length(){
		return 4*4;
	}
	
	public String formattedString(){
		return String.format("%4d%4d%4d%5.0f%5.0f%6.1f%5.0f"
				+ "%3d%3d%4d%7.4f%6.3f%6.1f%7.1f%7.1f%3d%5.0f", 
				month(),day(),hour(),wetbulb.getDouble(),drybulb.getDouble(),pressure.getDouble(),
				cloudAmount.getDouble(),snowFlag.getInteger(),rainFlag.getInteger(),
				windDirection.getInteger(),humidityRatio.getDouble(),density.getDouble(),
				enthalpy.getDouble(),globalHorizontal.getDouble(),directNormal.getDouble(),
				cloudType.getInteger(),windSpeed.getDouble());
	}
	
	public void printState(){
		System.out.println(formattedString());
	}
	
	public void set(WeatherObservation data,WeatherDataType type){
		
		if(type == WeatherDataType.DRYBULB){
			drybulb.set(data.getDouble());
		} else if(type == WeatherDataType.PRESSURE){
			pressure.set(data.getDouble());
		} else if(type == WeatherDataType.CLOUD_AMOUNT){
			cloudAmount.set(data.getDouble());
		} else if(type == WeatherDataType.SNOW_FLAG){
			snowFlag.set(data.getDouble());
		} else if(type == WeatherDataType.RAIN_FLAG){
			rainFlag.set(data.getDouble());
		} else if(type == WeatherDataType.WIND_DIRECTION){
			windDirection.set(data.getDouble());
		} else if(type == WeatherDataType.HUMIDITY_RATIO){
			humidityRatio.set(data.getDouble());
		} else if(type == WeatherDataType.DENSITY){
			density.set(data.getDouble());
		} else if(type == WeatherDataType.ENTHALPY){
			enthalpy.set(data.getDouble());
		} else if(type == WeatherDataType.GLOBAL_HORIZONTAL){
			globalHorizontal.set(data.getDouble());
		} else if(type == WeatherDataType.DIRECT_NORMAL){
			directNormal.set(data.getDouble());
		} else if(type == WeatherDataType.CLOUD_TYPE){
			cloudType.set(data.getDouble());
		} else if(type == WeatherDataType.WIND_SPEED){
			windSpeed.set(data.getDouble());
		}
	}
	
	public WeatherObservation get(WeatherDataType type){		
		
		if(type == WeatherDataType.DRYBULB){
			return drybulb;
		} else if(type == WeatherDataType.PRESSURE){
			return pressure;
		} else if(type == WeatherDataType.CLOUD_AMOUNT){
			return cloudAmount;
		} else if(type == WeatherDataType.SNOW_FLAG){
			return snowFlag;
		} else if(type == WeatherDataType.RAIN_FLAG){
			return rainFlag;
		} else if(type == WeatherDataType.WIND_DIRECTION){
			return windDirection;
		} else if(type == WeatherDataType.HUMIDITY_RATIO){
			return humidityRatio;
		} else if(type == WeatherDataType.DENSITY){
			return density;
		} else if(type == WeatherDataType.ENTHALPY){
			return enthalpy;
		} else if(type == WeatherDataType.GLOBAL_HORIZONTAL){
			return globalHorizontal;
		} else if(type == WeatherDataType.DIRECT_NORMAL){
			return directNormal;
		} else if(type == WeatherDataType.CLOUD_TYPE){
			return cloudType;
		} else if(type == WeatherDataType.WIND_SPEED){
			return windSpeed;
		} else if (type == WeatherDataType.WETBULB){
			return wetbulb;
		}
		return null;
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
	
	public void setPrevious(DOE2HourData previous){
		drybulb.setPrevious(previous.get(WeatherDataType.DRYBULB));
		pressure.setPrevious(previous.get(WeatherDataType.PRESSURE));
		cloudAmount.setPrevious(previous.get(WeatherDataType.CLOUD_AMOUNT));
		snowFlag.setPrevious(previous.get(WeatherDataType.SNOW_FLAG));
		rainFlag.setPrevious(previous.get(WeatherDataType.RAIN_FLAG));
		windDirection.setPrevious(previous.get(WeatherDataType.WIND_DIRECTION));
		humidityRatio.setPrevious(previous.get(WeatherDataType.HUMIDITY_RATIO));
		density.setPrevious(previous.get(WeatherDataType.DENSITY));
		enthalpy.setPrevious(previous.get(WeatherDataType.ENTHALPY));
		globalHorizontal.setPrevious(previous.get(WeatherDataType.GLOBAL_HORIZONTAL));
		directNormal.setPrevious(previous.get(WeatherDataType.DIRECT_NORMAL));
		cloudType.setPrevious(previous.get(WeatherDataType.CLOUD_TYPE));
		windSpeed.setPrevious(previous.get(WeatherDataType.WIND_SPEED));
	}
	
	public void setNext(DOE2HourData next){
		drybulb.setNext(next.get(WeatherDataType.DRYBULB));
		pressure.setNext(next.get(WeatherDataType.PRESSURE));
		cloudAmount.setNext(next.get(WeatherDataType.CLOUD_AMOUNT));
		snowFlag.setNext(next.get(WeatherDataType.SNOW_FLAG));
		rainFlag.setNext(next.get(WeatherDataType.RAIN_FLAG));
		windDirection.setNext(next.get(WeatherDataType.WIND_DIRECTION));
		humidityRatio.setNext(next.get(WeatherDataType.HUMIDITY_RATIO));
		density.setNext(next.get(WeatherDataType.DENSITY));
		enthalpy.setNext(next.get(WeatherDataType.ENTHALPY));
		globalHorizontal.setNext(next.get(WeatherDataType.GLOBAL_HORIZONTAL));
		directNormal.setNext(next.get(WeatherDataType.DIRECT_NORMAL));
		cloudType.setNext(next.get(WeatherDataType.CLOUD_TYPE));
		windSpeed.setNext(next.get(WeatherDataType.WIND_SPEED));
	}
}
