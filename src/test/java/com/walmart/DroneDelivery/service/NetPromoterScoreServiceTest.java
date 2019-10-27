package com.walmart.DroneDelivery.service;


import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.walmart.DroneDelivery.models.Order;
import com.walmart.DroneDelivery.services.NetPromoterScoreService;
import com.walmart.DroneDelivery.services.OrderServiceImpl;

public class NetPromoterScoreServiceTest {
	
	OrderServiceImpl orderService = new OrderServiceImpl();
	NetPromoterScoreService netPromoterService = new NetPromoterScoreService();
	
	
	@Test
	public void countNPS_WhenAllCustomersSatisfied() {
		List<Order> orders = orderService.makeOrders(Arrays.asList("WM001 N11W5 05:11:50", "WM002 S3E2 05:11:55"));
		orders.get(0).setOrderDeliveredTime(LocalTime.of(06, 00, 00));
		orders.get(1).setOrderDeliveredTime(LocalTime.of(06, 05, 00));
		netPromoterService.countNPS(orders);
		assertEquals(100, netPromoterService.getNPS());
	}
	
	@Test
	public void countNPS_WhenHalfCustomersPromotersAndHalfDetractors() {
		List<Order> orders = orderService.makeOrders(Arrays.asList("WM001 N11W5 05:11:50", "WM002 S120E120 05:11:55"));
		orders.get(0).setOrderDeliveredTime(LocalTime.of(06, 10, 00));
		orders.get(1).setOrderDeliveredTime(LocalTime.of(23, 05, 00));
		netPromoterService.countNPS(orders);
		assertEquals(0, netPromoterService.getNPS());
	}
	
	@Test
	public void countNPS_WhenHalfCustomersPromotersAndHalfNeutrals() {
		List<Order> orders = orderService.makeOrders(Arrays.asList("WM001 N11W5 05:11:50", "WM002 S60E60 05:11:55"));
		orders.get(0).setOrderDeliveredTime(LocalTime.of(06, 00, 00));
		orders.get(1).setOrderDeliveredTime(LocalTime.of(8, 05, 00));
		netPromoterService.countNPS(orders);
		assertEquals(50, netPromoterService.getNPS());
	}
}
