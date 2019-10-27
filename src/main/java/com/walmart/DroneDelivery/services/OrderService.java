package com.walmart.DroneDelivery.services;

import java.util.List;

import com.walmart.DroneDelivery.models.Order;

public interface OrderService {

	public void processOrders(List<Order> orders);
	public List<Order> makeOrders(List<String> rows);
}
