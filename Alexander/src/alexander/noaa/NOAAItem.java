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

public class NOAAItem {
	protected int value;
	private int startPosition;
	private int endPosition;
	
	public NOAAItem(String line, int startPosition, int endPosition){
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		read(line);
	}
	
	private void read(String line){
		value =Integer.parseInt(line.substring(startPosition,endPosition));
	}
	
	public int get(){
		return value;
	}
	
	public String outputString(){
		int length = endPosition - startPosition;
		return String.format("%"+length+"d", value);
	}
}
