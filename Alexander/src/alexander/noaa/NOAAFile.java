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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import alexander.core.WeatherDataArray;
import alexander.core.WeatherDataType;
import alexander.core.WeatherFile;

public class NOAAFile implements WeatherFile {
	private LinkedList<NOAARecord> records;
	
	public NOAAFile(String fileName){
		try {
			read(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void read(String fileName) throws FileNotFoundException{
		records = new LinkedList<NOAARecord>();
		Scanner in = new Scanner(new File(fileName));
		String line = "";
		while(in.hasNextLine()){
			line = in.nextLine();
			NOAARecord record = new NOAARecord(line);
			
			if(records.size()>0){
				record.setPrevious(records.getLast());
				records.getLast().setNext(record);
			}
			
			records.add(record);
		}
		in.close();
	}
	
	@Override
	public void write(String fileName) throws FileNotFoundException{
		
	}
	
	@Override
	public int year(){
		return records.get(0).year();
	}
	
	public void print(){
		for(int i=0;i<records.size();i++){
			System.out.println(records.get(i).formattedString());
		}
	}
	
	public WeatherDataArray get(WeatherDataType type){
		WeatherDataArray weatherDataArray = new WeatherDataArray(type);
		for(int i=0;i<records.size();i++){
			weatherDataArray.add(records.get(i).get(type));
		}
		return weatherDataArray;
	}
	
	public WeatherDataArray getHourly(WeatherDataType type){
		WeatherDataArray weatherDataArray = new WeatherDataArray(type);
		int hourOfYear = 0;
		for(int i=0;i<records.size();i++){
			if(records.get(i).hourOfYear()==hourOfYear){
				weatherDataArray.add(records.get(i).get(type));
				hourOfYear++;
			}
		}
		return weatherDataArray;
	}

}
