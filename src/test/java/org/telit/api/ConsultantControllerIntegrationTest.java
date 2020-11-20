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
import org.telit.model.Consultant;
import org.telit.repository.IConsultantRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsultantControllerIntegrationTest {

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
    private IConsultantRepository iConsultantRepository;

	@Test
	@Order(1)
	public void testCreateConsultant() {
		Consultant c = new Consultant();
		c.setName("Giacomo");
		c.setLastName("Bianchetto");
		c.setPassword("password");
		c.setAddress("via Padova 67, Vigonza");
		c.setType("IT");
		
		webClient.post()
					.uri("/consultant/create")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(c)
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(2)
	public void testUpdateConsultant() {
		Consultant c = iConsultantRepository.findAll().get(0);
		c.setName("Giacomo");
		c.setLastName("Bianchetto");
		c.setPassword("password");
		c.setAddress("via Padova 67, Vigonza");
		c.setType("DevOps");
		c.setActive(true);
		c.setPayRate(100.0);
		c.setWorkedHours(250.0);
		
		webClient.post()
					.uri("/consultant/update")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(c)
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(3)
	public void testGetConsultantById() {
		Consultant c = iConsultantRepository.findAll().get(0);
		webClient.get()
					.uri("/consultant/get/{id}", c.getId())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBody(Consultant.class)
		            .value(consultant -> consultant.getName(), equalTo("Giacomo"))
		            .value(consultant -> consultant.getLastName(), equalTo("Bianchetto"));
    }
	
	@Test
	@Order(4)
	public void testGetConsultantByType() {
		webClient.get()
					.uri(uriBuilder -> uriBuilder
					    .path("/consultant/findbytype")
					    .queryParam("type", "Devops")
					    .build())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBodyList(Consultant.class)
		            .value(consultants ->
		            consultants.forEach( consultant -> assertTrue(consultant.getType().equals("DevOps"))));
    }
	
	@Test
	@Order(5)
	public void testFindConsultantIfActiveByPayrateAndWorkedHours() {
		webClient.get()
					.uri(uriBuilder -> uriBuilder
					    .path("/consultant/customsearch")
					    .queryParam("payRate", 5000.0)
					    .queryParam("workedHours", 300.0)
					    .build())
		            .exchange()
		            .expectStatus().isOk()
		            .expectBodyList(Consultant.class).hasSize(1);
    }

	@Test
	@Order(6)
	public void testDeleteConsultant() {
		Consultant c = iConsultantRepository.findAll().get(0);
		webClient.get()
					.uri("/consultant/delete/{id}", c.getId())
					.exchange()
					.expectStatus()
					.isOk();
	}
	
	@Test
	@Order(7)
	public void testDeleteConsultantByFullName() {
		Consultant c = new Consultant();
		c.setName("Fabio");
		c.setLastName("Lima");
		
		webClient.post()
					.uri("/consultant/create")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(c)
					.exchange()
					.expectStatus()
					.isOk();
		
		webClient.get()
					.uri(uriBuilder -> uriBuilder
						    .path("/consultant/deletebyfullname")
						    .queryParam("name", "Fabio")
						    .queryParam("lastName", "Lima")
						    .build())
					.exchange()
					.expectStatus()
					.isOk();
		
		assertTrue(iConsultantRepository.findAll().isEmpty());
	}
}