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
package net.sf.jweather.tests;

import net.sf.jweather.metar.*;
import junit.framework.TestCase;
import java.util.Calendar;
import java.util.TimeZone;

public class MetarTest extends TestCase {
  Metar metar = null;
  String metarData = "";

  public MetarTest(String str) {
    super(str);
  }

  public static void main(String args[]) {
    junit.textui.TestRunner.run(MetarTest.class);
  }

  public void testClearRecord1() {
    metarData = "2004/01/06 02:50\n";
    metarData
      += "KLAX 060250Z 34010KT 10SM CLR 14/M07 A3012 RMK AO2 SLP199 T01441072 55003\n";

    try {
      metar = MetarParser.parseRecord(metarData);
      verifyClear1(metar);
      assertEquals("2004/01/06 02:50", metar.getDateString());
      Calendar calendar = Calendar.getInstance();
      TimeZone gmtZone = TimeZone.getTimeZone("GMT");
      calendar.setTimeZone(gmtZone);
      calendar.set(Calendar.YEAR, 2004);
      calendar.set(Calendar.MONTH, 0);
      calendar.set(Calendar.DAY_OF_MONTH, 6);
      calendar.set(Calendar.HOUR_OF_DAY, 2);
      calendar.set(Calendar.MINUTE, 50);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      assertTrue(metar.getDate().equals(calendar.getTime()));
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
      fail("exception in testGeneral1. message: " + e.getMessage());
    }
  }

  public void testClearReport1() {
    try {
      metar = MetarParser.parseReport("KLAX 060250Z 34010KT 10SM CLR 14/M07 A3012 RMK AO2 SLP199 T01441072 55003");
      verifyClear1(metar);
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
      fail("exception in testGeneral1. message: " + e.getMessage());
    }
  }
  
  public void testClearReportDate1() {
    try {
      metar = MetarParser.parseReport("2004/01/06 02:50",
      "KLAX 060250Z 34010KT 10SM CLR 14/M07 A3012 RMK AO2 SLP199 T01441072 55003");
      verifyClear1(metar);
      assertEquals("2004/01/06 02:50", metar.getDateString());
      Calendar calendar = Calendar.getInstance();
      TimeZone gmtZone = TimeZone.getTimeZone("GMT");
      calendar.setTimeZone(gmtZone);
      calendar.set(Calendar.YEAR, 2004);
      calendar.set(Calendar.MONTH, 0);
      calendar.set(Calendar.DAY_OF_MONTH, 6);
      calendar.set(Calendar.HOUR_OF_DAY, 2);
      calendar.set(Calendar.MINUTE, 50);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      assertTrue(metar.getDate().equals(calendar.getTime()));
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
      fail("exception in testGeneral1. message: " + e.getMessage());
    }
  }

  public void verifyClear1(Metar metar) throws Exception {
    
    assertEquals("KLAX", metar.getStationID());



    assertEquals(new Integer(340), metar.getWindDirection());
    assertEquals(new Float(10), metar.getWindSpeedInKnots());
    assertEquals(new Float(10), metar.getVisibility());
    assertEquals(new Float(30.12), metar.getPressure());

    assertEquals(new Float(14), metar.getTemperatureInCelsius());
    assertEquals(new Float(57.2), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-7), metar.getDewPointInCelsius());
    assertEquals(new Float(19.4), metar.getDewPointInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperaturePreciseInCelsius());
    assertEquals(new Float(57.9), metar.getTemperaturePreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(57.9),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointMostPreciseInFahrenheit());
}

public void testGeneral2() {
  metarData = "2004/01/23 12:00\n";
  metarData += "KMYV 231200Z AUTO 00000KT M1/4SM FG VV003 03/03 A3027 RMK AO2";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/23 12:00", metar.getDateString());
    assertEquals("KMYV", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 23);
    calendar.set(Calendar.HOUR_OF_DAY, 12);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    WeatherCondition wc = metar.getWeatherCondition(0);
    assertEquals(wc.isFog(), true);

    assertEquals(new Integer(0), metar.getWindDirection());
    assertEquals(new Float(0), metar.getWindSpeedInKnots());
    assertEquals(new Float(0.25), metar.getVisibility());
    assertTrue(metar.getVisibilityLessThan());
    assertEquals(new Float(30.27), metar.getPressure());

    assertEquals(new Float(3), metar.getTemperatureInCelsius());
    assertEquals(new Float(37.4), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(3), metar.getDewPointInCelsius());
    assertEquals(new Float(37.4), metar.getDewPointInFahrenheit());

    assertEquals(null, metar.getTemperaturePreciseInCelsius());
    assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
    assertEquals(null, metar.getDewPointPreciseInCelsius());
    assertEquals(null, metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(3), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(37.4),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(3), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(37.4), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testGeneral2. message: " + e.getMessage());
  }
}

public void testFractionalVisibility1() {
  metarData = "2004/01/06 02:50\n";
  metarData
    += "KLAX 060250Z 34010KT 1/2SM BR 14/M07 A3012 RMK AO2 SLP199 T01441072 55003\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/06 02:50", metar.getDateString());
    assertEquals("KLAX", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 6);
    calendar.set(Calendar.HOUR_OF_DAY, 2);
    calendar.set(Calendar.MINUTE, 50);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    WeatherCondition wc = metar.getWeatherCondition(0);
    assertEquals(wc.isMist(), true);

    assertEquals(new Integer(340), metar.getWindDirection());
    assertEquals(new Float(10), metar.getWindSpeedInKnots());
    assertEquals(new Float(.5), metar.getVisibility());
    assertEquals(new Float(30.12), metar.getPressure());

    assertEquals(new Float(14), metar.getTemperatureInCelsius());
    assertEquals(new Float(57.2), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-7), metar.getDewPointInCelsius());
    assertEquals(new Float(19.4), metar.getDewPointInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperaturePreciseInCelsius());
    assertEquals(new Float(57.9), metar.getTemperaturePreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(57.9),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testFractionalVisibility1. message: " + e.getMessage());
  }
}

public void testFractionalVisibility2() {
  metarData = "2004/01/06 02:50\n";
  metarData
    += "KLAX 060250Z 34010KT M1/4SM BR 14/M07 A3012 RMK AO2 SLP199 T01441072 55003\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/06 02:50", metar.getDateString());
    assertEquals("KLAX", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 6);
    calendar.set(Calendar.HOUR_OF_DAY, 2);
    calendar.set(Calendar.MINUTE, 50);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    WeatherCondition wc = metar.getWeatherCondition(0);
    assertEquals(wc.isMist(), true);

    assertEquals(new Integer(340), metar.getWindDirection());
    assertEquals(new Float(10), metar.getWindSpeedInKnots());
    assertEquals(new Float(.25), metar.getVisibility());
    assertTrue(metar.getVisibilityLessThan());
    assertEquals(new Float(30.12), metar.getPressure());

    assertEquals(new Float(14), metar.getTemperatureInCelsius());
    assertEquals(new Float(57.2), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-7), metar.getDewPointInCelsius());
    assertEquals(new Float(19.4), metar.getDewPointInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperaturePreciseInCelsius());
    assertEquals(new Float(57.9), metar.getTemperaturePreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(57.9),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testFractionalVisibility1. message: " + e.getMessage());
  }
}

public void testFractionalVisibilityInKilometers() {
  metarData = "2004/01/06 02:50\n";
  metarData
    += "KLAX 060250Z 34010KT 1 1/2KM BR 14/M07 A3012 RMK AO2 SLP199 T01441072 55003\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/06 02:50", metar.getDateString());
    assertEquals("KLAX", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 6);
    calendar.set(Calendar.HOUR_OF_DAY, 2);
    calendar.set(Calendar.MINUTE, 50);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    WeatherCondition wc = metar.getWeatherCondition(0);
    assertEquals(wc.isMist(), true);

    assertEquals(new Integer(340), metar.getWindDirection());
    assertEquals(new Float(10), metar.getWindSpeedInKnots());
    assertEquals(new Float(1.5), metar.getVisibilityInKilometers());
    assertEquals(new Float(30.12), metar.getPressure());

    assertEquals(new Float(14), metar.getTemperatureInCelsius());
    assertEquals(new Float(57.2), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-7), metar.getDewPointInCelsius());
    assertEquals(new Float(19.4), metar.getDewPointInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperaturePreciseInCelsius());
    assertEquals(new Float(57.9), metar.getTemperaturePreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(57.9),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail(
      "exception in testFractionalVisibilityInKilometers. message: "
        + e.getMessage());
  }
}

public void testVisibilityInKilometers() {
  metarData = "2004/01/06 20:50\n";
  metarData += "LOXT 062050Z VRB03KT 10KM FEW060SC BKN120AC M10/M12 Q1025 BNK";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/06 20:50", metar.getDateString());
    assertEquals("LOXT", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 6);
    calendar.set(Calendar.HOUR_OF_DAY, 20);
    calendar.set(Calendar.MINUTE, 50);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    assertTrue(metar.getWindDirectionIsVariable());
    assertEquals(new Float(3), metar.getWindSpeedInKnots());
    assertEquals(new Float(10), metar.getVisibilityInKilometers());

    assertEquals(new Float(-10), metar.getTemperatureInCelsius());
    assertEquals(new Float(14), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-12), metar.getDewPointInCelsius());
    assertEquals(new Float(10.4), metar.getDewPointInFahrenheit());

    assertEquals(null, metar.getTemperaturePreciseInCelsius());
    assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
    assertEquals(null, metar.getDewPointPreciseInCelsius());
    assertEquals(null, metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(-10), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(new Float(14), metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-12), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(10.4), metar.getDewPointMostPreciseInFahrenheit());

    SkyCondition sc = metar.getSkyCondition(0);
    assertTrue(sc.isFewClouds());
    assertEquals(6000, sc.getHeight());
    assertEquals("SC", sc.getModifier());

    sc = metar.getSkyCondition(1);
    assertTrue(sc.isBrokenClouds());
    assertEquals(12000, sc.getHeight());
    assertEquals("AC", sc.getModifier());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testVisibilityInKilometers. message: " + e.getMessage());
  }
}

public void testVisibilityInMeters() {
  metarData = "2004/01/07 20:50\n";
  metarData
    += "LOWW 072050Z 15002KT 4000 -SN BR FEW008 SCT011 BKN067 M05/M06 Q1020 NOSIG 11490531 16490336";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/07 20:50", metar.getDateString());
    assertEquals("LOWW", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 7);
    calendar.set(Calendar.HOUR_OF_DAY, 20);
    calendar.set(Calendar.MINUTE, 50);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    assertEquals(new Integer(150), metar.getWindDirection());
    assertEquals(new Float(2), metar.getWindSpeedInKnots());
    assertEquals(new Float(4000), metar.getVisibilityInMeters());

    assertEquals(new Float(-5), metar.getTemperatureInCelsius());
    assertEquals(new Float(23), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-6), metar.getDewPointInCelsius());
    assertEquals(new Float(21.2), metar.getDewPointInFahrenheit());

    assertEquals(null, metar.getTemperaturePreciseInCelsius());
    assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
    assertEquals(null, metar.getDewPointPreciseInCelsius());
    assertEquals(null, metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(-5), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(new Float(23), metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-6), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(21.2), metar.getDewPointMostPreciseInFahrenheit());

    SkyCondition sc = metar.getSkyCondition(0);
    assertTrue(sc.isFewClouds());
    assertEquals(800, sc.getHeight());

    sc = metar.getSkyCondition(1);
    assertTrue(sc.isScatteredClouds());
    assertEquals(1100, sc.getHeight());

    sc = metar.getSkyCondition(2);
    assertTrue(sc.isBrokenClouds());
    assertEquals(6700, sc.getHeight());

    WeatherCondition wc = metar.getWeatherCondition(0);
    assertTrue(wc.isLight());
    assertTrue(wc.isSnow());

    wc = metar.getWeatherCondition(1);
    assertTrue(wc.isMist());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testVisibilityInMeters. message: " + e.getMessage());
  }
}

public void testRainWithOutRVR() {
  metarData = "2004/01/30 06:15\n";
  metarData += "KVCB 300615Z AUTO 00000KT 2 1/2SM RA CLR 06/06 A3017 RMK AO2\n";

  System.out.println("METAR: " + metarData);

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals(metar.getDateString(), "2004/01/30 06:15");
    assertEquals(metar.getStationID(), "KVCB");

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 30);
    calendar.set(Calendar.HOUR_OF_DAY, 6);
    calendar.set(Calendar.MINUTE, 15);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    WeatherCondition wc = metar.getWeatherCondition(0);
    assertEquals(true, wc.isRain());

    assertEquals(new Integer(0), metar.getWindDirection());
    assertEquals(new Float(0), metar.getWindSpeedInKnots());
    assertEquals(new Float(2.5), metar.getVisibility());
    assertEquals(new Float(30.17), metar.getPressure());

    assertEquals(new Float(6), metar.getTemperatureInCelsius());
    assertEquals(new Float(6), metar.getDewPointInCelsius());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testRainWithOutRVR. message: " + e.getMessage());
  }
}

public void testWindSpeedInMPS() {
  metarData = "2004/01/06 02:50\n";
  metarData
    += "KLAX 060250Z 34020MPS 10SM CLR 14/M07 A3012 RMK AO2 SLP199 T01441072 55003\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/06 02:50", metar.getDateString());
    assertEquals("KLAX", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 6);
    calendar.set(Calendar.HOUR_OF_DAY, 2);
    calendar.set(Calendar.MINUTE, 50);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    assertEquals(new Integer(340), metar.getWindDirection());
    assertEquals(new Float(20), metar.getWindSpeedInMPS());
    assertEquals(new Float(10), metar.getVisibility());
    assertEquals(new Float(30.12), metar.getPressure());

    assertEquals(new Float(14), metar.getTemperatureInCelsius());
    assertEquals(new Float(57.2), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-7), metar.getDewPointInCelsius());
    assertEquals(new Float(19.4), metar.getDewPointInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperaturePreciseInCelsius());
    assertEquals(new Float(57.9), metar.getTemperaturePreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(14.4), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(57.9),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-7.2), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(19), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testWindSpeedInMPS. message: " + e.getMessage());
  }
}

public void testCavok() {
  metarData = "2004/01/28 03:20\n";
  metarData += "EGPF 280320Z 30008KT CAVOK 01/M03 Q1006\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/28 03:20", metar.getDateString());
    assertEquals("EGPF", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 28);
    calendar.set(Calendar.HOUR_OF_DAY, 3);
    calendar.set(Calendar.MINUTE, 20);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    assertEquals(new Integer(300), metar.getWindDirection());
    assertEquals(new Float(8), metar.getWindSpeedInKnots());
    assertEquals(new Float(10), metar.getVisibilityInKilometers());
    assertTrue(metar.getIsCavok());

    assertEquals(new Float(1), metar.getTemperatureInCelsius());
    assertEquals(new Float(33.8), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-3), metar.getDewPointInCelsius());
    assertEquals(new Float(26.6), metar.getDewPointInFahrenheit());

    assertEquals(new Float(29.71), metar.getPressure());
    assertEquals(new Integer(1006), metar.getPressureInHectoPascals());

    assertEquals(null, metar.getTemperaturePreciseInCelsius());
    assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
    assertEquals(null, metar.getDewPointPreciseInCelsius());
    assertEquals(null, metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(1), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(33.8),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-3), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(26.6), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testGeneral1. message: " + e.getMessage());
  }
}

public void testVisibility() {
  metarData = "2004/01/28 03:20\n";
  metarData += "EGPF 280320Z 30008KT 9999 01/M03 Q1006\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/28 03:20", metar.getDateString());
    assertEquals("EGPF", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 28);
    calendar.set(Calendar.HOUR_OF_DAY, 3);
    calendar.set(Calendar.MINUTE, 20);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    assertEquals(new Integer(300), metar.getWindDirection());
    assertEquals(new Float(8), metar.getWindSpeedInKnots());
    assertEquals(new Float(10), metar.getVisibilityInKilometers());

    assertEquals(new Float(1), metar.getTemperatureInCelsius());
    assertEquals(new Float(33.8), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-3), metar.getDewPointInCelsius());
    assertEquals(new Float(26.6), metar.getDewPointInFahrenheit());

    assertEquals(null, metar.getTemperaturePreciseInCelsius());
    assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
    assertEquals(null, metar.getDewPointPreciseInCelsius());
    assertEquals(null, metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(1), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(33.8),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-3), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(26.6), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testGeneral1. message: " + e.getMessage());
  }
}

public void testNosig() {
  metarData = "2004/01/28 03:20\n";
  metarData
    += "EDDB 280320Z 21003KT 2300 BR FEW028 SCT062 01/M03 Q1000 NOSIG\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/28 03:20", metar.getDateString());
    assertEquals("EDDB", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 28);
    calendar.set(Calendar.HOUR_OF_DAY, 3);
    calendar.set(Calendar.MINUTE, 20);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    assertEquals(new Integer(210), metar.getWindDirection());
    assertEquals(new Float(3), metar.getWindSpeedInKnots());
    assertEquals(new Float(2.3), metar.getVisibilityInKilometers());
    assertTrue(metar.getIsNoSignificantChange());

    assertEquals(new Float(1), metar.getTemperatureInCelsius());
    assertEquals(new Float(33.8), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-3), metar.getDewPointInCelsius());
    assertEquals(new Float(26.6), metar.getDewPointInFahrenheit());

    assertEquals(null, metar.getTemperaturePreciseInCelsius());
    assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
    assertEquals(null, metar.getDewPointPreciseInCelsius());
    assertEquals(null, metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(1), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(33.8),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-3), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(26.6), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testGeneral1. message: " + e.getMessage());
  }
}

public void testNSC() {
  metarData = "2004/01/28 03:20\n";
  metarData += "EDDB 280320Z 21003KT 2300 NSC 01/M03 Q1000\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/01/28 03:20", metar.getDateString());
    assertEquals("EDDB", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 28);
    calendar.set(Calendar.HOUR_OF_DAY, 3);
    calendar.set(Calendar.MINUTE, 20);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    assertEquals(new Integer(210), metar.getWindDirection());
    assertEquals(new Float(3), metar.getWindSpeedInKnots());
    assertEquals(new Float(2.3), metar.getVisibilityInKilometers());

    SkyCondition sc = metar.getSkyCondition(0);
    assertTrue(sc.isNoSignificantClouds());

    assertEquals(new Float(1), metar.getTemperatureInCelsius());
    assertEquals(new Float(33.8), metar.getTemperatureInFahrenheit());
    assertEquals(new Float(-3), metar.getDewPointInCelsius());
    assertEquals(new Float(26.6), metar.getDewPointInFahrenheit());

    assertEquals(null, metar.getTemperaturePreciseInCelsius());
    assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
    assertEquals(null, metar.getDewPointPreciseInCelsius());
    assertEquals(null, metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(1), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(
      new Float(33.8),
      metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(new Float(-3), metar.getDewPointMostPreciseInCelsius());
    assertEquals(new Float(26.6), metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testGeneral1. message: " + e.getMessage());
  }
}


public void testMissingDewPoint() {
  metarData = "2004/08/26 00:50\n";
  metarData += "KWHP 260050Z 16008KT 10SM SKC 28/ A2993\n";

  try {
    metar = MetarParser.parseRecord(metarData);
    assertEquals("2004/08/26 00:50", metar.getDateString());
    assertEquals("KWHP", metar.getStationID());

    Calendar calendar = Calendar.getInstance();
    TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(gmtZone);
    calendar.set(Calendar.YEAR, 2004);
    calendar.set(Calendar.MONTH, 7);
    calendar.set(Calendar.DAY_OF_MONTH, 26);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 50);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    assertTrue(metar.getDate().equals(calendar.getTime()));

    assertEquals(new Integer(160), metar.getWindDirection());
    assertEquals(new Float(8), metar.getWindSpeedInKnots());
    assertEquals(new Float(10), metar.getVisibility());

    SkyCondition sc = metar.getSkyCondition(0);
    assertTrue(sc.isClear());

    assertEquals(new Float(28), metar.getTemperatureInCelsius());
    assertEquals(new Float(82.4), metar.getTemperatureInFahrenheit());
    assertEquals(null, metar.getDewPointInCelsius());
    assertEquals(null, metar.getDewPointInFahrenheit());
    
    assertEquals(new Float(29.93), metar.getPressure());

    assertEquals(null, metar.getTemperaturePreciseInCelsius());
    assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
    assertEquals(null, metar.getDewPointPreciseInCelsius());
    assertEquals(null, metar.getDewPointPreciseInFahrenheit());

    assertEquals(new Float(28), metar.getTemperatureMostPreciseInCelsius());
    assertEquals(new Float(82.4), metar.getTemperatureMostPreciseInFahrenheit());
    assertEquals(null, metar.getDewPointMostPreciseInCelsius());
    assertEquals(null, metar.getDewPointMostPreciseInFahrenheit());
  }
  catch (Exception e) {
    e.printStackTrace(System.err);
    fail("exception in testMissingDewPoint. message: " + e.getMessage());
  }
  }

  public void testMissingTemperature() {
    metarData = "2004/08/26 00:50\n";
    metarData += "KWHP 260050Z 16008KT 10SM SKC /28 A2993\n";

    try {
      metar = MetarParser.parseRecord(metarData);
      assertEquals("2004/08/26 00:50", metar.getDateString());
      assertEquals("KWHP", metar.getStationID());

      Calendar calendar = Calendar.getInstance();
      TimeZone gmtZone = TimeZone.getTimeZone("GMT");
      calendar.setTimeZone(gmtZone);
      calendar.set(Calendar.YEAR, 2004);
      calendar.set(Calendar.MONTH, 7);
      calendar.set(Calendar.DAY_OF_MONTH, 26);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 50);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      assertTrue(metar.getDate().equals(calendar.getTime()));

      assertEquals(new Integer(160), metar.getWindDirection());
      assertEquals(new Float(8), metar.getWindSpeedInKnots());
      assertEquals(new Float(10), metar.getVisibility());

      SkyCondition sc = metar.getSkyCondition(0);
      assertTrue(sc.isClear());

      assertEquals(null, metar.getTemperatureInCelsius());
      assertEquals(null, metar.getTemperatureInFahrenheit());
      assertEquals(new Float(28), metar.getDewPointInCelsius());
      assertEquals(new Float(82.4), metar.getDewPointInFahrenheit());
      
      assertEquals(new Float(29.93), metar.getPressure());

      assertEquals(null, metar.getTemperaturePreciseInCelsius());
      assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
      assertEquals(null, metar.getDewPointPreciseInCelsius());
      assertEquals(null, metar.getDewPointPreciseInFahrenheit());

      assertEquals(null, metar.getTemperatureMostPreciseInCelsius());
      assertEquals(null, metar.getTemperatureMostPreciseInFahrenheit());
      assertEquals(new Float(28), metar.getDewPointMostPreciseInCelsius());
      assertEquals(new Float(82.4), metar.getDewPointMostPreciseInFahrenheit());
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
      fail("exception in testMissingTemperature. message: " + e.getMessage());
    }
  }
  public void testMissingTemperatureAndDewpoint() {
      metarData = "2004/08/26 00:50\n";
      metarData += "KWHP 260050Z 16008KT 10SM SKC / A2993\n";

      try {
        metar = MetarParser.parseRecord(metarData);
        assertEquals("2004/08/26 00:50", metar.getDateString());
        assertEquals("KWHP", metar.getStationID());

        Calendar calendar = Calendar.getInstance();
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(gmtZone);
        calendar.set(Calendar.YEAR, 2004);
        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.DAY_OF_MONTH, 26);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(metar.getDate().equals(calendar.getTime()));

        assertEquals(new Integer(160), metar.getWindDirection());
        assertEquals(new Float(8), metar.getWindSpeedInKnots());
        assertEquals(new Float(10), metar.getVisibility());

        SkyCondition sc = metar.getSkyCondition(0);
        assertTrue(sc.isClear());

        assertEquals(null, metar.getTemperatureInCelsius());
        assertEquals(null, metar.getTemperatureInFahrenheit());
        assertEquals(null, metar.getDewPointInCelsius());
        assertEquals(null, metar.getDewPointInFahrenheit());
        
        assertEquals(new Float(29.93), metar.getPressure());

        assertEquals(null, metar.getTemperaturePreciseInCelsius());
        assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
        assertEquals(null, metar.getDewPointPreciseInCelsius());
        assertEquals(null, metar.getDewPointPreciseInFahrenheit());

        assertEquals(null, metar.getTemperatureMostPreciseInCelsius());
        assertEquals(null, metar.getTemperatureMostPreciseInFahrenheit());
        assertEquals(null, metar.getDewPointMostPreciseInCelsius());
        assertEquals(null, metar.getDewPointMostPreciseInFahrenheit());
      }
      catch (Exception e) {
        e.printStackTrace(System.err);
        fail("exception in testMissingTemperatureAndDewpoint. message: " + e.getMessage());
      }
    }
  
  public void testMissingVisAndWeatherCondition() {
      metarData = "2004/08/26 06:54\n";
      metarData += "K12N 260654Z AUTO 00000KT 13/12 A3032 RMK AO2 SLP264 T01330117 PWINO TSNO\n";

      try {
        metar = MetarParser.parseRecord(metarData);
        assertEquals("2004/08/26 06:54", metar.getDateString());
        assertEquals("K12N", metar.getStationID());

        Calendar calendar = Calendar.getInstance();
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(gmtZone);
        calendar.set(Calendar.YEAR, 2004);
        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.DAY_OF_MONTH, 26);
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 54);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(metar.getDate().equals(calendar.getTime()));

        assertEquals(new Integer(0), metar.getWindDirection());
        assertEquals(new Float(0), metar.getWindSpeedInKnots());
        assertEquals(null, metar.getVisibility());

        assertEquals(0, metar.getSkyConditions().size());

        assertEquals(new Float(13), metar.getTemperatureInCelsius());
        assertEquals(new Float(55.4), metar.getTemperatureInFahrenheit());
        assertEquals(new Float(12), metar.getDewPointInCelsius());
        assertEquals(new Float(53.6), metar.getDewPointInFahrenheit());
        
        assertEquals(new Float(30.32), metar.getPressure());

        assertEquals(new Float(13.3), metar.getTemperaturePreciseInCelsius());
        assertEquals(new Float(55.9), metar.getTemperaturePreciseInFahrenheit());
        assertEquals(new Float(11.7), metar.getDewPointPreciseInCelsius());
        assertEquals(new Float(53.1), metar.getDewPointPreciseInFahrenheit());
      }
      catch (Exception e) {
        e.printStackTrace(System.err);
        fail("exception in testMissingVisAndWeatherCondition. message: " + e.getMessage());
      }
    }

    public void testHectoPascalPressure() {
      metarData = "2004/09/03 00:50\n";
      metarData += "EGDL 030050Z 19006KT CAVOK 14/11 Q1018\n";

      try {
        metar = MetarParser.parseRecord(metarData);
        assertEquals("2004/09/03 00:50", metar.getDateString());
        assertEquals("EGDL", metar.getStationID());

        Calendar calendar = Calendar.getInstance();
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(gmtZone);
        calendar.set(Calendar.YEAR, 2004);
        calendar.set(Calendar.MONTH, 8);
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(metar.getDate().equals(calendar.getTime()));

        assertEquals(new Integer(190), metar.getWindDirection());
        assertEquals(new Float(6), metar.getWindSpeedInKnots());
        assertEquals(new Float(10), metar.getVisibilityInKilometers());
        assertTrue(metar.getIsCavok());

        assertEquals(new Float(14), metar.getTemperatureInCelsius());
        assertEquals(new Float(57.2), metar.getTemperatureInFahrenheit());
        assertEquals(new Float(11), metar.getDewPointInCelsius());
        assertEquals(new Float(51.8), metar.getDewPointInFahrenheit());
        
        assertEquals(new Float(30.06), metar.getPressure());
        assertEquals(new Integer(1018), metar.getPressureInHectoPascals());

        assertEquals(null, metar.getTemperaturePreciseInCelsius());
        assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
        assertEquals(null, metar.getDewPointPreciseInCelsius());
        assertEquals(null, metar.getDewPointPreciseInFahrenheit());
      }
      catch (Exception e) {
        e.printStackTrace(System.err);
        fail("exception in testMissingVisAndWeatherCondition. message: " + e.getMessage());
      }
    }

    public void testBecomingVisibilityHaze() {
      metarData = "2004/09/03 00:50\n";
      metarData += "EGDL 030050Z 19006KT CAVOK 14/11 Q1018 BECMG 7000 HZ\n";

      try {
        metar = MetarParser.parseRecord(metarData);
        assertEquals("2004/09/03 00:50", metar.getDateString());
        assertEquals("EGDL", metar.getStationID());

        Calendar calendar = Calendar.getInstance();
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(gmtZone);
        calendar.set(Calendar.YEAR, 2004);
        calendar.set(Calendar.MONTH, 8);
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(metar.getDate().equals(calendar.getTime()));

        assertEquals(new Integer(190), metar.getWindDirection());
        assertEquals(new Float(6), metar.getWindSpeedInKnots());
        assertEquals(new Float(10), metar.getVisibilityInKilometers());
        assertTrue(metar.getIsCavok());

        assertEquals(new Float(14), metar.getTemperatureInCelsius());
        assertEquals(new Float(57.2), metar.getTemperatureInFahrenheit());
        assertEquals(new Float(11), metar.getDewPointInCelsius());
        assertEquals(new Float(51.8), metar.getDewPointInFahrenheit());
        
        assertEquals(new Float(30.06), metar.getPressure());
        assertEquals(new Integer(1018), metar.getPressureInHectoPascals());

        assertEquals(null, metar.getTemperaturePreciseInCelsius());
        assertEquals(null, metar.getTemperaturePreciseInFahrenheit());
        assertEquals(null, metar.getDewPointPreciseInCelsius());
        assertEquals(null, metar.getDewPointPreciseInFahrenheit());
        
        assertEquals("BECMG 7000 HZ", metar.getBecoming());
      }
      catch (Exception e) {
        e.printStackTrace(System.err);
        fail("exception in testMissingVisAndWeatherCondition. message: " + e.getMessage());
      }
    }
    
    public void testVRB05() {
        metarData = "2004/09/23 16:47\n";
        metarData += "KPAO 231647Z VRB05 30SM FEW150 20/12 A3005\n";

        try {
          metar = MetarParser.parseRecord(metarData);
          assertEquals("2004/09/23 16:47", metar.getDateString());
          assertEquals("KPAO", metar.getStationID());

          Calendar calendar = Calendar.getInstance();
          TimeZone gmtZone = TimeZone.getTimeZone("GMT");
          calendar.setTimeZone(gmtZone);
          calendar.set(Calendar.YEAR, 2004);
          calendar.set(Calendar.MONTH, 8);
          calendar.set(Calendar.DAY_OF_MONTH, 23);
          calendar.set(Calendar.HOUR_OF_DAY, 16);
          calendar.set(Calendar.MINUTE, 47);
          calendar.set(Calendar.SECOND, 0);
          calendar.set(Calendar.MILLISECOND, 0);
          assertTrue(metar.getDate().equals(calendar.getTime()));

          assertTrue(metar.getWindDirectionIsVariable());
          assertEquals(null, metar.getWindDirection());
          assertEquals(new Float(5), metar.getWindSpeedInKnots());
          assertEquals(new Float(30), metar.getVisibility());
          
          // Add few150

          assertEquals(new Float(20), metar.getTemperatureInCelsius());
          assertEquals(new Float(12), metar.getDewPointInCelsius());
          
          assertEquals(new Float(30.05), metar.getPressure());

        }
        catch (Exception e) {
          e.printStackTrace(System.err);
          fail("exception in testVRB05. message: " + e.getMessage());
        }
    }
    
    public void testMissingVis() {
      metarData = "2004/09/16 00:19\n";
      metarData += "KHSA 160019Z AUTO 02017KT 23/22 A2949 RMK\n";

      try {
        metar = MetarParser.parseRecord(metarData);
        assertEquals("2004/09/16 00:19", metar.getDateString());
        assertEquals("KHSA", metar.getStationID());

        Calendar calendar = Calendar.getInstance();
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(gmtZone);
        calendar.set(Calendar.YEAR, 2004);
        calendar.set(Calendar.MONTH, 8);
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 19);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(metar.getDate().equals(calendar.getTime()));

        assertEquals(new Integer(20), metar.getWindDirection());
        assertEquals(new Float(17), metar.getWindSpeedInKnots());
        assertEquals(null, metar.getVisibility());
        
        assertEquals(new Float(23), metar.getTemperatureInCelsius());
        assertEquals(new Float(22), metar.getDewPointInCelsius());
        
        assertEquals(new Float(29.49), metar.getPressure());
      }
      catch (Exception e) {
        e.printStackTrace(System.err);
        fail("exception in testMissingVis. message: " + e.getMessage());
      }
   }
   
   public void testDirectionalVisibility() {
      metarData = "2004/09/20 08:50\n";
      metarData += "EGBJ 200850Z 23007G17KT 4000NE RADZ BKN009 BKN015 15/14 Q1006\n";

      try {
        metar = MetarParser.parseRecord(metarData);
        assertEquals("2004/09/20 08:50", metar.getDateString());
        assertEquals("EGBJ", metar.getStationID());

        Calendar calendar = Calendar.getInstance();
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(gmtZone);
        calendar.set(Calendar.YEAR, 2004);
        calendar.set(Calendar.MONTH, 8);
        calendar.set(Calendar.DAY_OF_MONTH, 20);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(metar.getDate().equals(calendar.getTime()));

        assertEquals(new Integer(230), metar.getWindDirection());
        assertEquals(new Float(7), metar.getWindSpeedInKnots());
        assertEquals(new Float(17), metar.getWindGustsInKnots());
        assertEquals(new Float(4000), metar.getVisibilityInMeters());
        
        assertEquals(new Float(15), metar.getTemperatureInCelsius());
        assertEquals(new Float(14), metar.getDewPointInCelsius());
        
        assertEquals(new Integer(1006), metar.getPressureInHectoPascals());
      }
      catch (Exception e) {
        e.printStackTrace(System.err);
        fail("exception in testDirectionalVisibility. message: " + e.getMessage());
      }
   }
}
