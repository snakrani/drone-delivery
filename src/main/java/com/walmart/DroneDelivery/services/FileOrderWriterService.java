package com.walmart.DroneDelivery.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.walmart.DroneDelivery.models.Order;

public class FileOrderWriterService implements OrderWriterService {

	private Logger logger = Logger.getLogger(FileOrderWriterService.class.getName());
	public final String pathToStoreFiles = "/orders";
	public final String deliveredOrdersFileLocation = "/orders/orders.txt";
	public final String failedOrdersFileLocation = "/orders/undelivered-orders.txt";

	/* writes delivered orders to file
	 * @param List<Order> - delivered order list
	 **/
	@Override
	public void writeDelieveredOrders(List<Order> deliveredOrders) {
		Path path = Paths.get(deliveredOrdersFileLocation);
		writeOrders(path, deliveredOrders);
	}

	/* writes not delivered orders to file
	 * @param List<Order> - not delivered order list
	 **/
	@Override
	public void writeUndeliveredOrders(List<Order> unprocessedOrders) {
		Path path = Paths.get(failedOrdersFileLocation);
		writeOrders(path, unprocessedOrders);
	}

	/* returns file location of delivered orders.
	 **/
	@Override
	public String getResourcePath() {
		return deliveredOrdersFileLocation;
	}

	/*
	 * writes nps score to file.
	 * @param nps - nps score
	 **/
	@Override
	public void writeNPS(int nps) {
		Path path = Paths.get(deliveredOrdersFileLocation);
		try (BufferedWriter writer = Files.newBufferedWriter(path,  StandardOpenOption.APPEND)) {
			writer.newLine();
			writer.write("------------------\n");
			writer.write("NPS " + String.valueOf(nps));
			writer.write("\n------------------\n");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/*
	 * writes orders to file
	 * @param path - file path
	 * @param orders - orders
	 **/
	private void writeOrders(Path path, List<Order> orders) {
		createRootDirectory();
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
			for (Order order : orders) {
				if (order.getOrderProcessedTimeString() != "") {
					writer.write(getDelieveredOrderString(order));
				} else {
					writer.write(getOrderString(order));
				}
				writer.newLine();
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private String getDelieveredOrderString(Order order) {
		return order.getOrderNumber() + " " + order.getOrderProcessedTimeString();
	}

	private String getOrderString(Order order) {
		return order.getOrderNumber() + " " + order.getDirection() + " " + getFormattedOrderTime(order);
	}

	private String getFormattedOrderTime(Order order) {
		return String.join(":", String.format("%02d", order.getOrderTime().getHour()),
				String.format("%02d", order.getOrderTime().getMinute()),
				String.format("%02d", order.getOrderTime().getSecond()));
	}

	private void createRootDirectory() {
		try {
			if (!Files.exists(Paths.get(pathToStoreFiles))) {
				Files.createDirectory(Paths.get(pathToStoreFiles));
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	
}
