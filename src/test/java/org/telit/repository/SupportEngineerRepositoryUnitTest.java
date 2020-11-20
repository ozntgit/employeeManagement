package org.telit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telit.model.SupportEngineer;

@DataJpaTest
//@AutoConfigureTestDatabase(replace=Replace.NONE) //to use psotgres db
public class SupportEngineerRepositoryUnitTest {

	@Autowired
	ISupportEngineerRepository iSupportEngineerRepository;

	@Test
	public void testCreateSupportEngineer() {
		SupportEngineer se = new SupportEngineer();
		se.setName("Giacomo");
		se.setLastName("Bianchetto");
		se.setPassword("");
		se.setAddress("via Padova 67, Vigonza");
		se.setType("IT");
		se.setActive(true);
		se.setCategory("cat");
		se.setRole("chief");
		se.setSeniority("rookie");

		SupportEngineer savedSE = iSupportEngineerRepository.save(se);
		assertThat(savedSE).hasFieldOrPropertyWithValue("name", "Giacomo");
		assertThat(savedSE).hasFieldOrPropertyWithValue("lastName", "Bianchetto");
		assertThat(savedSE).hasFieldOrPropertyWithValue("password", "");
		assertThat(savedSE).hasFieldOrPropertyWithValue("address", "via Padova 67, Vigonza");
		assertThat(savedSE).hasFieldOrPropertyWithValue("type", "IT");
		assertThat(savedSE).hasFieldOrPropertyWithValue("active", true);
		assertThat(savedSE).hasFieldOrPropertyWithValue("category", "cat");
		assertThat(savedSE).hasFieldOrPropertyWithValue("role", "chief");
		assertThat(savedSE).hasFieldOrPropertyWithValue("seniority", "rookie");
	}

	@Test
	public void testUpdateSupportEngineer() {
		SupportEngineer se = new SupportEngineer();
		se.setName("Giacomo");
		se.setLastName("Bianchetto");
		se.setPassword("");
		se.setAddress("via Padova 67, Vigonza");
		se.setType("IT");
		se.setActive(true);
		se.setCategory("cat");
		se.setRole("chief");
		se.setSeniority("pure wisdom");
		iSupportEngineerRepository.save(se);

		se.setId(1l);
		se.setType("DevOps");
		se.setCategory("super category");
		se.setRole("commander");
		se.setSeniority("pure wisdom");

		SupportEngineer updatedSE = iSupportEngineerRepository.save(se);
		assertThat(updatedSE).hasFieldOrPropertyWithValue("name", "Giacomo");
		assertThat(updatedSE).hasFieldOrPropertyWithValue("lastName", "Bianchetto");
		assertThat(updatedSE).hasFieldOrPropertyWithValue("password", "");
		assertThat(updatedSE).hasFieldOrPropertyWithValue("address", "via Padova 67, Vigonza");
		assertThat(updatedSE).hasFieldOrPropertyWithValue("type", "DevOps");
		assertThat(updatedSE).hasFieldOrPropertyWithValue("active", true);
		assertThat(updatedSE).hasFieldOrPropertyWithValue("category", "super category");
		assertThat(updatedSE).hasFieldOrPropertyWithValue("role", "commander");
		assertThat(updatedSE).hasFieldOrPropertyWithValue("seniority", "pure wisdom");
	}

	@Test
	public void testGetSupportEngineerById() {
		iSupportEngineerRepository.save(new SupportEngineer());
		assertNotNull(iSupportEngineerRepository.findById(1l));
	}

	@Test
	public void testFindSupportEngineerByType() {
		SupportEngineer se = new SupportEngineer();
		se.setType("Management");

		SupportEngineer se2 = new SupportEngineer();
		se2.setType("Management");

		iSupportEngineerRepository.save(se);
		iSupportEngineerRepository.save(se2);

		List<SupportEngineer> list = iSupportEngineerRepository.findByType("Management");
		assertThat(list).hasSize(2);
	}

	@Test
	public void testDeleteSupportEngineerById() {
		SupportEngineer se = iSupportEngineerRepository.save(new SupportEngineer());
		iSupportEngineerRepository.deleteById(se.getId());
		assertThat(iSupportEngineerRepository.findById(se.getId())).isEmpty();
	}

	@Test
	public void testDeleteSupportEngineerByFullName() {
		SupportEngineer se = new SupportEngineer();
		se.setName("Giacomo");
		se.setLastName("Bianchetto");
		iSupportEngineerRepository.save(se);

		iSupportEngineerRepository.deleteByNameAndLastName(se.getName(), se.getLastName());
		assertThat(iSupportEngineerRepository.findAll()).isEmpty();
	}
}