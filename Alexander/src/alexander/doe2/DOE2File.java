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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import alexander.core.WeatherCalendar;
import alexander.core.WeatherDataArray;
import alexander.core.WeatherDataType;
import alexander.core.WeatherFile;

/**
 * @author Aaron Powers </p>
 * 
 * This class reads the DOE2 .bin weather format and generates an Annual Data object. </p>
 * 
 * The DOE2 binary weather format is comprised of 24 records of 6208 bytes each.  Therefore, each valid file
 * should show up as about 145.5 kB on a storage disk.  The bytes are arranged in Little-Endian format. Each
 * record roughly represents half a month of data.<p />
 * 
 * Each record contains a header which includes the following: <br/>
 * Location: As a 20 byte ASCII string <br/>
 * Year: As a 4 byte integer <br/>
 * Latitude: As a 4 byte float <br/>
 * Longitude: As a 4 byte float <br/>
 * Time Zone: As a 4 byte integer <br/>
 * Record Number: As a 4 byte integer <br/>
 * Number of Days in the Record: As a 4 byte integer <br/>
 * Clearness Number: As a 4 byte float <br/>
 * Ground Temperature: As a 4 byte float <br/>
 * Solar Flag: As a 4 byte integer <br/>
 * A sequence of 4*NumDays 31 bit words which encode the hourly data 
 * (the first bit in a 32 bit signed integer is used
 * to denote positive or negative). <p/>
 * 
 * The hourly data is encoded in two ways.  First, it is encrypted using a simple linear code.  
 * Second, it is compressed by combining multiple values into four individual
 * 31 bit words.  The order of the hourly data is as follows: <p/>
 * 
 * First 31 Bit Word: <br/>
 * Atmospheric Pressure: 15 bits <br/>
 * Wetbulb Temperature: 8 bits<br/>
 * Drybulb Temperature: 8 bits<p/>
 * 
 * Second 31 Bit Word: <br/>
 * Global Horizontal Radiation: 11 bits<br/>
 * Direct Normal Radiation: 10 bits <br/>
 * Cloud Amount: 4 bits <br/>
 * Snow Flag:  1 bit <br/>
 * Rain Flag: 1 bit <br/>
 * Wind Direction: 4 bits <p/>
 * 
 * Third 31 Bit Word: <br/>
 * Humidity Ratio: 24 bits <br/>
 * Air Density: 7 bits<p/>
 * 
 * Fourth 31 Bit Word: <br/>
 * Enthalpy: 20 bits <br/>
 * Cloud Type: 4 bits<br/>
 * Wind Speed: 7 bits<p/>
 * 
 * Before compression, each hourly data point is encoded according to:<br/>
 * X_encoded = b + m*X <br/>
 * where X is the un-encoded data and b and x are defined according to a code. <br/>
 * The code is as follows, in the order of the variables stated above:<br/>
 * b = {15,-99,-99,0,0,0,0.01,0.01,0.01,0,0.02,-30,0.01,0 }<br/>
 * m = {0.1,1,1,1,1,1,1,1,1,0.0001,0.001,0.5,1,1 }
 * 
 */

public class DOE2File implements WeatherFile{
	
	private ArrayList<DOE2Record> records;
	
	public DOE2File(String fileName){
		try {
			read(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void read(String fileName) throws IOException{	
		WeatherCalendar calendar = new WeatherCalendar(1985,1,1,0);
		records = new ArrayList<DOE2Record>();
		int numRecords = 24;	
		Path path = Paths.get(fileName);
	    byte[] bytes =  Files.readAllBytes(path);    
	    for(int i=0;i<numRecords;i++){
			byte[] recordBytes = Arrays.copyOfRange(bytes, i*6208 + 4, (i+1)*6208-4);
			DOE2Record record = new DOE2Record(recordBytes,calendar);
			records.add(record);
		}
	}
	
	@Override
	public void write(String fileName) throws IOException{
		ByteBuffer buffer = ByteBuffer.allocate(6208*24);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		for(int i=0;i<records.size();i++){
			records.get(i).write(buffer);
		}
		buffer.flip();
		FileOutputStream outputStream = new FileOutputStream(new File(fileName));
		FileChannel channel =outputStream.getChannel();
		while(buffer.hasRemaining()) {
		    channel.write(buffer);
		};
		channel.close();
		outputStream.close();
	}
	
	@Override
	public int year(){
		return records.get(0).getHeaderData().year();
	}
	
	public void print(){
		for(int i=0;i<records.size();i++){
			records.get(i).print();
		}
	}
	
	public void set(WeatherDataArray data){
		for(int i=0;i<records.size();i++){
			records.get(i).set(data);
		}
	}
	
	public WeatherDataArray get(WeatherDataType type){
		WeatherDataArray weatherDataArray = new WeatherDataArray(type);
		for(int i=0;i<records.size();i++){
			weatherDataArray.append(records.get(i).get(type));
		}
		return weatherDataArray;
	}
	
	public int numRecords(){
		return records.size();
	}
	
	public DOE2Record record(int index){
		return records.get(index);
	}

}
