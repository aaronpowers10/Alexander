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
import java.nio.ByteOrder;
import java.util.LinkedList;

import alexander.core.WeatherCalendar;
import alexander.core.WeatherDataArray;
import alexander.core.WeatherDataType;

public class DOE2Record {

	private DOE2Header headerData;
	private LinkedList<DOE2HourData> hourlyData;
	private int numDays;

	public DOE2Record(byte[] bytes,WeatherCalendar calendar) {
		if(calendar.day()==1){
			numDays = 16;
		} else {
			numDays = calendar.daysInMonth() - 16;
		}
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		
		read(buffer,calendar);
	}

	private void read(ByteBuffer buffer,WeatherCalendar calendar) {
		buffer.rewind();
		headerData = new DOE2Header(buffer);
		calendar.setYear(headerData.year());
		hourlyData = new LinkedList<DOE2HourData>();
		for(int i=0;i<numDays;i++){
			for(int j=0;j<24;j++){
				DOE2HourData hourData = new DOE2HourData(buffer.getInt(), buffer.getInt(), buffer.getInt(), buffer.getInt(),
						calendar);
				
				if(hourlyData.size()>0){
					hourData.setPrevious(hourlyData.getLast());
					hourlyData.getLast().setNext(hourData);
				}
				
				hourlyData.add(hourData);
				
			}
		}
	}
	
	public void write(ByteBuffer outputBuffer){
		//outputBuffer.put(new byte[4]);
		outputBuffer.putInt(6200);
		headerData.write(outputBuffer);	
		for(int i=0;i<hourlyData.size();i++){
			hourlyData.get(i).write(outputBuffer);
		}
		if(numDays != 16){
			int remainingBytes = (length()-8) - DOE2Header.length() - hourlyData.size()*DOE2HourData.length();
			byte[] dummyBytes = new byte[remainingBytes];
			outputBuffer.put(dummyBytes);
		}
		outputBuffer.putInt(6200);
		//outputBuffer.put(new byte[4]);
	}

	public DOE2Header getHeaderData() {
		return headerData;
	}

	public LinkedList<DOE2HourData> getHourData() {
		return hourlyData;
	}

	public int length() {
		return 6208;
	}
	
	public void print(){
		for(int i=0;i<hourlyData.size();i++){
			hourlyData.get(i).printState();
		}
	}
	
	public void set(WeatherDataArray data){
		for(int i=0;i<hourlyData.size();i++){
			hourlyData.get(i).set(data.get(hourlyData.get(i).hourOfYear()),data.type());
		}
	}
	
	public WeatherDataArray get(WeatherDataType type){
		WeatherDataArray weatherDataArray = new WeatherDataArray(type);
		for(int i=0;i<hourlyData.size();i++){
			weatherDataArray.add(hourlyData.get(i).get(type));
		}
		return weatherDataArray;
	}
	
	public int days(){
		return headerData.days();
	}

}
