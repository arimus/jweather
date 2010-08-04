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

import java.io.*;
import java.net.*;
import org.apache.log4j.Logger;
import net.sf.jweather.metar.*;

/**
 * Responsible for downloading the METAR reports and feeding them to the Metar
 * class to be parsed
 *
 * <p>
 * <code>
 * Metar metar = Weather.getMetar("KLAX");<br>
 * metar.print();
 * </code>
 * </p>
 *
 * @author David Castro, dcastro@apu.edu
 * @version $Revision: 1.8 $
 * @see <a href="MetarFetcher.html">Metar</a>
 * @see <a href="Metar.html">Metar</a>
 */
public class Weather {
	private static Logger log = null;

	final static String httpMetarURL = "http://weather.noaa.gov/pub/data/observations/metar/stations/";
	//final static String httpMetarHostname =  "weather.noaa.gov";
	//final static int    httpMetarPort     =  80;
	//final static String httpMetarPath     = "/pub/data/observations/metar/stations/";

	static {
    	log = Logger.getLogger("net.sf.jweather");
		log.debug("Weather: instantiated");
	}

	public static Metar getMetar(String station) {
		return getMetar(station, 0);
	}

	public static Metar getMetar(String station, int timeout) {
		String metarData = MetarFetcher.fetch(station, timeout);
		Metar metar = null;

		if (metarData != null) {
			try {
				metar = MetarParser.parse(metarData);
			} catch (Exception e) {
				System.err.println("Weather: "+e);
				e.printStackTrace(System.err);
			}
		}

		return metar;
	}
}
