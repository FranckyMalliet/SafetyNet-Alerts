package com.safetynet.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
public class Application {

	/**
	 * This application manipulate a json file and his data
	 * If you want to use the actuators, please write /actuator/nameOfData
	 * @param args
	 */

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
		

