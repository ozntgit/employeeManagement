package org.telit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telit.model.Consultant;

@DataJpaTest
//@AutoConfigureTestDatabase(replace=Replace.NONE) //to use postgres db
public class ConsultantRepositoryUnitTest {

	@Autowired
	IConsultantRepository iConsultantRepository;

	@Test
	public void testCreateConsultant() {
		Date d = new Date();
		Consultant c = new Consultant();
		c.setName("Giacomo");
		c.setLastName("Bianchetto");
		c.setPassword("");
		c.setAddress("via Padova 67, Vigonza");
		c.setType("IT");
		c.setActive(true);
		c.setPayRate(100.0);
		c.setWorkedHours(250.0);
		c.setContractExpirationDate(d);
		c.setServiceLevelAgreementInformation("Bla Bla Bla");

		Consultant savedC = iConsultantRepository.save(c);
		assertThat(savedC).hasFieldOrPropertyWithValue("name", "Giacomo");
		assertThat(savedC).hasFieldOrPropertyWithValue("lastName", "Bianchetto");
		assertThat(savedC).hasFieldOrPropertyWithValue("password", "");
		assertThat(savedC).hasFieldOrPropertyWithValue("address", "via Padova 67, Vigonza");
		assertThat(savedC).hasFieldOrPropertyWithValue("type", "IT");
		assertThat(savedC).hasFieldOrPropertyWithValue("active", true);
		assertThat(savedC).hasFieldOrPropertyWithValue("payRate", 100.0);
		assertThat(savedC).hasFieldOrPropertyWithValue("workedHours", 250.0);
		assertThat(savedC).hasFieldOrPropertyWithValue("contractExpirationDate", d);
		assertThat(savedC).hasFieldOrPropertyWithValue("serviceLevelAgreementInformation", "Bla Bla Bla");
	}

	@Test
	public void testUpdateConsultant() {
		Date d = new Date();
		Consultant c = new Consultant();
		c.setName("Giacomo");
		c.setLastName("Bianchetto");
		c.setPassword("");
		c.setAddress("via Padova 67, Vigonza");
		c.setType("IT");
		c.setActive(true);
		c.setPayRate(100.0);
		c.setWorkedHours(250.0);
		c.setContractExpirationDate(d);
		c.setServiceLevelAgreementInformation("Bla Bla Bla");
		iConsultantRepository.save(c);

		c.setId(1l);
		c.setType("DevOps");
		c.setPayRate(1000.0);
		c.setContractExpirationDate(d);
		c.setServiceLevelAgreementInformation("Ops Ops Ops");

		Consultant updatedC = iConsultantRepository.save(c);
		assertThat(updatedC).hasFieldOrPropertyWithValue("name", "Giacomo");
		assertThat(updatedC).hasFieldOrPropertyWithValue("lastName", "Bianchetto");
		assertThat(updatedC).hasFieldOrPropertyWithValue("password", "");
		assertThat(updatedC).hasFieldOrPropertyWithValue("address", "via Padova 67, Vigonza");
		assertThat(updatedC).hasFieldOrPropertyWithValue("type", "DevOps");
		assertThat(updatedC).hasFieldOrPropertyWithValue("active", true);
		assertThat(updatedC).hasFieldOrPropertyWithValue("payRate", 1000.0);
		assertThat(updatedC).hasFieldOrPropertyWithValue("workedHours", 250.0);
		assertThat(updatedC).hasFieldOrPropertyWithValue("contractExpirationDate", d);
		assertThat(updatedC).hasFieldOrPropertyWithValue("serviceLevelAgreementInformation", "Ops Ops Ops");
	}

	@Test
	public void testGetConsultantById() {
		iConsultantRepository.save(new Consultant());
		assertNotNull(iConsultantRepository.findById(1l));
	}

	@Test
	public void testFindConsultantByType() {
		Consultant c = new Consultant();
		c.setType("Management");

		Consultant c2 = new Consultant();
		c2.setType("Management");

		iConsultantRepository.save(c);
		iConsultantRepository.save(c2);

		List<Consultant> list = iConsultantRepository.findByType("Management");
		assertThat(list).hasSize(2);
	}

	@Test
	public void testFindConsultantIfActiveByPayrateAndWorkedHours() {

		Consultant c2 = new Consultant();
		c2.setActive(true);
		c2.setPayRate(4999.0);
		c2.setWorkedHours(100.0);

		iConsultantRepository.save(new Consultant()); //not active
		iConsultantRepository.save(c2);

		List<Consultant> list = iConsultantRepository.findByActiveTrueAndPayRateLessThanAndWorkedHoursLessThan(5000.0, 300.0);
		assertThat(list).hasSize(1);
	}

	@Test
	public void testDeleteConsultantById() {
		Consultant c = iConsultantRepository.save(new Consultant());
		iConsultantRepository.deleteById(c.getId());
		assertThat(iConsultantRepository.findById(c.getId())).isEmpty();
	}

	@Test
	public void testDeleteConsultantByFullName() {
		Consultant c = new Consultant();
		c.setName("Giacomo");
		c.setLastName("Bianchetto");
		iConsultantRepository.save(c);

		iConsultantRepository.deleteByNameAndLastName(c.getName(), c.getLastName());
		assertThat(iConsultantRepository.findAll()).isEmpty();
	}
}