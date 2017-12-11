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

public interface UnitConverter {

	public double convert(double value);
	
	public default double inverse(double value){
		double output = value;
		double residual =  value - convert(output);
		double residualInc;
		double dx = 0.0001;
		double slope;
		while(Math.abs(residual)>0.0000001){
			residual =  value - convert(output);
			residualInc = value - convert(output + dx);
			slope = (residualInc - residual)/dx;
			output = output - residual/slope;			
		}
		return output;
	}
	
}
