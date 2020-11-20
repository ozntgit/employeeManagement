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
import org.telit.model.Regular;
import org.telit.repository.IRegularRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegularControllerIntegrationTest {

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
    private IRegularRepository iRegularRepository;

	@Test
	@Order(1)
	public void testCreateRegular() {
		Regular r = new Regular();
		r.setName("Giacomo");
		r.setLastName("Bianchetto");
		r.setPassword("password");
		r.setAddress("via Padova 67, Vigonza");
		r.setType("IT");
		
		webClient.post()
					.uri("/regular/create")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(r)
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(2)
	public void testUpdateRegular() {
		Regular r = iRegularRepository.findAll().get(0);
		r.setName("Giacomo");
		r.setLastName("Bianchetto");
		r.setPassword("password");
		r.setAddress("via Padova 67, Vigonza");
		r.setType("DevOps");
		r.setActive(true);
		r.setSSN("ABCD");
		r.setPensionInformation("Impossibile to retire.");
		
		webClient.post()
					.uri("/regular/update")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(r)
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(3)
	public void testGetRegularById() {
		Regular r = iRegularRepository.findAll().get(0);
		webClient.get()
					.uri("/regular/get/{id}", r.getId())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBody(Regular.class)
		            .value(regular -> regular.getName(), equalTo("Giacomo"))
		            .value(regular -> regular.getLastName(), equalTo("Bianchetto"));
    }
	
	@Test
	@Order(4)
	public void testGetRegularByType() {
		webClient.get()
					.uri(uriBuilder -> uriBuilder
					    .path("/regular/findbytype")
					    .queryParam("type", "Devops")
					    .build())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBodyList(Regular.class)
		            .value(regulars ->
		            regulars.forEach( regular -> assertTrue(regular.getType().equals("DevOps"))));
    }

	@Test
	@Order(6)
	public void testDeleteRegular() {
		Regular r = iRegularRepository.findAll().get(0);
		webClient.get()
					.uri("/regular/delete/{id}", r.getId())
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(7)
	public void testDeleteRegularByFullName() {
		Regular r = new Regular();
		r.setName("Fabio");
		r.setLastName("Lima");
		
		webClient.post()
					.uri("/regular/create")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(r)
					.exchange()
					.expectStatus()
					.isOk();
		
		webClient.get()
					.uri(uriBuilder -> uriBuilder
						    .path("/regular/deletebyfullname")
						    .queryParam("name", "Fabio")
						    .queryParam("lastName", "Lima")
						    .build())
					.exchange()
					.expectStatus()
					.isOk();
		
		assertTrue(iRegularRepository.findAll().isEmpty());
	}
}