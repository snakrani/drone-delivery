package com.walmart.DroneDelivery.models;

import java.time.LocalTime;

import com.walmart.DroneDelivery.utils.OrderUtil;
import com.walmart.DroneDelivery.utils.TimeUtil;

public class Order {

	TimeUtil timeUtil = new TimeUtil();
	OrderUtil orderUtil = new OrderUtil();
	
	private String orderNumber;
	private LocalTime orderTime;
	private LocalTime orderProcessedTime;
	private LocalTime orderDeliveredTime;
	private String direction;
	private double timeToDeliverOrder;

	public Order(String orderNumber, String direction, String orderTime) {
		this.setOrderNumber(orderNumber);
		this.setDirection(direction);
		this.setOrderTime(timeUtil.getLocalTime(orderTime));
		this.setTimeToDeliverOrder(direction);
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public LocalTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalTime orderTime) {
		this.orderTime = orderTime;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return direction;
	}

	public String getOrderProcessedTimeString() {
		if(orderProcessedTime != null) {
		return String.join(":", String.format("%02d", orderProcessedTime.getHour()),
			String.format("%02d", orderProcessedTime.getMinute()),
				String.format("%02d", orderProcessedTime.getSecond())); 
		} else {
			return "";
		}
		
	}

	public void setOrderProcessedTime(LocalTime orderProcessedTime) {
		this.orderProcessedTime = orderProcessedTime;
	}
	
	public LocalTime getOrderProcessedTime() {
		return orderProcessedTime;
	}

	public double getTimeToDeliverOrder() {
		return timeToDeliverOrder;
	}

	public void setTimeToDeliverOrder(String direction) {
		this.timeToDeliverOrder = orderUtil.getTimeToDeliverOrder(direction);
	}

	public LocalTime getOrderDeliveredTime() {
		return orderDeliveredTime;
	}

	public void setOrderDeliveredTime(LocalTime orderDeliveredTime) {
		this.orderDeliveredTime = orderDeliveredTime;
	}

}
