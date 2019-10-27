package com.walmart.DroneDelivery.services;

import java.util.List;

import com.walmart.DroneDelivery.models.Order;

public interface OrderWriterService {

	void writeDelieveredOrders(List<Order> deliveredOrders);

	void writeUndeliveredOrders(List<Order> undeliveredOrders);
	
	String getResourcePath();

	void writeNPS(int nps);
}
