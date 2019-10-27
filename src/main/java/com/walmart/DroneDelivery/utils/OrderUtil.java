package com.walmart.DroneDelivery.utils;

public class OrderUtil {

	public double getTimeToDeliverOrder(String direction) {
		int eastOrWestIndex = 0;
		for(int i = 1; i < direction.length(); i++) {
			if(direction.charAt(i) == 'E' || direction.charAt(i) == 'W') {
				eastOrWestIndex = i;
			}
		}
		int x = Integer.parseInt(direction.substring(1, eastOrWestIndex));
		int y = Integer.parseInt(direction.substring(eastOrWestIndex+1, direction.length()));
		double distance = Math.sqrt((x * x) + (double)(y * y)); 
		return convertTimeToMinutesAndSeconds(distance);
	}

	private double convertTimeToMinutesAndSeconds(double distance) {
		int distanceMinutes = (int) distance;
		int mantissa = (int) ((distance - distanceMinutes) * 100);
		int distanceSeconds = (int) (mantissa*60 / 100);
		String str = distanceMinutes + "." + distanceSeconds;
		return Double.valueOf(str);
	}
}
