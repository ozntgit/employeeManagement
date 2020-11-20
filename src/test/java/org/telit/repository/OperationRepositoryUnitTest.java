package org.telit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telit.model.Operation;

@DataJpaTest
//@AutoConfigureTestDatabase(replace=Replace.NONE) //to use psotgres db
public class OperationRepositoryUnitTest {

	@Autowired
	IOperationRepository iOperationRepository;

	@Test
	public void testCreateOperation() {
		Date d = new Date();
		Operation o = new Operation();
		o.setName("Giacomo");
		o.setLastName("Bianchetto");
		o.setPassword("");
		o.setAddress("via Padova 67, Vigonza");
		o.setType("IT");
		o.setActive(true);
		o.setWorkedHours(250.0);
		o.setContractExpirationDate(d);

		Operation savedO = iOperationRepository.save(o);
		assertThat(savedO).hasFieldOrPropertyWithValue("name", "Giacomo");
		assertThat(savedO).hasFieldOrPropertyWithValue("lastName", "Bianchetto");
		assertThat(savedO).hasFieldOrPropertyWithValue("password", "");
		assertThat(savedO).hasFieldOrPropertyWithValue("address", "via Padova 67, Vigonza");
		assertThat(savedO).hasFieldOrPropertyWithValue("type", "IT");
		assertThat(savedO).hasFieldOrPropertyWithValue("active", true);
		assertThat(savedO).hasFieldOrPropertyWithValue("workedHours", 250.0);
		assertThat(savedO).hasFieldOrPropertyWithValue("contractExpirationDate", d);
	}

	@Test
	public void testUpdateOperation() {
		Date d = new Date();
		Operation o = new Operation();
		o.setName("Giacomo");
		o.setLastName("Bianchetto");
		o.setPassword("");
		o.setAddress("via Padova 67, Vigonza");
		o.setType("IT");
		o.setActive(true);
		o.setWorkedHours(250.0);
		o.setContractExpirationDate(d);
		iOperationRepository.save(o);

		o.setId(1l);
		o.setType("DevOps");
		o.setContractExpirationDate(d);

		Operation updatedO = iOperationRepository.save(o);
		assertThat(updatedO).hasFieldOrPropertyWithValue("name", "Giacomo");
		assertThat(updatedO).hasFieldOrPropertyWithValue("lastName", "Bianchetto");
		assertThat(updatedO).hasFieldOrPropertyWithValue("password", "");
		assertThat(updatedO).hasFieldOrPropertyWithValue("address", "via Padova 67, Vigonza");
		assertThat(updatedO).hasFieldOrPropertyWithValue("type", "DevOps");
		assertThat(updatedO).hasFieldOrPropertyWithValue("active", true);
		assertThat(updatedO).hasFieldOrPropertyWithValue("workedHours", 250.0);
		assertThat(updatedO).hasFieldOrPropertyWithValue("contractExpirationDate", d);
	}

	@Test
	public void testGetOperationById() {
		iOperationRepository.save(new Operation());
		assertNotNull(iOperationRepository.findById(1l));
	}

	@Test
	public void testFindOperationByType() {
		Operation o = new Operation();
		o.setType("Management");

		Operation o2 = new Operation();
		o2.setType("Management");

		iOperationRepository.save(o);
		iOperationRepository.save(o2);

		List<Operation> list = iOperationRepository.findByType("Management");
		assertThat(list).hasSize(2);
	}

	@Test
	public void testDeleteOperationById() {
		Operation o = iOperationRepository.save(new Operation());
		iOperationRepository.deleteById(o.getId());
		assertThat(iOperationRepository.findById(o.getId())).isEmpty();
	}

	@Test
	public void testDeleteOperationByFullName() {
		Operation o = new Operation();
		o.setName("Giacomo");
		o.setLastName("Bianchetto");
		iOperationRepository.save(o);

		iOperationRepository.deleteByNameAndLastName(o.getName(), o.getLastName());
		assertThat(iOperationRepository.findAll()).isEmpty();
	}
}