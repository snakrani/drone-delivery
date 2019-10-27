package com.walmart.DroneDelivery.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import com.walmart.DroneDelivery.comparators.OrderDistanceComparator;
import com.walmart.DroneDelivery.models.Order;

public class OrderServiceImpl implements OrderService {

	DroneDeliveryService droneDeliveryService;
	OrderWriterService orderWriterService;
	NetPromoterScoreService netPromoterScoreService;

	/**
	 * Processes orders
	 * 
	 * @param orders
	 *            - orders to be processed
	 */
	@Override
	public void processOrders(List<Order> orders) {
		Collections.sort(orders, new OrderDistanceComparator());
		List<Order> deliveredOrders = droneDeliveryService.deliverOrders(orders);
		writeOrders(orders, deliveredOrders);

	}

	/**
	 * sends orders to write to file
	 * 
	 * @param orders
	 *            - list of orders
	 * @param deliveredOrders
	 *            - list of delivered orders
	 */
	public void writeOrders(List<Order> orders, List<Order> deliveredOrders) {
		
		orderWriterService.writeDelieveredOrders(deliveredOrders);
		orders.removeAll(deliveredOrders);
		orderWriterService.writeUndeliveredOrders(orders);
		netPromoterScoreService.countNPS(deliveredOrders);
		netPromoterScoreService.setDetractorsForUndeliveredOrders(orders.size());
		orderWriterService.writeNPS(netPromoterScoreService.getNPS());
	}

	/**
	 * makes orders from String row
	 * 
	 * @param rows
	 *            - list of orders in string format
	 */
	@Override
	public List<Order> makeOrders(List<String> rows) {
		List<Order> orders = new ArrayList<>(rows.size());
		for (String row : rows) {
			Order order = makeOrder(row);
			if (order != null) {
				orders.add(order);
			}
		}
		return orders;
	}

	/**
	 * makes orders from String row
	 * 
	 * @param row
	 *            - Order row
	 * @return order
	 */
	public Order makeOrder(String row) {
		StringTokenizer tokenizer = new StringTokenizer(row);
		if (row != null) {
			String orderNumber = tokenizer.nextToken();
			String direction = tokenizer.nextToken();
			String time = tokenizer.nextToken();
			return new Order(orderNumber, direction, time);
		}
		return null;
	}

	public OrderServiceImpl(DroneDeliveryServiceImpl droneDeliveryServiceImpl,
			NetPromoterScoreService netPromoterService, OrderWriterService orderWriterService) {
		this.droneDeliveryService = droneDeliveryServiceImpl;
		this.netPromoterScoreService = netPromoterService;
		this.orderWriterService = orderWriterService;
	}

	public OrderServiceImpl() {
	}

}
