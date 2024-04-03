package com.cps690.ehnacefilemethods;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.cps690.ehnacefilemethods.Dto.CustomConfig;
import com.cps690.ehnacefilemethods.utils.FileController;

@SpringBootApplication
@EnableConfigurationProperties(CustomConfig.class)
@ComponentScan(basePackages = "com.cps690.ehnacefilemethods")
public class EhnacefilemethodsApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EhnacefilemethodsApplication.class, args);
		FileController fileController = new FileController();
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		
		// Initialize delay, interval, and time unit
		long initialDelay = 0; // Start time interval
		long interval = 15; // Interval in seconds
		TimeUnit timeUnit = TimeUnit.SECONDS;
		// Schedule the task to run at fixed intervals
		scheduler.scheduleAtFixedRate(() -> {
			// Perform the action
			fileController.monitorFileDeletion("D:/cachestore/service1");
		}, initialDelay, interval, timeUnit);

	}

}
