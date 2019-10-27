package com.walmart.DroneDelivery.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.walmart.DroneDelivery.models.Order;
import com.walmart.DroneDelivery.utils.TimeUtil;


public class DroneDeliveryServiceImpl implements DroneDeliveryService {

	TimeUtil timeUtil = new TimeUtil();
	LocalTime startTime = LocalTime.of(06, 00, 00);
	LocalTime currentTime = startTime; 

	/* 
	 * Delivers orders  
	 * @param orders
	 * @return List<order>
	 **/
	@Override
	public List<Order> deliverOrders(List<Order> orders) {

		List<Order> processedOrders = new ArrayList<>(orders.size());
		for (Order order : orders) {
			LocalTime timeToDeliverOrder = totalTimeToDeliverOrder(order);
			if (currentTime.getHour() >= 6 && timeToDeliverOrder.getHour() < 22) {
				order.setOrderProcessedTime(currentTime);
				order.setOrderDeliveredTime(oneWayTimeToDeliverOrder(order));
				processedOrders.add(order);
				currentTime = timeToDeliverOrder;
			}
		}
		return processedOrders;
	}
	

	/**
	 * returns both way time to deliver order
	 * @param order
	 * @return LocalTime
	 */
	private LocalTime totalTimeToDeliverOrder(Order order) {
		String[] time = getTimeToDeliverOrder(order);
		int twoWayDistanceMinutes = Integer.parseInt(time[0]) * 2;
		int twoWayDistanceSeconds = Integer.parseInt(time[1]) * 2;
		return currentTime.plusMinutes(twoWayDistanceMinutes).plusSeconds(twoWayDistanceSeconds);
	}
	
	/**
	 * returns one way time to deliver order
	 * @param order -  order
	 * @return LocalTime
	 */
	public LocalTime oneWayTimeToDeliverOrder(Order order) {
		String[] time = getTimeToDeliverOrder(order);
		return currentTime.plusMinutes(Integer.parseInt(time[0])).plusSeconds(Integer.parseInt(time[1]));
	}
	
	
	/**
	 * returns String array of minutes and seconds of time to deliver order.
	 * @param order
	 * @return
	 */
	private String[] getTimeToDeliverOrder(Order order) {
		String timeString = String.valueOf(order.getTimeToDeliverOrder());
		return timeString.split("\\.");
	}

	
	public void setCurrentTime(LocalTime time) {
		this.currentTime = time;
	}

}
