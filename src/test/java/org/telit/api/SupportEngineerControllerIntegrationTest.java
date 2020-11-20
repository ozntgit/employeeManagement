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
import org.telit.model.SupportEngineer;
import org.telit.repository.ISupportEngineerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SupportEngineerControllerIntegrationTest {

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
    private ISupportEngineerRepository iSupportEngineerRepository;

	@Test
	@Order(1)
	public void testCreateSupportEngineer() {
		SupportEngineer se = new SupportEngineer();
		se.setName("Giacomo");
		se.setLastName("Bianchetto");
		se.setPassword("password");
		se.setAddress("via Padova 67, Vigonza");
		se.setType("IT");
		
		webClient.post()
					.uri("/supportengineer/create")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(se)
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(2)
	public void testUpdateSupportEngineer() {
		SupportEngineer se = iSupportEngineerRepository.findAll().get(0);
		se.setName("Giacomo");
		se.setLastName("Bianchetto");
		se.setPassword("password");
		se.setAddress("via Padova 67, Vigonza");
		se.setType("DevOps");
		se.setActive(true);
		se.setCategory("super category");
		se.setRole("commander");
		se.setSeniority("pure wisdom");
		
		webClient.post()
					.uri("/supportengineer/update")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(se)
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(3)
	public void testGetSupportEngineerById() {
		SupportEngineer se = iSupportEngineerRepository.findAll().get(0);
		webClient.get()
					.uri("/supportengineer/get/{id}", se.getId())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBody(SupportEngineer.class)
		            .value(supportEngineer -> supportEngineer.getName(), equalTo("Giacomo"))
		            .value(supportEngineer -> supportEngineer.getLastName(), equalTo("Bianchetto"));
    }
	
	@Test
	@Order(4)
	public void testGetSupportEngineerByType() {
		webClient.get()
					.uri(uriBuilder -> uriBuilder
					    .path("/supportengineer/findbytype")
					    .queryParam("type", "Devops")
					    .build())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBodyList(SupportEngineer.class)
		            .value(supportEngineers ->
		            supportEngineers.forEach( supportEngineer -> assertTrue(supportEngineer.getType().equals("DevOps"))));
    }

	@Test
	@Order(6)
	public void testDeleteSupportEngineer() {
		SupportEngineer se = iSupportEngineerRepository.findAll().get(0);
		webClient.get()
					.uri("/supportengineer/delete/{id}", se.getId())
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(7)
	public void testDeleteSupportEngineerByFullName() {
		SupportEngineer se = new SupportEngineer();
		se.setName("Fabio");
		se.setLastName("Lima");
		
		webClient.post()
					.uri("/supportengineer/create")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(se)
					.exchange()
					.expectStatus()
					.isOk();
		
		webClient.get()
					.uri(uriBuilder -> uriBuilder
						    .path("/supportengineer/deletebyfullname")
						    .queryParam("name", "Fabio")
						    .queryParam("lastName", "Lima")
						    .build())
					.exchange()
					.expectStatus()
					.isOk();
		
		assertTrue(iSupportEngineerRepository.findAll().isEmpty());
	}
}