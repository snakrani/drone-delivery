package com.walmart.DroneDelivery.utils;

import java.time.LocalTime;

public class TimeUtil {

	public LocalTime getLocalTime(String time) {
		String[] hhMMSS = time.split(":");
		LocalTime localTime = LocalTime.of(Integer.parseInt(hhMMSS[0]), 
				Integer.parseInt(hhMMSS[1]),
				Integer.parseInt(hhMMSS[2]));
		return localTime;
	}
}
