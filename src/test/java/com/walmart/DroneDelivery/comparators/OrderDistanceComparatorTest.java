package com.walmart.DroneDelivery.comparators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.walmart.DroneDelivery.models.Order;

public class OrderDistanceComparatorTest {

	OrderDistanceComparator orderDistanceComparator = new OrderDistanceComparator();
	
	@Test
	public void compare_OrderOneIsBigger() {
		Order order1 = new Order("WM001", "N11W5", "05:11:50");
		Order order2 = new Order("WM002", "S3E2", "05:11:55");
		
		assertEquals(1, orderDistanceComparator.compare(order1, order2));
	}
	
	@Test
	public void compare_OrderTwoIsBigger() {
		Order order1 = new Order("WM001", "S3E2", "05:11:50");
		Order order2 = new Order("WM002", "N11W5", "05:11:55");
		
		assertEquals(-1, orderDistanceComparator.compare(order1, order2));
	}
	
	@Test
	public void compare_CompareBothOrdersEqual() {
		Order order1 = new Order("WM001", "S3E2", "05:11:50");
		Order order2 = new Order("WM002", "S3E2", "05:11:55");
		
		assertEquals(0, orderDistanceComparator.compare(order1, order2));
	}
}
