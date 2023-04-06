package com.tukorea.cogTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class CogTestApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CogTestApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(CogTestApplication.class, args);
	}

}