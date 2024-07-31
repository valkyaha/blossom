package com.kittyvt.blossom_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
public class BlossomBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlossomBackendApplication.class, args);
	}

}
