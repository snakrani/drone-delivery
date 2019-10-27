package com.walmart.DroneDelivery.services;

import java.time.Duration;
import java.util.List;

import com.walmart.DroneDelivery.models.Order;

public class NetPromoterScoreService {

	float promotersCount;
	float detractorsCount;
	float totalCount;

	public void countNPS(List<Order> orders) {
		for (Order order : orders) {
			int hoursTaken = (int) Duration.between(order.getOrderTime(), order.getOrderDeliveredTime()).toHours();
			if (hoursTaken < 2) {
				promotersCount++;
			} else if (hoursTaken >= 4) {
				detractorsCount++;
			}
			totalCount++;
		}
	}

	public int getNPS() {
		return (int) (((promotersCount / totalCount) - (detractorsCount / totalCount)) * 100);
	}

	public void setDetractorsForUndeliveredOrders(int count) {
		detractorsCount += count;
	}

}
