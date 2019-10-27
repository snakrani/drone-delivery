package com.walmart.DroneDelivery.models;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

public class OrdersTest {

	Order order = new Order("S1", "S3E2", "05:11:55");

	@Test
	public void getOrderProcessedTimeString_ShouldFormatTimeInTwoDigitFormat() {
		order.setOrderProcessedTime(LocalTime.of(12, 0, 0));
		assertEquals("12:00:00", order.getOrderProcessedTimeString());
	}
}
