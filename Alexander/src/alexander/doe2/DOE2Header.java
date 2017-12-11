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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class DOE2Header {
	
	private String location;
	private int year;
	private double latitude;
	private double longitude;
	private int timeZone;
	private int solarFlag;
	private double clearness;
	private double groundTemperature;
	private int days;
	private int recordNumber;
	
	public DOE2Header(ByteBuffer buffer){
		read(buffer);
	}
	
	private void read(ByteBuffer buffer){
		buffer.rewind();
		byte[] b = new byte[20];
		for (int i = 0; i < 20; i++) {
			b[i] = buffer.get();
		}
		try {
			location = new String(b, "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		year = buffer.getInt();
		latitude = buffer.getFloat();
		longitude = buffer.getFloat();
		timeZone = buffer.getInt();
		recordNumber = buffer.getInt();
		days = buffer.getInt();
		clearness = buffer.getFloat();
		groundTemperature = buffer.getFloat();
		solarFlag = buffer.getInt();

		
	}
	
	public void write(ByteBuffer buffer){
		byte[] trimmedLocationBytes = location.getBytes();
		for(int i=0;i<20;i++){
			if(i<trimmedLocationBytes.length){
				buffer.put(trimmedLocationBytes[i]);
			} else {
				buffer.put(" ".getBytes());
			}
		}
		buffer.putInt(year);
		buffer.putFloat((float)latitude);
		buffer.putFloat((float)longitude);
		buffer.putInt(timeZone);
		buffer.putInt(recordNumber);
		buffer.putInt(days);
		buffer.putFloat((float)clearness);
		buffer.putFloat((float)groundTemperature);
		buffer.putInt(solarFlag);

	}
	
	/**
	 * Returns the length of the header in bytes.
	 * @return
	 */
	public static int length(){
		return 20+4*9;
	}
	
	public String getLocation(){
		return location;
	}
	
	public int year(){
		return year;
	}
	
	public double latitude(){
		return latitude;
	}
	
	public double longitude(){
		return longitude;
	}
	
	public int timeZone(){
		return timeZone;
	}
	
	public int solarFlag(){
		return solarFlag;
	}
	
	public double clearness(){
		return this.clearness;
	}
	
	public double groundTemperature(){
		return this.groundTemperature;
	}
	
	public int days(){
		return days;
	}
	
	public int recordNumber(){
		return this.recordNumber;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	
	public void setTimeZone(int timeZone){
		this.timeZone = timeZone;
	}
	
	public void setSolarFlag(int solarFlag){
		this.solarFlag = solarFlag;
	}
	
	public void setClearness(double clearness){
		this.clearness = clearness;
	}
	
	public void setGroundTemperature(double groundTemperature){
		this.groundTemperature = groundTemperature;
	}
	
	public void setDays(int days){
		this.days = days;
	}
	
	public void setRecordNumber(int recordNumber){
		this.recordNumber = recordNumber;
	}

}
