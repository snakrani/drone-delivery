package com.walmart.DroneDelivery.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.walmart.DroneDelivery.models.Order;
import com.walmart.DroneDelivery.services.DroneDeliveryServiceImpl;
import com.walmart.DroneDelivery.services.FileOrderWriterService;
import com.walmart.DroneDelivery.services.NetPromoterScoreService;
import com.walmart.DroneDelivery.services.OrderServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

	@Mock
	FileOrderWriterService fileOrderWriterServiceMock; 
	
	@Mock
	DroneDeliveryServiceImpl droneDeliveryServiceImplMock;
	
	@Mock
	NetPromoterScoreService netPromoterScoreServiceMock;
	
	@InjectMocks OrderServiceImpl orderService;
	
	@Captor ArgumentCaptor<List<Order>> orderCaptor;
	
	@Test
	public void processOrders_validateOrdersSortedAndProcessed() throws Exception {
		
		List<Order> orders = orderService.makeOrders(asList("WM001 N11W5 05:11:50", "WM002 S3E2 05:11:55"));
		
		orderService.processOrders(orders);
		
		verify(droneDeliveryServiceImplMock).deliverOrders(orderCaptor.capture());
		List<Order> ordersCaptured = orderCaptor.getValue();
		
		assertEquals("WM002", ordersCaptured.get(0).getOrderNumber());
		assertEquals("WM001", ordersCaptured.get(1).getOrderNumber());
	}

	@Test
	public void writeOrders_ShouldWriteDeliveredAndUndeliveredOrdersInFile() {
		
		List<Order> orders = orderService.makeOrders(asList("WM002 S3E2 05:11:55", "WM001 N11W5 05:11:50"));
		List<Order> deliveredOrders = orderService.makeOrders(asList("WM002 S3E2 05:11:55"));
		
		orderService.writeOrders(orders, deliveredOrders);
		
		verify(fileOrderWriterServiceMock).writeDelieveredOrders(deliveredOrders);
		verify(fileOrderWriterServiceMock).writeUndeliveredOrders(orders);
		verify(netPromoterScoreServiceMock).setDetractorsForUndeliveredOrders(orders.size());
		verify(netPromoterScoreServiceMock).countNPS(deliveredOrders);
		verify(fileOrderWriterServiceMock).writeNPS(0);
	}
	
	
}
