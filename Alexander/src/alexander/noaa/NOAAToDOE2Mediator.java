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

import alexander.core.GenericWeatherData;
import alexander.core.WeatherCalendar;
import alexander.core.WeatherDataArray;
import alexander.core.WeatherDataType;
import alexander.core.WeatherFileMediator;
import alexander.doe2.DOE2File;

public class NOAAToDOE2Mediator implements WeatherFileMediator{
	
	private NOAAFile noaaFile;
	private DOE2File doe2File;
	
	public NOAAToDOE2Mediator(NOAAFile noaaFile, DOE2File doe2File){
		this.noaaFile = noaaFile;
		this.doe2File = doe2File;
	}

	@Override
	public void injectAirData() {
		WeatherDataArray drybulb = noaaFile.getHourly(WeatherDataType.DRYBULB);
		WeatherDataArray dewpoint = noaaFile.getHourly(WeatherDataType.DEWPOINT);
		WeatherDataArray pressure = noaaFile.getHourly(WeatherDataType.PRESSURE);
		WeatherDataArray wetbulb = new WeatherDataArray(WeatherDataType.WETBULB);
		WeatherDataArray humidityRatio = new WeatherDataArray(WeatherDataType.HUMIDITY_RATIO);
		WeatherDataArray enthalpy = new WeatherDataArray(WeatherDataType.ENTHALPY);
		WeatherDataArray density = new WeatherDataArray(WeatherDataType.DENSITY);
		
		WeatherCalendar calendar = new WeatherCalendar(noaaFile.year(),1,1,0);
		for(int i=0;i<drybulb.size();i++){
			wetbulb.add(new GenericWeatherData(wetbulb.get(i).getDouble(),calendar.clone()));
			humidityRatio.add(new GenericWeatherData(humidityRatio.get(i).getDouble(),calendar.clone()));
			enthalpy.add(new GenericWeatherData(enthalpy.get(i).getDouble(),calendar.clone()));
			density.add(new GenericWeatherData(density.get(i).getDouble(),calendar.clone()));
			calendar.incrementOneHour();
		}
		
		doe2File.set(drybulb);
		doe2File.set(pressure);
		doe2File.set(wetbulb);
		doe2File.set(humidityRatio);
		doe2File.set(enthalpy);
		doe2File.set(density);
		
	}

}
