package com.walmart.DroneDelivery.utils;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

public class TimeUtilTest {

	TimeUtil timeUtil = new TimeUtil();
	
	@Test
	public void getLocalTime_ConvertsStringToLocalTime() {
		LocalTime expected =LocalTime.of(12, 20, 32);
		LocalTime actual = timeUtil.getLocalTime("12:20:32");
		assertEquals(expected, actual);
	}
	
}

