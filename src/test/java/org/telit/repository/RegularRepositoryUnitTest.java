package org.telit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telit.model.Regular;

@DataJpaTest
//@AutoConfigureTestDatabase(replace=Replace.NONE) //to use psotgres db
public class RegularRepositoryUnitTest {

	@Autowired
	IRegularRepository iRegularRepository;

	@Test
	public void testCreateRegular() {
		Regular r = new Regular();
		r.setName("Giacomo");
		r.setLastName("Bianchetto");
		r.setPassword("");
		r.setAddress("via Padova 67, Vigonza");
		r.setType("IT");
		r.setActive(true);
		r.setSSN("");
		r.setPensionInformation("Bla Bla Bla");

		Regular savedR = iRegularRepository.save(r);
		assertThat(savedR).hasFieldOrPropertyWithValue("name", "Giacomo");
		assertThat(savedR).hasFieldOrPropertyWithValue("lastName", "Bianchetto");
		assertThat(savedR).hasFieldOrPropertyWithValue("password", "");
		assertThat(savedR).hasFieldOrPropertyWithValue("address", "via Padova 67, Vigonza");
		assertThat(savedR).hasFieldOrPropertyWithValue("type", "IT");
		assertThat(savedR).hasFieldOrPropertyWithValue("active", true);
		assertThat(savedR).hasFieldOrPropertyWithValue("SSN", "");
		assertThat(savedR).hasFieldOrPropertyWithValue("pensionInformation", "Bla Bla Bla");
	}

	@Test
	public void testUpdateRegular() {
		Regular r = new Regular();
		r.setName("Giacomo");
		r.setLastName("Bianchetto");
		r.setPassword("");
		r.setAddress("via Padova 67, Vigonza");
		r.setType("IT");
		r.setActive(true);
		r.setSSN("");
		r.setPensionInformation("U wish");
		iRegularRepository.save(r);

		r.setId(1l);
		r.setType("DevOps");
		r.setPensionInformation("U WILL NEVER RETIRE");

		Regular updatedR = iRegularRepository.save(r);
		assertThat(updatedR).hasFieldOrPropertyWithValue("name", "Giacomo");
		assertThat(updatedR).hasFieldOrPropertyWithValue("lastName", "Bianchetto");
		assertThat(updatedR).hasFieldOrPropertyWithValue("password", "");
		assertThat(updatedR).hasFieldOrPropertyWithValue("address", "via Padova 67, Vigonza");
		assertThat(updatedR).hasFieldOrPropertyWithValue("type", "DevOps");
		assertThat(updatedR).hasFieldOrPropertyWithValue("active", true);
		assertThat(updatedR).hasFieldOrPropertyWithValue("SSN", "");
		assertThat(updatedR).hasFieldOrPropertyWithValue("pensionInformation", "U WILL NEVER RETIRE");
	}

	@Test
	public void testGetRegularById() {
		iRegularRepository.save(new Regular());
		assertNotNull(iRegularRepository.findById(1l));
	}

	@Test
	public void testFindRegularByType() {
		Regular r = new Regular();
		r.setType("Management");

		Regular r2 = new Regular();
		r2.setType("Management");

		iRegularRepository.save(r);
		iRegularRepository.save(r2);

		List<Regular> list = iRegularRepository.findByType("Management");
		assertThat(list).hasSize(2);
	}

	@Test
	public void testDeleteRegularById() {
		Regular r = iRegularRepository.save(new Regular());
		iRegularRepository.deleteById(r.getId());
		assertThat(iRegularRepository.findById(r.getId())).isEmpty();
	}

	@Test
	public void testDeleteRegularByFullName() {
		Regular r = new Regular();
		r.setName("Giacomo");
		r.setLastName("Bianchetto");
		iRegularRepository.save(r);

		iRegularRepository.deleteByNameAndLastName(r.getName(), r.getLastName());
		assertThat(iRegularRepository.findAll()).isEmpty();
	}
}