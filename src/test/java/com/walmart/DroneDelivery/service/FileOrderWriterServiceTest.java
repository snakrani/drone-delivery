package com.walmart.DroneDelivery.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.walmart.DroneDelivery.models.Order;
import com.walmart.DroneDelivery.services.FileOrderWriterService;
import com.walmart.DroneDelivery.services.OrderService;
import com.walmart.DroneDelivery.services.OrderServiceImpl;

public class FileOrderWriterServiceTest {

	FileOrderWriterService fileOrderWriterService = new FileOrderWriterService();
	OrderService orderService = new OrderServiceImpl();

	@Test
	public void writeDelieveredOrders() throws IOException {
		deleteFile(fileOrderWriterService.deliveredOrdersFileLocation);
		deleteFile(fileOrderWriterService.failedOrdersFileLocation);
		deleteFile(fileOrderWriterService.pathToStoreFiles);
		List<String> orderRows = Arrays.asList("WM001 S3E2 05:11:55", "WM002 N11W5 05:11:50");
		List<Order> orders = orderService.makeOrders(orderRows);
		fileOrderWriterService.writeDelieveredOrders(orders);
		List<String> fileRows = getFileRows(fileOrderWriterService.deliveredOrdersFileLocation);
		assertEquals(orderRows.get(0), fileRows.get(0));
		assertEquals(orderRows.get(1), fileRows.get(1));
	}

	@Test
	public void writeNotDelieveredOrders() throws IOException {
		List<String> orderRows = Arrays.asList("WM003 S12E32 05:11:55", "WM004 N90W56 05:11:50");
		List<Order> orders = orderService.makeOrders(orderRows);
		fileOrderWriterService.writeUndeliveredOrders(orders);
		List<String> fileRows = getFileRows(fileOrderWriterService.failedOrdersFileLocation);
		assertEquals(orderRows.get(0), fileRows.get(0));
		assertEquals(orderRows.get(1), fileRows.get(1));
	}

	@Test
	public void validate_getResourcePath() {
		assertEquals("/orders/orders.txt", fileOrderWriterService.getResourcePath());
	}

	@Test
	public void validate_writeNpsWritesNpsScore() {
		fileOrderWriterService.writeNPS(80);
	}
	
	public void deleteFile(String fileLocation) throws IOException {
		try {
			Files.delete(Paths.get(fileLocation));
		} catch(NoSuchFileException e) {
			System.out.println(e);
		}
		catch (IOException ioException) {
			throw ioException;
		}
	}

	public List<String> getFileRows(String fileLocation) throws IOException {
		Path path = Paths.get(fileLocation);
		try {
			return Files.lines(path).collect(Collectors.toList());
		} catch (IOException ex) {
			throw ex;
		}
	}

}
