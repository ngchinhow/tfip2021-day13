package com.tfip2021.module2;

import java.io.File;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Workshop13Application {
	private static final Logger logger = LoggerFactory.getLogger(Workshop13Application.class);
	private static final String DEFAULT_DIR = "C:\\Users\\CH\\AppData\\Local\\Temp\\data";

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Workshop13Application.class);
		ApplicationArguments cliOpts = new DefaultApplicationArguments(args);
		String dataDir = DEFAULT_DIR;
		if (cliOpts.containsOption("dataDir")) {
			dataDir = cliOpts.getOptionValues("dataDir").get(0);
		}
		app.setDefaultProperties(
			Collections.singletonMap("dataDir", dataDir)
		);
		logger.info(dataDir);
		File dir = new File(dataDir);
		dir.mkdirs();
		app.run(args);
	}
}
