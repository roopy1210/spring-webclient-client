package com.roopy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/webclient")
	public void runWebClient() {
		
		WebClient webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
		
		long startTime = System.currentTimeMillis();

		webClient.get().uri("/hello")
				.retrieve()
				.bodyToMono(String.class)
				.subscribe(ret -> {
					logger.info(ret + ", time: " + (System.currentTimeMillis() - startTime) + "sec");
			});

		webClient.get().uri("/world")
				.retrieve()
				.bodyToMono(String.class)
				.subscribe(ret -> {
					logger.info(ret + ", time: " + (System.currentTimeMillis() - startTime) + "sec");
		    });

		logger.info("end: " + (System.currentTimeMillis() - startTime) + "sec");
	}
	
	@GetMapping("/resttemplate")
	public void runRestTemplate() {
		
		long startTime = System.currentTimeMillis();

		String hello = restTemplate.getForObject("http://localhost:8081/hello", String.class);
		logger.info(hello + ", time: " + (System.currentTimeMillis() - startTime) + "sec");
		
		long startTime2 = System.currentTimeMillis();
		
		String world = restTemplate.getForObject("http://localhost:8081/world", String.class);
		logger.info(world + ", time: " + (System.currentTimeMillis() - startTime2) + "sec");
		
		logger.info("end: " + (System.currentTimeMillis() - startTime) + "sec");
	}
}
