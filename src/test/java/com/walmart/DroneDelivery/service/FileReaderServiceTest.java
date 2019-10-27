package com.walmart.DroneDelivery.service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.walmart.DroneDelivery.services.FileReaderService;

public class FileReaderServiceTest {

	FileReaderService fileReaderService = new FileReaderService();
	
	@Test
	public void getData() throws IOException {
		String file = "test.txt";
		List<String> lines = Arrays.asList("1st line", "2nd line");
		Files.write(Paths.get(file), lines, UTF_8, CREATE);
		assertEquals(lines, fileReaderService.getData(file));
	}
}
