package com.walmart.DroneDelivery;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.walmart.DroneDelivery.models.Order;
import com.walmart.DroneDelivery.services.DroneDeliveryServiceImpl;
import com.walmart.DroneDelivery.services.FileOrderWriterService;
import com.walmart.DroneDelivery.services.NetPromoterScoreService;
import com.walmart.DroneDelivery.services.OrderService;
import com.walmart.DroneDelivery.services.ReaderService;

@RunWith(MockitoJUnitRunner.class)
public class DroneDeliveryControllerTest {

	@Mock
	FileOrderWriterService fileOrderWriterServiceMock;

	@Mock
	DroneDeliveryServiceImpl droneDeliveryServiceImplMock;

	@Mock
	NetPromoterScoreService netPromoterScoreServiceMock;

	@Mock
	ReaderService readerServiceMock;

	@Mock
	OrderService orderServiceMock;

	@Mock
	Logger loggerMock;

	@InjectMocks
	DroneDeliveryController droneDeliveryController;

	@Test
	public void process() {

		String filePath = "/orders.txt";
		List<String> orderRows = asList("WM001 N11W5 05:11:50", "WM002 S3E2 05:11:55");
		List<Order> orders = asList(new Order("WM001", "N11W5", "05:11:50"));

		when(readerServiceMock.getData(filePath)).thenReturn(orderRows);
		when(orderServiceMock.makeOrders(orderRows)).thenReturn(orders);

		droneDeliveryController.process(filePath);

		verify(readerServiceMock).getData(filePath);
		verify(orderServiceMock).makeOrders(orderRows);
		verify(orderServiceMock).processOrders(orders);
	}

	@Test
	public void process_throwsException() {
		
		String filePath = "/orders.txt";
		String expectedErrorMessage = "File Reading Error";
		RuntimeException runtimeException = new RuntimeException(expectedErrorMessage);
		
		when(readerServiceMock.getData(filePath)).thenThrow(runtimeException);
		droneDeliveryController.process(filePath);
		
		verify(loggerMock).log(Level.SEVERE, runtimeException.getMessage(), runtimeException);
	}

	@Test
	public void process_ThrowsExceptionWhenNoFileArgument() {
		
		String exceptionMessage = null;
		try {
			droneDeliveryController.process(null);
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		}
		assertEquals("Please provide File path with Orders.", exceptionMessage);
	}
}
