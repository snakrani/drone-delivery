package com.walmart.DroneDelivery.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.walmart.DroneDelivery.models.Order;
import com.walmart.DroneDelivery.services.DroneDeliveryServiceImpl;
import com.walmart.DroneDelivery.services.OrderServiceImpl;

public class DroneDeliveryServiceImplTest {

	DroneDeliveryServiceImpl droneDeliveryService = new DroneDeliveryServiceImpl();
	OrderServiceImpl orderService = new OrderServiceImpl();
	
	List<Order> orders = orderService.makeOrders(Arrays.asList( "WM001 S3E2 05:11:55", "WM002 N11W5 05:11:50"));
	
	@Test
	public void deliverOrders_ShouldDeliverOrdersAfterSix() {
		LocalTime time = LocalTime.of(06, 00, 00);
		droneDeliveryService.setCurrentTime(time);
		List<Order> deliveredOrders = droneDeliveryService.deliverOrders(orders);
		assertEquals(2, deliveredOrders.size());
	}

	@Test
	public void deliverOrders_ShouldSetOrderDeliveredTime() {
		LocalTime time = LocalTime.of(06, 00, 00);
		droneDeliveryService.setCurrentTime(time);
		List<Order> deliveredOrders = droneDeliveryService.deliverOrders(orders);
		assertEquals(LocalTime.of(06, 03, 36), deliveredOrders.get(0).getOrderDeliveredTime());
		assertEquals(LocalTime.of(06, 19, 16), deliveredOrders.get(1).getOrderDeliveredTime());
	}
	
	@Test
	public void deliverOrders_ShouldNotDeliverOrdersBeforeSix() {
		LocalTime time = LocalTime.of(05, 59, 01);
		droneDeliveryService.setCurrentTime(time);
		List<Order> deliveredOrders = droneDeliveryService.deliverOrders(orders);
		assertEquals(0, deliveredOrders.size());
	}

	@Test
	public void deliverOrders_ShouldNotDeliverOrdersAfterTen() {
		LocalTime time = LocalTime.of(22, 00, 01);
		droneDeliveryService.setCurrentTime(time);
		List<Order> deliveredOrders = droneDeliveryService.deliverOrders(orders);
		assertEquals(0, deliveredOrders.size());
	}
	
}
