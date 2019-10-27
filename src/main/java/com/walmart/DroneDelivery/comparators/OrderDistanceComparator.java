package com.walmart.DroneDelivery.comparators;

import java.util.Comparator;

import com.walmart.DroneDelivery.models.Order;
import com.walmart.DroneDelivery.utils.OrderUtil;

public class OrderDistanceComparator implements Comparator<Order> {

	OrderUtil orderUtil = new OrderUtil();

	@Override
	public int compare(Order o1, Order o2) {
		double timeToDeliverOrderOne = orderUtil.getTimeToDeliverOrder(o1.getDirection());
		double TimeToDeliverOrderTwo = orderUtil.getTimeToDeliverOrder(o2.getDirection());
		
		if (timeToDeliverOrderOne > TimeToDeliverOrderTwo) {
			return 1;
		} else if (timeToDeliverOrderOne < TimeToDeliverOrderTwo) {
			return -1;
		} else
			return 0;
	}
}
