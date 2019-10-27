package com.walmart.DroneDelivery.services;

import java.util.List;

import com.walmart.DroneDelivery.models.Order;

public interface DroneDeliveryService {
	
	List<Order> deliverOrders(List<Order> orders);
}
