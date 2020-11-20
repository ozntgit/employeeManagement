package org.telit.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.telit.model.Operation;
import org.telit.repository.IOperationRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OperationControllerIntegrationTest {

	@Autowired
	private WebTestClient webClient;
	
	//Increase timeout waiting level to avoid random error on execution
	@BeforeEach
    public void setUp() {
		webClient = webClient
                        .mutate()
                        .responseTimeout(Duration.ofMillis(30000))
                        .build();
    }

	@Autowired
    private IOperationRepository iOperationRepository;

	@Test
	@Order(1)
	public void testCreateOperation() {
		Operation o = new Operation();
		o.setName("Giacomo");
		o.setLastName("Bianchetto");
		o.setPassword("password");
		o.setAddress("via Padova 67, Vigonza");
		o.setType("IT");
		
		webClient.post()
					.uri("/operation/create")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(o)
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(2)
	public void testUpdateOperation() {
		Operation o = iOperationRepository.findAll().get(0);
		o.setName("Giacomo");
		o.setLastName("Bianchetto");
		o.setPassword("password");
		o.setAddress("via Padova 67, Vigonza");
		o.setType("DevOps");
		o.setActive(true);
		o.setWorkedHours(250.0);
		
		webClient.post()
					.uri("/operation/update")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(o)
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(3)
	public void testGetOperationById() {
		Operation o = iOperationRepository.findAll().get(0);
		webClient.get()
					.uri("/operation/get/{id}", o.getId())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBody(Operation.class)
		            .value(operation -> operation.getName(), equalTo("Giacomo"))
		            .value(operation -> operation.getLastName(), equalTo("Bianchetto"));
    }
	
	@Test
	@Order(4)
	public void testGetOperationByType() {
		webClient.get()
					.uri(uriBuilder -> uriBuilder
					    .path("/operation/findbytype")
					    .queryParam("type", "Devops")
					    .build())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBodyList(Operation.class)
		            .value(operations ->
		            operations.forEach( operation -> assertTrue(operation.getType().equals("DevOps"))));
    }

	@Test
	@Order(6)
	public void testDeleteOperation() {
		Operation o = iOperationRepository.findAll().get(0);
		webClient.get()
					.uri("/operation/delete/{id}", o.getId())
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(7)
	public void testDeleteOperationByFullName() {
		Operation o = new Operation();
		o.setName("Fabio");
		o.setLastName("Lima");
		
		webClient.post()
					.uri("/operation/create")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(o)
					.exchange()
					.expectStatus()
					.isOk();
		
		webClient.get()
					.uri(uriBuilder -> uriBuilder
						    .path("/operation/deletebyfullname")
						    .queryParam("name", "Fabio")
						    .queryParam("lastName", "Lima")
						    .build())
					.exchange()
					.expectStatus()
					.isOk();
		
		assertTrue(iOperationRepository.findAll().isEmpty());
	}
}