/*
jWeather(TM) is a Java library for parsing raw weather data
Copyright (C) 2004 David Castro

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

For more information, please email arimus@users.sourceforge.net
*/
package net.sf.jweather;

import net.sf.jweather.metar.*;

public class Test {
	static String station = "KLAX";
	static Weather weather = null;
	static Metar metar = null;

	public static void main(String[] args) {
		if ((args.length > 0) && (args[0].length() == 4)) {
			station = args[0];
			metar = Weather.getMetar(station);
		} else if (args.length > 0) {
			System.out.println("please specify a valid station code (e.g. KCNO)");
		} else {
			metar = weather.getMetar(station, 5000);
		}

		if (metar != null) {
			metar.print();
		} else {
			System.out.println("could not retrieve station data for '"+station+"'");
		}
	}
}
