package com.walmart.DroneDelivery.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileReaderService implements ReaderService {

	private Logger logger = Logger.getLogger(FileOrderWriterService.class.getName());
	
	@Override
	public List<String> getData(String filePath) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			lines = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return lines;
	}
}
