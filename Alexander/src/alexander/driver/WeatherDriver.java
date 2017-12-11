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

package alexander.driver;

import java.io.IOException;

import alexander.core.Interpolator;
import alexander.core.WeatherDataType;
import alexander.doe2.DOE2File;
import alexander.noaa.NOAAFile;
import alexander.noaa.NOAAToDOE2Mediator;

public class WeatherDriver {
	
	public static void main(String[] args) throws IOException{
		test1();
    }
	
	private static void test1() throws IOException{
		System.out.println("Running Test 1");
		System.out.println("This tests the read/write features by reading a DOE2 bin file and writing to a new file name.");
		System.out.println("The contents of the file should also be printed");
		DOE2File file  = new DOE2File("TN_Memphis_International.bin");
		file.write("TN_Memphis_International_output.bin");
		file.print();
		System.out.println("Finished Test 1");
	}
	
	private static void test2(){
		System.out.println("Running Test 2.");
		System.out.println("This test reads a NOAA file and prints results to console");
		NOAAFile file = new NOAAFile("Memphis_NOAA");
		file.print();
		System.out.println("Finished Test 2.");
	}
	
	private static void test3() throws IOException{
		System.out.println("Running Test 3.");
		System.out.println("This test takes a NOAA AMY file, extracts air data, combines with data from TMY3 file, and saves to DOE2 .bin format");
		NOAAFile noaaFile = new NOAAFile("Memphis_NOAA");
		noaaFile.clean(WeatherDataType.DRYBULB, new Interpolator());
		noaaFile.clean(WeatherDataType.DEWPOINT, new Interpolator());
		noaaFile.clean(WeatherDataType.PRESSURE, new Interpolator());
		noaaFile.print();
		DOE2File doe2File = new DOE2File("TN_Memphis_International.bin");
		NOAAToDOE2Mediator mediator = new NOAAToDOE2Mediator(noaaFile,doe2File);
		mediator.injectAirData();
		doe2File.write("Memphis_AMY_Combined_Output.bin");
		DOE2File test = new DOE2File("Memphis_AMY_Combined_Output.bin");
		test.print();
		System.out.println("Finished Test 3.");
	}

}
