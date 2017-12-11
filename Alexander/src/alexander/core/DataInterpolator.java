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

public class DataInterpolator {
	
	public void interpolate(WeatherDataArray data){
		//TODO
		//Handle case where first or last hour are missing
		DataSegment segment = new DataSegment();
		for(int i=0;i<data.size();i++){
			if(data.get(i).isMissing()){
				segment.add(data.get(i-1));
				segment.add(data.get(i));
				segment.add(data.get(i+1));
			} else if(segment.size()>1){
				interpolateSingleSegment(segment);
				segment.clear();
			}
		}
	}
	
	public void interpolateSingleSegment(DataSegment segment){
		double x1 = segment.get(0).minuteOfYear();
		double x2 = segment.get(segment.size()-1).minuteOfYear();
		double y1 = segment.get(0).getDouble();
		double y2 = segment.get(segment.size()-1).getDouble();
		for(int i=1;i<segment.size()-1;i++){
			double x = segment.get(i).minuteOfYear();
			segment.get(i).set((y2-y1)/(x2-x1)*(x-x1) + y1);
		}
	}
	
	class DataSegment{
		private ArrayList<WeatherObservation> data;
		
		public DataSegment(){
			data = new ArrayList<WeatherObservation>();
		}
		
		public int size(){
			return data.size();
		}
		
		public WeatherObservation get(int i){
			return data.get(i);
		}
		
		public void add(WeatherObservation item){
			if(!isInSegment(item)){
				data.add(item);
			}
		}
		
		private boolean isInSegment(WeatherObservation item){
			for(int i=0;i<data.size();i++){
				if(data.get(i).equals(item)){
					return true;
				}
			}
			return false;
		}
		
		public void clear(){
			data.clear();
		}
	}
	
	

}
