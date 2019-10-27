package com.walmart.DroneDelivery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.walmart.DroneDelivery.models.Order;
import com.walmart.DroneDelivery.services.DroneDeliveryServiceImpl;
import com.walmart.DroneDelivery.services.FileOrderWriterService;
import com.walmart.DroneDelivery.services.FileReaderService;
import com.walmart.DroneDelivery.services.NetPromoterScoreService;
import com.walmart.DroneDelivery.services.OrderService;
import com.walmart.DroneDelivery.services.OrderServiceImpl;
import com.walmart.DroneDelivery.services.OrderWriterService;
import com.walmart.DroneDelivery.services.ReaderService;

public class DroneDeliveryController {

	private Logger logger = Logger.getLogger(DroneDeliveryController.class.getName());
	private OrderWriterService orderWriterService = new FileOrderWriterService();
	private DroneDeliveryServiceImpl droneDeliveryServiceImpl = new DroneDeliveryServiceImpl();
	private NetPromoterScoreService netPromoterScoreService = new NetPromoterScoreService();
	private ReaderService readerService = new FileReaderService();
	private OrderService orderService = new OrderServiceImpl(droneDeliveryServiceImpl, netPromoterScoreService,
			orderWriterService);

	public static void main(String[] args) {
		DroneDeliveryController controller = new DroneDeliveryController();
		controller.process(args[0]);
	}

	public void process(String filePath) {
		validateFilePath(filePath);
		try {
			List<String> rows = readerService.getData(filePath);
			List<Order> orders = orderService.makeOrders(rows);
			orderService.processOrders(orders);
			System.out.println("Orders stored and processed in file :" + System.getenv("SystemDrive")
					+ orderWriterService.getResourcePath());

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void validateFilePath(String filePath) {
		if (filePath == null || filePath.length() < 2) {
			throw new RuntimeException("Please provide File path with Orders.");
		}
	}
}
