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

public class Interpolator implements DataCleaner{


	@Override
	public void clean(WeatherObservation observation) {
		double y1 = observation.previous().getDouble();
		double y2 = observation.next().getDouble();
		double x1 = observation.previous().minuteOfYear();
		double x2 = observation.next().minuteOfYear();
		double x = observation.minuteOfYear();
		
		observation.set((y2-y1)/(x2-x1)*(x-x1) + y1);
		
	}
}
