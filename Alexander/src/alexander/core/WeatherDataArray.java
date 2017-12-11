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

import java.util.ArrayList;

public class WeatherDataArray {
	
	private ArrayList<WeatherObservation> dataArray;
	private WeatherDataType type;
	
	public WeatherDataArray(WeatherDataType type){
		this.type = type;
		dataArray = new ArrayList<WeatherObservation>();
	}
	
	public WeatherDataArray(WeatherDataType type, ArrayList<WeatherObservation> dataArray){
		this.type = type;
		this.dataArray = dataArray;
	}
	
	public void add(WeatherObservation data){
		dataArray.add(data);
	}
	
	public WeatherObservation get(int index){
		return dataArray.get(index);
	}
	
	public WeatherDataType type(){
		return type;
	}
	
	public int size(){
		return dataArray.size();
	}
	
	public void append(WeatherDataArray array2){
		for(int i=0;i<array2.size();i++){
			dataArray.add(array2.get(i));
		}
	}
	
	public WeatherDataArray subArray(int fromIndex, int toIndex){
		WeatherDataArray subArray = new WeatherDataArray(type(),(ArrayList<WeatherObservation>)dataArray.subList(fromIndex, toIndex));
		return subArray;
	}
	

}
